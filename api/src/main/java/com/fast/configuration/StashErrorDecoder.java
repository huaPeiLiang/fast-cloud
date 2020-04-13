package com.fast.configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import static feign.FeignException.errorStatus;

public class StashErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String json = Util.toString(response.body().asReader());
            JSONObject jsonObject = JSON.parseObject(json);
            String message = response.reason();
            if (jsonObject != null){
                message = jsonObject.getString("message");
            }
            if (StringUtils.isNotEmpty(message)){
                return new RuntimeException(message);
            }
            return errorStatus(methodKey, response);
        } catch (IOException e) {
            return errorStatus(methodKey, response);
        }
    }
}
