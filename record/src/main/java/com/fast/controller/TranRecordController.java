package com.fast.controller;

import com.fast.model.record.root.TranRecord;
import com.fast.service.TranRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tran/record")
public class TranRecordController {

    @Autowired
    private TranRecordServiceImpl tranRecordService;

    @RequestMapping(value = "/add")
    public TranRecord add(@RequestParam(value = "accountId") Integer accountId, @RequestParam(value = "changeAmount") Double changeAmount, @RequestParam(value = "changeType") String changeType){
        return tranRecordService.add(accountId, changeAmount, changeType);
    }

}
