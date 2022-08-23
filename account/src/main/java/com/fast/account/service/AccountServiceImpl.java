package com.fast.account.service;

import com.fast.api.account.AccountDubboService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class AccountServiceImpl implements AccountDubboService {

    @Override
    public String hello(String name) {
        return "Hello" + name;
    }

}
