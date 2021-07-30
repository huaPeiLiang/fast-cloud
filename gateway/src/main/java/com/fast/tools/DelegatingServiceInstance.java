//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.fast.tools;

import org.springframework.cloud.client.ServiceInstance;

import java.net.URI;
import java.util.Map;

public class DelegatingServiceInstance implements ServiceInstance {
    final ServiceInstance delegate;
    private String overrideScheme;

    public DelegatingServiceInstance(ServiceInstance delegate, String overrideScheme) {
        this.delegate = delegate;
        this.overrideScheme = overrideScheme;
    }

    public String getServiceId() {
        return this.delegate.getServiceId();
    }

    public String getHost() {
        return this.delegate.getHost();
    }

    public int getPort() {
        return this.delegate.getPort();
    }

    public boolean isSecure() {
        return !"https".equals(this.overrideScheme) && !"wss".equals(this.overrideScheme) ? this.delegate.isSecure() : true;
    }

    public URI getUri() {
        return this.delegate.getUri();
    }

    public Map<String, String> getMetadata() {
        return this.delegate.getMetadata();
    }

    public String getScheme() {
        String scheme = this.delegate.getScheme();
        return scheme != null ? scheme : this.overrideScheme;
    }
}
