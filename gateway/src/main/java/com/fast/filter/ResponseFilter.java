//package com.fast.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.fast.model.ReturnData;
//import org.reactivestreams.Publisher;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferFactory;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.core.io.buffer.DefaultDataBufferFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//
//@Component
//public class ResponseFilter implements GlobalFilter, Ordered {
//
//    @Override
//    public int getOrder() {
//        return -1;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpResponse originalResponse = exchange.getResponse();
//        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
//        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
//            @Override
//            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//                Mono<Void> voidMono = super.writeWith(body);
//                String contentType = exchange.getResponse().getHeaders().getFirst("Content-Type");
//                if (contentType != null && contentType.contains("application/json") && body instanceof Flux) {
//                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
//                    voidMono = super.writeWith(fluxBody.buffer().map(dataBuffer -> {
//                        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
//                        DataBuffer join = dataBufferFactory.join(dataBuffer);
//                        byte[] content = new byte[join.readableByteCount()];
//                        join.read(content);
//                        //释放掉内存
//                        DataBufferUtils.release(join);
//                        int status = exchange.getResponse().getStatusCode().value();
//                        ReturnData returnData = null;
//                        HttpHeaders headers = exchange.getResponse().getHeaders();
//                        if (HttpStatus.OK.value() == status) {
//                            String responseJSON = new String(content, StandardCharsets.UTF_8);
//                            Object object = JSON.parse(responseJSON);
//                            returnData = ReturnData.success(object);
//                        } else {
//                            String responseJSON = new String(content, StandardCharsets.UTF_8);
//                            JSONObject jsonObject = JSONObject.parseObject(responseJSON);
//                            returnData = ReturnData.failed(jsonObject.getString("message"));
//                            exchange.getResponse().setStatusCode(HttpStatus.OK);
//                        }
//                        byte[] bytes = JSONObject.toJSONBytes(returnData);
//                        return bufferFactory.wrap(bytes);
//                    }));
//                }
//                return voidMono;
//            }
//        };
//        return chain.filter(exchange.mutate().response(decoratedResponse).build());
//    }
//
//}
