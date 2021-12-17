package com.fast.filter;

import com.fast.enums.ResponseCodeEnum;
import com.fast.model.FastRunTimeException;
import com.fast.tools.DelegatingServiceInstance;
import com.fast.tools.LoadBalancerUriTools;
import lombok.extern.log4j.Log4j2;
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
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;

/**
 * @Author Martin
 * @Date 2021/7/29 09:29
 * @remark
 */
@Log4j2
@Component
public class GrayFilter implements GlobalFilter, Ordered {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public int getOrder() {
        return -200;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Route route = (Route)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        URI url = route.getUri();
        String schemePrefix = (String)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR);
        log.info("GrayFilter----------->灰度拦截");
        if (url != null && ("lb".equals(url.getScheme()) || "lb".equals(schemePrefix))) {
            log.info("GrayFilter----------->进入灰度拦截");
            ServerWebExchangeUtils.addOriginalRequestUrl(exchange, url);
            return this.choose(exchange, url.getHost()).doOnNext((response) -> {
                if (!response.hasServer()) {
                    log.error("GrayFilter----------->无法找到实例");
                    throw new FastRunTimeException(ResponseCodeEnum.无法找到实例);
                } else {
                    URI uri = exchange.getRequest().getURI();
                    String overrideScheme = null;
                    if (schemePrefix != null) {
                        overrideScheme = url.getScheme();
                    }
                    log.info("GrayFilter----------->scheme:{},host:{},port:{},path:{}",uri.getScheme(),uri.getHost(),uri.getPort(),uri.getPath());
                    DelegatingServiceInstance serviceInstance = new DelegatingServiceInstance((ServiceInstance)response.getServer(), overrideScheme);
                    URI requestUrl = this.reconstructURI(serviceInstance, uri);

                    Route newRoute = Route.async().id(route.getId())
                            .order(route.getOrder())
                            .uri(requestUrl)
                            .asyncPredicate(route.getPredicate())
                            .filters(route.getFilters()).build();
                    log.info("GrayFilter----------->最终路由地址：scheme:{},host:{},port:{},path:{}",requestUrl.getScheme(),requestUrl.getHost(),requestUrl.getPort(),requestUrl.getPath());
                    exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, requestUrl);
                    exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR, newRoute);
                    log.info("GrayFilter----------->完成路由地址重定向");
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
        String versionNo = headers.getFirst("Version");
        log.info("GrayFilter----------->路由地址Version:{}",versionNo);
        Map<String,String> versionMap = new HashMap<>();
        versionMap.put("Version",versionNo);
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
