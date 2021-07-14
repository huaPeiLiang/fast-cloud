package com.fast;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fast.model.ReturnData;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SentinelBlockHandler implements UrlBlockHandler {
    @Override
    public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException e) throws IOException {
        ReturnData returnData = ReturnData.failed("sentinel flow");
        response.setStatus(500);
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        new ObjectMapper().writeValue(response.getWriter(), returnData);
    }
}
