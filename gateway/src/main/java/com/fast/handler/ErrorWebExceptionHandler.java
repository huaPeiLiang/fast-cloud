//package com.fast.handler;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.biteinvestments.cloud.gateway.exception.GatewayException;
//import com.biteinvestments.cloud.gateway.service.ApiLogService;
//import com.biteinvestments.cloud.gateway.utils.IPUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.autoconfigure.web.ErrorProperties;
//import org.springframework.boot.autoconfigure.web.ResourceProperties;
//import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
//import org.springframework.boot.web.error.ErrorAttributeOptions;
//import org.springframework.boot.web.reactive.error.ErrorAttributes;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.MessageSource;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//import reactor.netty.http.client.PrematureCloseException;
//
//import javax.annotation.Resource;
//import java.nio.CharBuffer;
//import java.nio.charset.StandardCharsets;
//import java.time.Duration;
//import java.util.Locale;
//import java.util.Map;
//
//public class ErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {
//    private static final Logger logger = LoggerFactory.getLogger(ErrorWebExceptionHandler.class);
//    @Resource
//    private   MessageSource  messageSource;
//    @Resource
//    private ApiLogService apiLogService;
//    public ErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
//        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
//    }
//
//    @Override
//    protected int getHttpStatus(Map<String, Object> errorAttributes) {
//        return 200;
//    }
//    @Override
//    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
//        Throwable error = this.getError(request);
//        JSONObject map = new JSONObject();
//        map.put("code", 500);
//        Locale locale=request.exchange().getAttribute("_locale");
//        String msg = error.getMessage();
//
//        if (msg != null && (msg.contains("Connection refused") || msg.contains("SERVICE_UNAVAILABLE"))) {
//            map.put("code", 500);
//            map.put("errorKey","MSG_SERVICE_UNAVAILABLE");
//            map.put("msg", messageSource.getMessage("msg.service.unavailable",null,locale));
//        } else {
//            if (error instanceof IllegalArgumentException) {
//                map.put("code", 400);
//                map.put("msg", "Invalid request");
//                map.put("errorKey","MSG_INVALID_REQUEST");
//            } else if (error instanceof IllegalAccessException) {
//                map.put("code", 401);
//                map.put("errorKey","MSG_UNAUTHORIZED");
//                map.put("msg", error.getMessage());
//            } else if (error instanceof ResponseStatusException) {
//                HttpStatus status = ((ResponseStatusException) error).getStatus();
//                map.put("code", status.value());
//                map.put("errorKey","UNKNOWN_ERROR");
//                map.put("msg", status.name());
//            }else if(error instanceof GatewayException){
//                GatewayException bizException=(GatewayException)error;
//                map.put("code", bizException.getCode());
//                if(bizException.getMsgKey()!=null) {
//                    map.put("errorKey", bizException.getMsgKey().toUpperCase().replace(".", "_"));
//                    map.put("msg", messageSource.getMessage(bizException.getMsgKey(),null,locale));
//                }
//            }else if(error instanceof PrematureCloseException){
//                map.put("code", 400);
//                map.put("errorKey","MSG_REQUEST_TOO_LARGE");
//                map.put("msg", messageSource.getMessage("msg.request.too.large",null,locale));
//            } else {
//                map.put("code", 500);
//                map.put("errorKey","GATEWAY_INTERNAL_ERROR");
//                map.put("msg", msg);
//                logger.error("服务器内部错误", error);
//            }
//
//        }
//        printRequest(request.exchange(), map);
//        return map;
//    }
//
//
//    private void printRequest(ServerWebExchange exchange, JSONObject map) {
//        ServerHttpRequest request = exchange.getRequest();
//        logger.error("========================== Error Report Start=========================");
//        logger.error("URL:{}" , request.getURI().toString());
//        logger.error("RemoteIP:{}" , IPUtils.getRemoteIp(request));
//        logger.error("Header:{}" , request.getHeaders().toString());
//        logger.error("Method:{}" , request.getMethodValue());
//        MediaType contentType = request.getHeaders().getContentType();
//        logger.error("Content-Type:{}" , contentType);
//        logger.error("Parameter:{}" , request.getQueryParams().toString());
//        boolean isAjax=contentType!=null && contentType.toString().contains("application/json");
//        boolean isPost="POST".equals(request.getMethodValue()) || "PUT".equals(request.getMethodValue());
//        if(isPost && isAjax){
//            try {
//                DataBuffer buffer=request.getBody().blockFirst(Duration.ofSeconds(3));
//                if(buffer!=null) {
//                    CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
//                    DataBufferUtils.release(buffer);
//                    logger.error("Body:{}" , charBuffer.toString());
//                }
//            }catch (Exception e){
//                logger.info("读取body异常：{}",e.getMessage());
//            }
//        }
//        String responseJSON=JSON.toJSONString(map);
//        logger.error("Response:{}",JSON.toJSONString(map));
//        logger.error("========================== Error Report End =========================");
//        apiLogService.saveResponse(exchange,"ERROR",responseJSON);
//    }
//
////
//    @Override
//    protected Mono<ServerResponse> renderErrorView(String viewName, ServerResponse.BodyBuilder responseBody, Map<String, Object> error) {
//        responseBody.contentType(MediaType.APPLICATION_JSON_UTF8);
//
//        return responseBody.body(BodyInserters.fromObject(JSON.toJSONString(error, true)));
//    }
//    @Override
//    protected Mono<ServerResponse> renderDefaultErrorView(ServerResponse.BodyBuilder responseBody, Map<String, Object> error) {
//        responseBody.contentType(MediaType.APPLICATION_JSON_UTF8);
//        return responseBody.body(BodyInserters.fromObject(JSON.toJSONString(error)));
//    }
//
//}
