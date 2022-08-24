package com.fast.api.account;

import com.fast.model.account.request.AccountUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "ACCOUNT", contextId = "ZalandoApi")
@Component
public interface ZalandoApi {

    @GetMapping("/error")
    String errorTest();


}
