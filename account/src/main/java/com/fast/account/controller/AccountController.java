package com.fast.account.controller;

import com.fast.model.account.request.AccountUpdateRequest;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class AccountController {

    @GetMapping("/print/account/{firstName}/{lastName}")
    public String printAccount(@PathVariable("firstName") String firstName,
                               @PathVariable("lastName") String lastName,
                       @RequestParam("accountAge") Integer accountAge,
                       @RequestParam("accountHeight") BigDecimal accountHeight){
        return "Hello " + firstName + lastName + ",Your age is " + accountAge + "，Your height is " + accountHeight;
    }

    @PutMapping("/account/{firstName}/{lastName}")
    public String updateAccount(@PathVariable("firstName") String firstName,
                                @PathVariable("lastName") String lastName,
                                @RequestBody AccountUpdateRequest request){
        return "Hello " + firstName + lastName + ",Your new name is " + request.getFirstName() + request.getLastName() + ", Your age is " + request.getAccountAge() + "，Your height is " + request.getAccountHeight();
    }

}
