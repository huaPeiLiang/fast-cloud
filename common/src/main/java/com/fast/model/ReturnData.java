package com.fast.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class ReturnData implements Serializable {
    protected final static Logger logger = LoggerFactory.getLogger(ReturnData.class);

    private static final long serialVersionUID = 1L;

    public static final int CODE_SUCCESS = 1;

    public static final int CODE_FAILED = -1;

    private Object data;
    private int code;
    private String msg;

    public ReturnData() {}

    private ReturnData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ReturnData(int code, Object data, String msg) {
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

    public static final ReturnData failed(int code, String msg) {
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

    public static final ReturnData failed(int code,Object data, String msg) {
        return new ReturnData(code, data, msg);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
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
