package com.fast.configuration;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.fast.enums.ResponseCodeEnum;
import com.fast.model.FastRunTimeException;
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
            if (Objects.nonNull(JSON.parseObject(json))){
                int code = JSON.parseObject(json).getInteger("status");
                String message = JSON.parseObject(json).getString("message");
                ResponseCodeEnum responseCodeEnum = ResponseCodeEnum.IsFastError(code);
                if (ObjectUtils.isNotEmpty(responseCodeEnum)){
                    return new FastRunTimeException(responseCodeEnum);
                } else{
                    return new RuntimeException(message);
                }
            }
            return errorStatus(methodKey, response);
        } catch (IOException e) {
            return errorStatus(methodKey, response);
        }
    }
}
