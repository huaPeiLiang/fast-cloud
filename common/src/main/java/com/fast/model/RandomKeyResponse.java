package com.fast.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.security.Key;

/**
 * @Author Martin
 */
@Data
@Builder
public class RandomKeyResponse implements Serializable {

    private static final long serialVersionUID = 3178710556025896517L;

    private String mapKey;
    private Key key;
}
