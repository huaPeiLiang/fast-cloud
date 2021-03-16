package com.fast.controller.account;

import com.fast.api.account.AccountApi;
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
    public ReturnData ribbonTest(){
        try{
            return ReturnData.success(accountApi.ribbonTest());
        }catch (HystrixRuntimeException e){
            return ReturnData.failed(e);
        } catch (Exception e){
            return ReturnData.failed(e.getMessage());
        }
    }

    @RequestMapping("/get-dynamic-configuration-name")
    public ReturnData hystrixSuccess(){
        try{
            return ReturnData.success(accountApi.getDynamicConfigurationName());
        }catch (HystrixRuntimeException e){
            return ReturnData.failed(e);
        } catch (Exception e){
            return ReturnData.failed(e.getMessage());
        }
    }

}
