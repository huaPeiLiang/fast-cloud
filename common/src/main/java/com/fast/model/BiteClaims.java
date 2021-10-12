package com.fast.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Martin
 */
@Data
@Builder
public class BiteClaims implements Serializable {

    private static final long serialVersionUID = -674402862690602444L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long accountId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tenantId;

    private Long times;

    private String tokenType;

    private Long loginTime;

}
