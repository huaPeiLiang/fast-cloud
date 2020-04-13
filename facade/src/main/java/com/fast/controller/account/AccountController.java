package com.fast.controller.account;


import com.fast.api.account.AccountApi;
import com.fast.model.account.request.AccountTransferRequest;
import com.fast.model.account.root.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private AccountApi accountApi;

    @RequestMapping(value = "/get/by-id")
    public Account getAccountById(@RequestParam("id") int id){
        return accountApi.getAccountById(id);
    }

    @PostMapping(value = "/transfer")
    public void transfer(@RequestBody @Valid AccountTransferRequest requestVo){
        accountApi.transfer(requestVo);
    };

}