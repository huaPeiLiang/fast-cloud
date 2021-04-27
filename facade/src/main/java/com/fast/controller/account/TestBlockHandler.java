package com.fast.controller.account;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fast.model.ReturnData;

public class TestBlockHandler {

    public static ReturnData ribbonTestBlockHandler(BlockException e){
        return ReturnData.failed(e.getMessage());
    }

}
