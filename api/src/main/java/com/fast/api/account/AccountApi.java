package com.fast.api.account;

import com.fast.model.account.request.AccountUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "ACCOUNT", contextId = "AccountApi")
@Component
public interface AccountApi {

    @GetMapping("/print/account/{firstName}/{lastName}")
    String printAccount(@PathVariable("firstName") String firstName,
                               @PathVariable("lastName") String lastName,
                       @RequestParam("accountAge") Integer accountAge,
                       @RequestParam("accountHeight") BigDecimal accountHeight);

    @PutMapping("/account/{firstName}/{lastName}")
    String updateAccount(@PathVariable("firstName") String firstName,
                                @PathVariable("lastName") String lastName,
                                @RequestBody AccountUpdateRequest request);

}
