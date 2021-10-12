package com.fast.filter;

import com.fast.model.BiteClaims;
import com.fast.util.JWTUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Martin
 */
@Log4j2
@RefreshScope
@Component
public class TokenFilter implements GlobalFilter, Ordered {

    @Value("${url.white.list:}")
    private String urlWhiteList;

    @Override
    public int getOrder() {
        return -100;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Route route = (Route)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        URI uri = route.getUri();
        log.info("TokenFilter----------->token认证scheme:{},host:{},port:{},path:{}",uri.getScheme(),uri.getHost(),uri.getPort(),uri.getPath());
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();
        log.info("TokenFilter----------->token认证scheme:{},host:{},port:{},path:{}",uri.getScheme(),uri.getHost(),uri.getPort(),uri.getPath());
        // 判断是否是报名单接口
        List<String> urlWhiteLists = Arrays.asList(urlWhiteList.split(","));
        if (urlWhiteLists.indexOf(path) == -1){
            log.info("TokenFilter----------->token不在白名单内scheme:{},host:{},port:{},path:{}",uri.getScheme(),uri.getHost(),uri.getPort(),uri.getPath());
            // token验证
            String accessToken = request.getHeaders().getFirst("AccessToken");
            BiteClaims biteClaims = JWTUtils.checkToken(accessToken);
        }
        log.info("TokenFilter----------->token认证完成scheme:{},host:{},port:{},path:{}",uri.getScheme(),uri.getHost(),uri.getPort(),uri.getPath());
        return chain.filter(exchange);
    }

}
