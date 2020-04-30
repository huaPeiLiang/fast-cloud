package com.fast.configuration;

import com.alibaba.fastjson.JSON;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.util.Objects;

import static feign.FeignException.errorStatus;

public class StashErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String json = Util.toString(response.body().asReader());
            String message = "网络异常，请稍后再试！";
            if (Objects.nonNull(JSON.parseObject(json))){
                message = JSON.parseObject(json).getString("message");
                return new Exception(message);
            }
            return errorStatus(methodKey, response);
        } catch (IOException e) {
            return errorStatus(methodKey, response);
        }
    }
}
