package com.fast.account.service.impl;

import com.fast.account.service.ZalandoService;
import com.fast.enums.ErrorCodeEnum;
import com.fast.model.BusinessErrorException;
import org.springframework.stereotype.Service;

@Service
public class ZalandoServiceImpl implements ZalandoService {

    @Override
    public String errorTest() {
        if (true){
            throw new BusinessErrorException(ErrorCodeEnum.PARAMETER_ERROR);
        }
        return "Success.";
    }

}
