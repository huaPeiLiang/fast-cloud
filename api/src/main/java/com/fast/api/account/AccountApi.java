package com.fast.api.account;

import com.fast.configuration.FeignRequest;
import com.fast.configuration.StashErrorDecoder;
import com.fast.model.account.request.AccountTransferRequest;
import com.fast.model.account.root.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@FeignClient(name = "ACCOUNT",configuration = {StashErrorDecoder.class, FeignRequest.class})
@Component
public interface AccountApi {

    //-----------------------------------------------------------------------------------------------------------------
    // AccountController
    //-----------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/account/get/by-id")
    Account getAccountById(@RequestParam("id") int id);

    @PostMapping(value = "/account/transfer")
    void transfer(@RequestBody @Valid AccountTransferRequest requestVo);

    //-----------------------------------------------------------------------------------------------------------------
    // TestController
    //-----------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/test/ribbon-test")
    String ribbonTest();

    // 默认的hystrix key：HiveApi#hystrixSuccess()  如果这个方法接收参数比如接收一个String，那么key：Hive#hystrixSuccess(String) 多个参数的以,号分隔
    @RequestMapping("/test/hystrix-success")
    String hystrixSuccess();

    @RequestMapping("/test/hystrix-timeout")
    String hystrixTimeOut();

    @RequestMapping("/test/hystrix-error")
    String hystrixError() throws Exception;

}
