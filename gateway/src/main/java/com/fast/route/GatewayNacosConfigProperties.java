package com.fast.route;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "nacos")
public class GatewayNacosConfigProperties {
    private String routesDataId;
    private String routeGroupId;
    private String tokenWhiteListDataId;
    private String tokenWhiteListGroupId;

    public String getRoutesDataId() {
        return routesDataId;
    }

    public void setRoutesDataId(String routesDataId) {
        this.routesDataId = routesDataId;
    }

    public String getRouteGroupId() {
        return routeGroupId;
    }

    public void setRouteGroupId(String routeGroupId) {
        this.routeGroupId = routeGroupId;
    }

    public String getTokenWhiteListDataId() {
        return tokenWhiteListDataId;
    }

    public void setTokenWhiteListDataId(String tokenWhiteListDataId) {
        this.tokenWhiteListDataId = tokenWhiteListDataId;
    }

    public String getTokenWhiteListGroupId() {
        return tokenWhiteListGroupId;
    }

    public void setTokenWhiteListGroupId(String tokenWhiteListGroupId) {
        this.tokenWhiteListGroupId = tokenWhiteListGroupId;
    }
}
