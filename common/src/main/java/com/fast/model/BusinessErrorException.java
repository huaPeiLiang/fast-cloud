package com.fast.model;

import com.fast.enums.ErrorCodeEnum;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * 自定义异常
 */
public class BusinessErrorException extends AbstractThrowableProblem {

    public BusinessErrorException(ErrorCodeEnum errorCodeEnum) {
        super(null,
                errorCodeEnum.title,
                Status.NOT_FOUND,
                errorCodeEnum.message
        );
    }


}
