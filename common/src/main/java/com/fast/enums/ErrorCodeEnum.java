package com.fast.enums;

public enum ErrorCodeEnum {
    PARAMETER_ERROR(400,"Parameter Error", "Parameter error."),
    ;

    public final Integer code;

    public final String title;

    public final String message;

    public Integer getCode(){
        return code;
    }

    public String getTitle(){
        return title;
    }

    public String getMessage(){
        return message;
    }

    ErrorCodeEnum(Integer code,String title, String message) {
        this.code = code;
        this.title = title;
        this.message = message;
    }
}
