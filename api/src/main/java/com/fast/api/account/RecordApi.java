package com.fast.api.account;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "record")
@Component
public interface RecordApi {

    @RequestMapping(value = "/tran/record/add")
    void add(@RequestParam(value = "accountId") Integer accountId, @RequestParam(value = "changeAmount") Double changeAmount, @RequestParam(value = "changeType") String changeType);
}
