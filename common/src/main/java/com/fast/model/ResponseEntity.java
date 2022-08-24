package com.fast.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class ResponseEntity<T> implements Serializable {
    protected final static Logger logger = LoggerFactory.getLogger(ResponseEntity.class);

    private static final long serialVersionUID = 1L;

    public static final int CODE_SUCCESS = 1;

    public static final int CODE_FAILED = -1;

    private T data;
    private int code;
    private String msg;

    public ResponseEntity() {}

    public ResponseEntity(int code, T data, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseEntity(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static final ResponseEntity success() {
        return success(null);
    }

    public static final ResponseEntity success(Object data) {
        return new ResponseEntity(CODE_SUCCESS, data, "Success.");
    }

    public static final ResponseEntity failed(String msg) {
        return failed(null, msg);
    }

    public static final ResponseEntity failed(Object data, String msg) {
        return new ResponseEntity(CODE_FAILED, data, msg);
    }

    public static final ResponseEntity failed(int code, Object data, String msg) {
        return new ResponseEntity(code, data, msg);
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
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
