package com.fast.api.account;

import com.fast.configuration.FeignRequest;
import com.fast.configuration.StashErrorDecoder;
import com.fast.model.PageResponse;
import com.fast.model.account.request.AccountPageRequest;
import com.fast.model.account.request.AccountTransferRequest;
import com.fast.model.account.root.Account;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name = "ACCOUNT",configuration = {StashErrorDecoder.class, FeignRequest.class})
@Component
public interface AccountApi {

    //-----------------------------------------------------------------------------------------------------------------
    // AccountController
    //-----------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/account/get/by-id")
    Account getAccountById(@RequestParam("id") int id);

    @PostMapping(value = "/account/page")
    PageResponse<Account> page(@RequestBody @Valid AccountPageRequest requestVo);

    @PostMapping(value = "/account/transfer")
    void transfer(@RequestBody @Valid AccountTransferRequest requestVo);

    //-----------------------------------------------------------------------------------------------------------------
    // TestController
    //-----------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/test/ribbon-test")
    String ribbonTest();

    // 默认的hystrix key：HiveApi#hystrixSuccess()  如果这个方法接收参数比如接收一个String，那么key：Hive#hystrixSuccess(String) 多个参数的以,号分隔
    @GetMapping("/test/hystrix-success")
    String hystrixSuccess();

    @GetMapping("/test/hystrix-timeout")
    String hystrixTimeOut();

    @GetMapping("/test/hystrix-error")
    String hystrixError() throws Exception;

    @GetMapping("/test/mq")
    void mqTest();


}
