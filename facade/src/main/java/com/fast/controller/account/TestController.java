package com.fast.controller.account;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.fast.api.account.AccountApi;
import com.fast.model.FastRunTimeException;
import com.fast.model.ReturnData;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private AccountApi accountApi;

    @RequestMapping(value = "/ribbon-test")
    @SentinelResource(value = "/test/ribbon-test", blockHandler = "ribbonTestBlockHandler", blockHandlerClass = TestBlockHandler.class)
    public ReturnData ribbonTest(){
        try{
            return ReturnData.success(accountApi.ribbonTest());
        }catch (FastRunTimeException e){
            return ReturnData.failed(e.getMessage());
        } catch (Exception e){
            return ReturnData.failed(e.getMessage());
        }
    }

    @RequestMapping("/get-dynamic-configuration-name")
    public ReturnData hystrixSuccess(){
        try{
            return ReturnData.success(accountApi.getDynamicConfigurationName());
        }catch (FastRunTimeException e){
            return ReturnData.failed(e.getMessage());
        } catch (Exception e){
            return ReturnData.failed(e.getMessage());
        }
    }

}
