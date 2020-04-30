package com.fast.model.response;

import lombok.Data;

@Data
public class PhoneAvgPriceByBrandResponse {
    private String brand;
    private Long number;
    private Double avgPrice;
}
