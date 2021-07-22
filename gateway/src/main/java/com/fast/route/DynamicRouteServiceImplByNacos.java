package com.fast.route;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executor;


@Component
public class DynamicRouteServiceImplByNacos {
    protected static final Logger logger = LoggerFactory.getLogger(DynamicRouteServiceImplByNacos.class);
    private GatewayNacosConfigProperties properties;
    /**
     * nacos服务器
     */
    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String nacosServer;

    /**
     * 动态路由service
     */
    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;
    /**
     * Config service
     */
    private ConfigService configService;

    public DynamicRouteServiceImplByNacos(@Autowired GatewayNacosConfigProperties properties) {
        this.properties=properties;
    }

    @PostConstruct
    public void init() {
        try {
            configService = NacosFactory.createConfigService(nacosServer);

            String configInfo = configService.getConfig(properties.getRoutesDataId(), properties.getRouteGroupId(), 30000);
            //logger.info("获取网关当前配置:\r\n" + configInfo);
            List<RouteDefinition> definitionList = JSON.parseArray(configInfo, RouteDefinition.class);
            for (RouteDefinition definition : definitionList) {
                logger.info("更新路由 : " + definition.toString());
                dynamicRouteService.add(definition);
            }
        } catch (Exception e) {
            logger.error("初始化网关路由时发生错误", e);
            return;
        }


        try {
            //监听nacos服务器参数变化，当发生变化时，需要更新路由
            configService.addListener(properties.getRoutesDataId(), properties.getRouteGroupId(), new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    List<RouteDefinition> definitionList = JSON.parseArray(configInfo, RouteDefinition.class);
                    for (RouteDefinition definition : definitionList) {
                        //logger.info("更新路由 : " + definition.toString());
                        dynamicRouteService.update(definition);
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
