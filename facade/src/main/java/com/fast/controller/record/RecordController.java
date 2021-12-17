package com.fast.controller.record;


import com.fast.api.record.RecordApi;
import com.fast.configuration.BaseResponse;
import com.fast.model.FastRunTimeException;
import com.fast.model.ReturnData;
import com.fast.model.record.root.TranRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@BaseResponse
@RestController
@RequestMapping(value = "/record")
public class RecordController {

    @Autowired
    private RecordApi recordApi;

    @GetMapping(value = "/query")
    public List<TranRecord> getAccountById(){
        return recordApi.query();
    }

}