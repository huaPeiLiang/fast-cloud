package com.fast.model;

import com.fast.enums.ErrorEnum;
import lombok.Data;

@Data
public class FastRunTimeException extends RuntimeException {

    private String code = "-1";
    private String message ;

    public FastRunTimeException(String message) {
        this.message = message;
    }

    public FastRunTimeException(ErrorEnum errorEnum) {
        this.message = errorEnum.message;
        this.code = errorEnum.code;
    }

    public FastRunTimeException(String code, String message) {
        this.message = message;
        this.code = code;
    }

}
