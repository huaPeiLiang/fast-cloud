package com.fast.service;

import com.baomidou.mybatisplus.core.toolkit.AES;
import com.fast.enums.ResponseCodeEnum;
import com.fast.model.FastRunTimeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class TestServiceImpl {

    @Value("${server.port}")
    private String serverPort;

    /**
     * 手动抛出异常测试
     * */
    public void throwFastException(){
        throw new FastRunTimeException(ResponseCodeEnum.Token过期或已失效);
    }

    public static void main(String[] args) {
        System.out.println("111.229.65.73 - "+AES.encrypt("111.229.65.73", "fd2a5ad9ef2e8e80"));
        System.out.println("111.229.65.73:8848 - "+AES.encrypt("111.229.65.73:8848", "fd2a5ad9ef2e8e80"));
        System.out.println("111.229.65.73:3306 - "+AES.encrypt("111.229.65.73:3306", "fd2a5ad9ef2e8e80"));
    }

}
