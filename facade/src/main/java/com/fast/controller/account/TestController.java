package com.fast.controller.account;

import com.fast.api.account.AccountApi;
import com.fast.configuration.BaseResponse;
import com.fast.model.FastRunTimeException;
import com.fast.model.ReturnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@BaseResponse
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private AccountApi accountApi;

    @PostMapping("/throw-fast-exception")
    public void throwFastException(){
        accountApi.throwFastException();
    }

    @PostMapping("/throw-fast-exception2")
    public void throwFastException2(){
        int i=0/0;
    }

}
