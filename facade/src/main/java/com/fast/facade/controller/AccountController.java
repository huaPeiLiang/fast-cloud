package com.fast.facade.controller;

import com.fast.api.account.AccountDubboService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @DubboReference
    private AccountDubboService accountDubboService;

    @GetMapping("/hello")
    public String hello(@RequestParam String name){
        return accountDubboService.hello(name);
    }

}
