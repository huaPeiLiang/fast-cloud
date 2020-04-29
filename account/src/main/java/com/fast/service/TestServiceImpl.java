package com.fast.service;

import com.fast.enums.ErrorEnum;
import com.fast.model.FastRunTimeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RefreshScope
public class TestServiceImpl {

    @Value("${server.port}")
    private String serverPort;
    @Value("${bus.renewal:null}")
    private String busRenewal;


    /**
     * 负载均衡测试方法
     * */
    public String ribbonTest(){
        return serverPort.concat(":").concat(busRenewal);
    }

    /**
     * 断路器测试方法
     * */
    public String hystrixSuccess(){
        return "Success";
    }

    public String hystrixTimeOut(){
        try {
            Random random = new Random();
            TimeUnit.SECONDS.sleep(random.nextInt(4));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "TimeOut";
    }

    public String hystrixError() throws Exception{
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new FastRunTimeException(ErrorEnum.网络异常);
    }

}
