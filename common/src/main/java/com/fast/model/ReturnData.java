package com.fast.model;

import com.fast.enums.ResponseCodeEnum;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Data
public class ReturnData implements Serializable {
    protected final static Logger logger = LoggerFactory.getLogger(ReturnData.class);

    private static final long serialVersionUID = 1L;

    private Object data;
    private int code;
    private String msg;

    public ReturnData(){}

    private ReturnData(int code, Object data, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static final ReturnData success() {
        return success(null);
    }

    public static final ReturnData success(Object data) {
        return new ReturnData(ResponseCodeEnum.成功.code, data, "Success.");
    }

    public static final ReturnData failed(Exception exception) {
        if (exception instanceof FastRunTimeException){
            return new ReturnData(((FastRunTimeException) exception).getCode(), null, exception.getMessage());
        }else{
            return new ReturnData(ResponseCodeEnum.网络异常.code, null, exception.getMessage());
        }
    }

}
