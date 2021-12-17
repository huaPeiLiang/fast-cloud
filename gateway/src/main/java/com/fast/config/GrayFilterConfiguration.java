package com.fast.config;


import com.fast.filter.GrayFilter;
//import com.fast.filter.ResponseFilter;
import com.fast.filter.TokenFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrayFilterConfiguration {

    @Bean
    @ConditionalOnMissingBean({GrayFilter.class})
    public GrayFilter gatewayLoadBalancerGrayFilter() {
        return new GrayFilter();
    }

    @Bean
    @ConditionalOnMissingBean({TokenFilter.class})
    public TokenFilter gatewayLoadBalancerTokenFilter() {
        return new TokenFilter();
    }

//    @Bean
//    @ConditionalOnMissingBean({ResponseFilter.class})
//    public ResponseFilter gatewayLoadBalancerResponseFilter() {
//        return new ResponseFilter();
//    }

}
