package com.fast.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl {

    @Value("${server.port}")
    private String serverPort;

    /**
     * 负载均衡测试方法
     * */
    public String ribbonTest(){
        return serverPort;
    }

}
