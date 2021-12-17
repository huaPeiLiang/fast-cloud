package com.fast.configuration;

import com.fast.model.FastRunTimeException;
import com.fast.model.ReturnData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(annotations = BaseResponse.class)
@ResponseBody
public class ExceptionHandlerAdvice {

    /**
     * 处理未捕获的Exception
     * @param e 异常
     * @return 统一响应体
     */
    @ExceptionHandler(Exception.class)
    public ReturnData handleException(Exception e){
        return ReturnData.failed(e);
//        return new ResponseResult(ResponseCode.SERVICE_ERROR.getCode(),ResponseCode.SERVICE_ERROR.getMsg(),null);
    }

    /**
     * 处理未捕获的RuntimeException
     * @param e 运行时异常
     * @return 统一响应体
     */
    @ExceptionHandler(RuntimeException.class)
    public ReturnData handleRuntimeException(RuntimeException e){
        return ReturnData.failed(e);
//        return new ResponseResult(ResponseCode.SERVICE_ERROR.getCode(),ResponseCode.SERVICE_ERROR.getMsg(),null);
    }

    /**
     * 处理业务异常BaseException
     * @param e 业务异常
     * @return 统一响应体
     */
    @ExceptionHandler(FastRunTimeException.class)
    public ReturnData handleBaseException(FastRunTimeException e){
        return ReturnData.failed(e);
    }
}
