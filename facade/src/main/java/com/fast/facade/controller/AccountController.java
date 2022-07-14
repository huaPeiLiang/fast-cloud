package com.fast.facade.controller;

import com.fast.api.account.AccountApi;
import com.fast.model.account.request.AccountUpdateRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
public class AccountController {

    @Resource
    private AccountApi accountApi;

    @GetMapping("/print/account/{firstName}/{lastName}")
    public String printAccount(@PathVariable("firstName") String firstName,
                               @PathVariable("lastName") String lastName,
                       @RequestParam("accountAge") Integer accountAge,
                       @RequestParam("accountHeight") BigDecimal accountHeight){
        return accountApi.printAccount(firstName, lastName, accountAge, accountHeight);
    }

    @PutMapping("/account/{firstName}/{lastName}")
    public String updateAccount(@PathVariable("firstName") String firstName,
                                @PathVariable("lastName") String lastName,
                                @RequestBody AccountUpdateRequest request){
        return accountApi.updateAccount(firstName, lastName, request);
    }

}
