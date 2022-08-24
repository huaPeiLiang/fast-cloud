package com.fast.account.controller;

import com.fast.account.service.ZalandoService;
import com.fast.model.ResponseEntity;
import com.fast.model.account.request.AccountUpdateRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
public class ZalandoController {

    @Resource
    private ZalandoService zalandoService;

    @GetMapping("/error")
    public String errorTest(){
        return zalandoService.errorTest();
    }

}
