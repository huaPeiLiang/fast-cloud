package com.fast.controller.account;

import com.fast.api.account.AccountApi;
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

}
