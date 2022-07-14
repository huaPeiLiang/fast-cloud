package com.fast.model.account.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountUpdateRequest {

    private String firstName;
    private String lastName;
    private Integer accountAge;
    private BigDecimal accountHeight;
}
