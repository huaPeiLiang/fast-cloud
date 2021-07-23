package com.fast.api.record;

import com.fast.model.record.root.TranRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "RECORD${VERSION:}")
@Component
public interface RecordApi {

    @RequestMapping(value = "/tran/record/add")
    void add(@RequestParam(value = "accountId") Integer accountId, @RequestParam(value = "changeAmount") Double changeAmount, @RequestParam(value = "changeType") String changeType);

    @RequestMapping(value = "/tran/record/query")
    List<TranRecord> query();

}
