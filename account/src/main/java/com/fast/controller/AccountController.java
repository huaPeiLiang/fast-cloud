package com.fast.controller;

import com.fast.model.PageResponse;
import com.fast.model.account.root.Account;
import com.fast.service.AccountServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @GetMapping(value = "/get/by-id")
    public Account getAccountById(@RequestParam("id") int id){
        return accountService.getAccountById(id);
    }

//    @PostMapping(value = "/page")
//    public PageResponse<Account> page(@RequestBody @Valid AccountPageRequest requestVo){
//        PageResponse<Account> basePageResponse = new PageResponse<>();
//        Page<Account> page = accountService.page(requestVo);
//        if (Objects.nonNull(page)){
//            BeanUtils.copyProperties(page,basePageResponse);
//        }
//        return basePageResponse;
//    }
//
//    @PostMapping(value = "/transfer")
//    public void transfer(@RequestBody @Valid AccountTransferRequest requestVo){
//        accountService.transfer(requestVo);
//    };

}
