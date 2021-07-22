package com.fast.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.fast.route.DynamicRouteServiceImplByNacos;
import com.fast.route.GatewayNacosConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.util.Properties;
import java.util.concurrent.Executor;

@Component
public class AppConfig {
    protected static final Logger logger = LoggerFactory.getLogger(DynamicRouteServiceImplByNacos.class);
    private GatewayNacosConfigProperties properties;
    private boolean disablePermission;
    private static final String CONFIG_NAME="global_config.properties";
    private static final String GROUP_ID="DEFAULT_GROUP";

    /**
     * nacos服务器
     */
    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String nacosServer;

    /**
     * Config service
     */
    private ConfigService configService;

    public boolean isDisablePermission() {
        return disablePermission;
    }

    public AppConfig(@Autowired GatewayNacosConfigProperties properties) {
        this.properties = properties;
    }


    @PostConstruct
    public void init() {
        try {
            configService = NacosFactory.createConfigService(nacosServer);

            String configInfo = configService.getConfig(CONFIG_NAME, GROUP_ID, 30000);
            Properties properties = new Properties();
            properties.load(new ByteArrayInputStream(configInfo.getBytes()));
            String disabled = (String) properties.get("app.gateway.permission.disabled");
            properties.clear();
            disablePermission = "true".equals(disabled);
        } catch (Exception e) {
            logger.error("解析global_config.properties 异常", e);
            return;
        }

        try {
            //监听nacos服务器参数变化，当发生变化时，需要更新路由
            configService.addListener(CONFIG_NAME, GROUP_ID, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    try {
                        Properties properties = new Properties();
                        properties.load(new ByteArrayInputStream(configInfo.getBytes()));
                        String disabled = (String) properties.get("app.gateway.permission.disabled");
                        properties.clear();
                        disablePermission = "true".equals(disabled);
                    }catch (Exception e){
                        logger.error("解析global_config.properties 异常", e);
                    }
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {

            logger.error("从nacos接收动态路由配置出错!!!", e);
        }
    }
}
