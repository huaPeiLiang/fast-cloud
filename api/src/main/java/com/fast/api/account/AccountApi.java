package com.fast.api.account;

import com.fast.configuration.FeignRequest;
import com.fast.configuration.StashErrorDecoder;
import com.fast.model.account.root.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ACCOUNT",configuration = {StashErrorDecoder.class, FeignRequest.class})
@Component
public interface AccountApi {

    //-----------------------------------------------------------------------------------------------------------------
    // AccountController
    //-----------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/account/get/by-id")
    Account getAccountById(@RequestParam("id") int id);

//    @PostMapping(value = "/account/page")
//    PageResponse<Account> page(@RequestBody @Valid AccountPageRequest requestVo);
//
//    @PostMapping(value = "/account/transfer")
//    void transfer(@RequestBody @Valid AccountTransferRequest requestVo);

    //-----------------------------------------------------------------------------------------------------------------
    // TestController
    //-----------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/test/ribbon-test")
    String ribbonTest();

    @GetMapping("/test/get-dynamic-configuration-name")
    String getDynamicConfigurationName();

}
