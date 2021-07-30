package com.fast.filter;

import com.fast.model.FastRunTimeException;
import com.fast.tools.DelegatingServiceInstance;
import com.fast.tools.LoadBalancerUriTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.reactive.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.reactive.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.reactive.Response;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;

/**
 * @Author Martin
 * @Date 2021/7/29 09:29
 * @remark
 */
public class GrayFilter implements GlobalFilter, Ordered {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Route route = (Route)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        URI url = route.getUri();
        String schemePrefix = (String)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR);
        if (url != null && ("lb".equals(url.getScheme()) || "lb".equals(schemePrefix))) {
            ServerWebExchangeUtils.addOriginalRequestUrl(exchange, url);
            return this.choose(exchange, url.getHost()).doOnNext((response) -> {
                if (!response.hasServer()) {
                    throw new FastRunTimeException("Unable to find instance for " + url.getHost());
                } else {
                    URI uri = exchange.getRequest().getURI();
                    String overrideScheme = null;
                    if (schemePrefix != null) {
                        overrideScheme = url.getScheme();
                    }

                    DelegatingServiceInstance serviceInstance = new DelegatingServiceInstance((ServiceInstance)response.getServer(), overrideScheme);
                    URI requestUrl = this.reconstructURI(serviceInstance, uri);

                    Route newRoute = Route.async().id(route.getId())
                            .order(route.getOrder())
                            .uri(requestUrl)
                            .asyncPredicate(route.getPredicate())
                            .filters(route.getFilters()).build();

                    exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, requestUrl);
                    exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR, newRoute);

                }
            }).then(chain.filter(exchange));
        } else {
            return chain.filter(exchange);
        }
    }

    protected URI reconstructURI(ServiceInstance serviceInstance, URI original) {
        return LoadBalancerUriTools.reconstructURI(serviceInstance, original);
    }

    private Mono<Response<ServiceInstance>> choose(ServerWebExchange exchange,String host) {
        URI uri = (URI)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        List<ServiceInstance> instances = discoveryClient.getInstances(host);
        HttpHeaders headers = exchange.getRequest().getHeaders();
        Response<ServiceInstance> serviceInstanceResponseByVersion = getServiceInstanceResponseByVersion(instances, headers);
        return Mono.just(getServiceInstanceResponseByVersion(instances, headers));
    }

    /**
     * 根据版本进行分发
     * @param instances
     * @param headers
     * @return
     */
    private Response<ServiceInstance> getServiceInstanceResponseByVersion(List<ServiceInstance> instances, HttpHeaders headers) {
        String versionNo = headers.getFirst("version");
        Map<String,String> versionMap = new HashMap<>();
        versionMap.put("version",versionNo);
        final Set<Map.Entry<String,String>> attributes =
                Collections.unmodifiableSet(versionMap.entrySet());
        ServiceInstance serviceInstance = null;
        for (ServiceInstance instance : instances) {
            Map<String,String> metadata = instance.getMetadata();
            if(metadata.entrySet().containsAll(attributes)){
                serviceInstance = instance;
                break;
            }
        }
        if(serviceInstance == null){
            return new EmptyResponse();
        }
        return new DefaultResponse(serviceInstance);
    }


}
