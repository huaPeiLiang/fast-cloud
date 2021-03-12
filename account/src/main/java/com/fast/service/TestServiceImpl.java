package com.fast.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class TestServiceImpl {

    @Value("${server.port}")
    private String serverPort;

    @Value("${dynamic.configuration.name}")
    private String name;

    /**
     * 负载均衡测试方法
     * */
    public String ribbonTest(){
        return serverPort;
    }

    /**
     * nacos动态获取配置测试方法
     * */
    public String getDynamicConfigurationName(){
        return name;
    }

}
