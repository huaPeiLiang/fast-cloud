package com.fast.controller;

import com.fast.service.TestServiceImpl;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private TestServiceImpl testService;

    @RequestMapping(value = "/ribbon-test")
    public String ribbonTest(){
        return testService.ribbonTest();
    }

    @HystrixCommand
    @RequestMapping("/hystrix-success")
    public String hystrixSuccess(){
        return testService.hystrixSuccess();
    }

    @HystrixCommand
    @RequestMapping("/hystrix-timeout")
    public String hystrixTimeOut(){
        return testService.hystrixTimeOut();
    }

    @HystrixCommand
    @RequestMapping("/hystrix-error")
    public String hystrixError() throws Exception{
        return testService.hystrixError();
    }

}
