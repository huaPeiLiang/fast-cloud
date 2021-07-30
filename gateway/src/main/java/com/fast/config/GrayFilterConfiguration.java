package com.fast.config;


import com.fast.filter.GrayFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrayFilterConfiguration {
    @Bean
    @ConditionalOnMissingBean({GrayFilter.class})
    public GrayFilter gatewayLoadBalancerClientFilter() {
        return new GrayFilter();
    }

}
