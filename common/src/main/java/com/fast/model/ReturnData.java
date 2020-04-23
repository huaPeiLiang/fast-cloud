package com.fast.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netflix.hystrix.exception.HystrixRuntimeException;

import java.io.Serializable;

public class ReturnData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String CODE_SUCCESS = "1";

    public static final String CODE_FAILED = "-1";

    private Object data;
    private String code;
    private String msg;

    public ReturnData() {}

    private ReturnData(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ReturnData(String code, Object data, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static final ReturnData success() {
        return success(null);
    }

    public static final ReturnData success(Object data) {
        return new ReturnData(CODE_SUCCESS, data, "Success.");
    }

    public static final ReturnData failed(String msg) {
        return failed(null, msg);
    }

    public static final ReturnData failed(Object data, String msg) {
        return new ReturnData(CODE_FAILED, data, msg);
    }

    public static final ReturnData failed(String code, String msg) {
        return new ReturnData(code, msg);
    }

    public static final ReturnData failed(FastRunTimeException e){
        return new ReturnData(e.getCode(), e.getMessage());
    }

    public static final ReturnData failed(HystrixRuntimeException e){
        if (e.getCause() instanceof FastRunTimeException){
            return ReturnData.failed((FastRunTimeException) e.getCause());
        }
        return ReturnData.failed(e.getCause().getMessage());
    }

    public static final ReturnData failed(String code,Object data, String msg) {
        return new ReturnData(code, data, msg);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return code == CODE_SUCCESS;
    }
}
