package com.fast.controller.record;


import com.fast.api.record.RecordApi;
import com.fast.model.FastRunTimeException;
import com.fast.model.ReturnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/record")
public class RecordController {

    @Autowired
    private RecordApi recordApi;

    @GetMapping(value = "/query")
    public ReturnData getAccountById(){
        try{
            return ReturnData.success(recordApi.query());
        }catch (FastRunTimeException e){
            return ReturnData.failed(e.getMessage());
        } catch (Exception e){
            return ReturnData.failed(e.getMessage());
        }
    }

}