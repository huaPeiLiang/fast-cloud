//package com.fast.config;
//
//import com.biteinvestments.cloud.gateway.filter.RequestVersionDispatchFilter;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
//import org.springframework.cloud.gateway.config.LoadBalancerProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class LoadBalancedBean {
//
//    @Bean
//    public RequestVersionDispatchFilter userLoadBalanceClientFilter(LoadBalancerClient client, LoadBalancerProperties properties) {
//        return new RequestVersionDispatchFilter(client, properties);
//    }
//}
