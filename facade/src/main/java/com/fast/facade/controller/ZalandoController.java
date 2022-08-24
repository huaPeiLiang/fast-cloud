package com.fast.facade.controller;

import com.fast.api.account.AccountApi;
import com.fast.api.account.ZalandoApi;
import com.fast.model.ResponseEntity;
import com.fast.model.account.request.AccountUpdateRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
public class ZalandoController {

    @Resource
    private ZalandoApi zalandoApi;

    @GetMapping("/error")
    public ResponseEntity<String> printAccount(){
        String s = zalandoApi.errorTest();
        return ResponseEntity.success(s);
    }

}
