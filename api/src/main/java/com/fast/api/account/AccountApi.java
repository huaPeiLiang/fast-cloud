package com.fast.api.account;

import com.fast.model.account.request.AccountTransferRequest;
import com.fast.model.account.root.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@FeignClient(name = "ACCOUNT")
@Component
public interface AccountApi {

    @RequestMapping(value = "/account/get/by-id")
    Account getAccountById(@RequestParam("id") int id);

    @PostMapping(value = "/account/transfer")
    void transfer(@RequestBody @Valid AccountTransferRequest requestVo);

}
