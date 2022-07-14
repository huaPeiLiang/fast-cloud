package com.fast.api.account;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ACCOUNT")
@Component
public interface AccountApi {

    @GetMapping("/test")
    String test();

}
