package com.fast.model;

import com.fast.enums.ResponseCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FastRunTimeException extends RuntimeException {

    private int code;

    private String message;

    public FastRunTimeException(ResponseCodeEnum responseCodeEnum) {
        this.code = responseCodeEnum.code;
        this.message = responseCodeEnum.message;
    }

    public FastRunTimeException(String message) {
        this.code = ResponseCodeEnum.网络异常.code;
        this.message = message;
    }

}
