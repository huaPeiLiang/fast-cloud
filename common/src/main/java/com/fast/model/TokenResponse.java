package com.fast.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Martin
 * @Date 2021/8/9 13:40
 * @remark
 */
@Data
@Builder
public class TokenResponse implements Serializable {

    private static final long serialVersionUID = -935438290887411032L;

    private String accessToken;

    private String refreshToken;
}
