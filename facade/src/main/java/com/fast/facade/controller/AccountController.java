package com.fast.facade.controller;

import com.fast.api.account.AccountApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AccountController {

    @Resource
    private AccountApi accountApi;

    @GetMapping("/test")
    public String test(){
        return accountApi.test();
    }

}
