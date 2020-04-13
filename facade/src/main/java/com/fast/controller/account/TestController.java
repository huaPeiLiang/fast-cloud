package com.fast.controller.account;

import com.fast.api.account.AccountApi;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private AccountApi accountApi;

    @RequestMapping(value = "/ribbon-test")
    public String ribbonTest(){
        return accountApi.ribbonTest();
    }

    @RequestMapping("/hystrix-success")
    public String hystrixSuccess(){
        return accountApi.hystrixSuccess();
    }

    @RequestMapping("/hystrix-timeout")
    public String hystrixTimeOut(){
        return accountApi.hystrixTimeOut();
    }

    @RequestMapping("/hystrix-error")
    public String hystrixError() throws Exception{
        return accountApi.hystrixError();
    }

}
