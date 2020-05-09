package com.fast.mq;

import com.fast.model.account.mq.AccountMqConstants;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

@EnableBinding
public interface AccountMq {

    /**
     * 发送测试消息
     */
    @Output(AccountMqConstants.MQ_ACCOUNT_TEST)
    SubscribableChannel sendTest();

}
