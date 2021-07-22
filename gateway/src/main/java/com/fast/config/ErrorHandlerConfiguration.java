package com.fast.config;
//
//
//import com.biteinvestments.cloud.gateway.handler.ErrorWebExceptionHandler;
//import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.web.reactive.error.ErrorAttributes;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.codec.ServerCodecConfigurer;
//import org.springframework.web.reactive.result.view.ViewResolver;
//
//import java.util.Collections;
//import java.util.List;
//
////
@Configuration
@EnableConfigurationProperties({ServerProperties.class, ResourceProperties.class})
public class ErrorHandlerConfiguration {
//
//    private final ServerProperties serverProperties;
//
//    private final ApplicationContext applicationContext;
//
//    private final ResourceProperties resourceProperties;
//    private List<ViewResolver>viewResolvers;
//
//
//    private final ServerCodecConfigurer serverCodecConfigurer;
//
//    public ErrorHandlerConfiguration(ServerProperties serverProperties,
//                                     ResourceProperties resourceProperties,
//                                     ObjectProvider<List<ViewResolver>> viewResolversProvider,
//                                     ServerCodecConfigurer serverCodecConfigurer,
//                                     ApplicationContext applicationContext) {
//        this.serverProperties = serverProperties;
//        this.applicationContext = applicationContext;
//        this.resourceProperties = resourceProperties;
//        this.viewResolvers = viewResolversProvider
//                .getIfAvailable(Collections::emptyList);
//        this.serverCodecConfigurer = serverCodecConfigurer;
//    }
//
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public ErrorWebExceptionHandler errorWebExceptionHandler(
//            ErrorAttributes errorAttributes) {
//        ErrorWebExceptionHandler exceptionHandler = new ErrorWebExceptionHandler(
//                errorAttributes, this.resourceProperties,
//                this.serverProperties.getError(), this.applicationContext);
//        exceptionHandler.setViewResolvers(this.viewResolvers);
//        exceptionHandler.setMessageWriters(this.serverCodecConfigurer.getWriters());
//        exceptionHandler.setMessageReaders(this.serverCodecConfigurer.getReaders());
//        return exceptionHandler;
//    }
}