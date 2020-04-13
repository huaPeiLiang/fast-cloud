package com.fast.controller;

import com.fast.model.account.request.AccountTransferRequest;
import com.fast.model.account.root.Account;
import com.fast.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @RequestMapping(value = "/get/by-id")
    public Account getAccountById(@RequestParam("id") int id){
        return accountService.getAccountById(id);
    }

    @PostMapping(value = "/transfer")
    public void transfer(@RequestBody @Valid AccountTransferRequest requestVo){
        accountService.transfer(requestVo);
    };

}
