package com.fast.mq;

import com.fast.model.account.mq.AccountMqConstants;
import com.fast.model.account.request.AccountMqTestRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding(AccountMq.class)
public class AccountMqImpl {

    /**
     * 消费测试消息
     */
    @StreamListener(AccountMqConstants.MQ_ACCOUNT_TEST)
    public void consumptionTest(AccountMqTestRequest request){
        System.out.println(request.getName());
    }

}
