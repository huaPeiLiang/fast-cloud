package com.fast.service;

import com.alibaba.fastjson.JSONObject;
import com.fast.model.account.request.AccountMqTestRequest;
import com.fast.mq.AccountMq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

@Service
public class MqServiceImpl {

    @Autowired
    private AccountMq accountMq;

    public void mqTestSend(){
        AccountMqTestRequest accountMqTestRequest = new AccountMqTestRequest();
        accountMqTestRequest.setName("张三");
        accountMq.sendTest().send(new GenericMessage<>(JSONObject.toJSONString(accountMqTestRequest)));
    }

}
