package com.fast.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

    /**
     * 断路器测试方法
     * */
    public String hystrixSuccess(){
        return "Success";
    }

    public String hystrixTimeOut(){
        try {
            Random random = new Random();
            TimeUnit.SECONDS.sleep(random.nextInt(4));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "TimeOut";
    }

    public String hystrixError() throws Exception{
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new IOException("请求异常");
    }

}
