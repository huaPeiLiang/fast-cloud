package com.fast.controller.account;

import com.fast.api.account.AccountApi;
import com.fast.model.ReturnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private AccountApi accountApi;

    @RequestMapping(value = "/ribbon-test")
    public ReturnData ribbonTest(){
        try{
            return ReturnData.success(accountApi.ribbonTest());
        }catch (Exception e){
            return ReturnData.failed(e.getMessage());
        }
    }

    @RequestMapping("/hystrix-success")
    public ReturnData hystrixSuccess(){
        try{
            return ReturnData.success(accountApi.hystrixSuccess());
        }catch (Exception e){
            return ReturnData.failed(e.getMessage());
        }
    }

    @RequestMapping("/hystrix-timeout")
    public ReturnData hystrixTimeOut(){
        try{
            return ReturnData.success(accountApi.hystrixTimeOut());
        }catch (Exception e){
            return ReturnData.failed(e.getMessage());
        }
    }

    @RequestMapping("/hystrix-error")
    public ReturnData hystrixError(){
        try{
            return ReturnData.success(accountApi.hystrixError());
        }catch (Exception e){
            return ReturnData.failed(e.getMessage());
        }
    }

}
