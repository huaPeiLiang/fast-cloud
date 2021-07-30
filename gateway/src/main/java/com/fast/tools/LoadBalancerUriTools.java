//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.fast.tools;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class LoadBalancerUriTools {
    private static final String PERCENTAGE_SIGN = "%";
    private static final String DEFAULT_SCHEME = "http";
    private static final String DEFAULT_SECURE_SCHEME = "https";
    private static final Map<String, String> INSECURE_SCHEME_MAPPINGS = new HashMap();

    private LoadBalancerUriTools() {
        throw new IllegalStateException("Can't instantiate a utility class");
    }

    private static boolean containsEncodedParts(URI uri) {
        boolean encoded = uri.getRawQuery() != null && uri.getRawQuery().contains("%") || uri.getRawPath() != null && uri.getRawPath().contains("%") || uri.getRawFragment() != null && uri.getRawFragment().contains("%");
        if (encoded) {
            try {
                UriComponentsBuilder.fromUri(uri).build(true);
                return true;
            } catch (IllegalArgumentException var3) {
                return false;
            }
        } else {
            return false;
        }
    }

    private static int computePort(int port, String scheme) {
        if (port >= 0) {
            return port;
        } else {
            return Objects.equals(scheme, "https") ? 443 : 80;
        }
    }

    public static URI reconstructURI(ServiceInstance serviceInstance, URI original) {
        if (serviceInstance == null) {
            throw new IllegalArgumentException("Service Instance cannot be null.");
        } else {
            return doReconstructURI(serviceInstance, original);
        }
    }

    private static URI doReconstructURI(ServiceInstance serviceInstance, URI original) {
        String host = serviceInstance.getHost();
        String scheme = (String)Optional.ofNullable(serviceInstance.getScheme()).orElse(computeScheme(original, serviceInstance));
        int port = computePort(serviceInstance.getPort(), scheme);
        if (Objects.equals(host, original.getHost()) && port == original.getPort() && Objects.equals(scheme, original.getScheme())) {
            return original;
        } else {
            boolean encoded = containsEncodedParts(original);
            return UriComponentsBuilder.fromUri(original).scheme(scheme).host(host).port(port).build(encoded).toUri();
        }
    }

    private static String computeScheme(URI original, ServiceInstance serviceInstance) {
        String originalOrDefault = (String)Optional.ofNullable(original.getScheme()).orElse("http");
        return serviceInstance.isSecure() && INSECURE_SCHEME_MAPPINGS.containsKey(originalOrDefault) ? (String)INSECURE_SCHEME_MAPPINGS.get(originalOrDefault) : originalOrDefault;
    }

    static {
        INSECURE_SCHEME_MAPPINGS.put("http", "https");
        INSECURE_SCHEME_MAPPINGS.put("ws", "wss");
    }
}
