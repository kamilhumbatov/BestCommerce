package com.commerce.dto;

import com.commerce.enums.PaymentOptions;
import com.commerce.enums.ProductCategory;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private Long id;
    private ProductCategory category;
    private String name;
    private BigDecimal price;
    private String description;
    private String inventory;
    private PaymentOptions paymentOptions;
    private String deliveryOptions;
}
