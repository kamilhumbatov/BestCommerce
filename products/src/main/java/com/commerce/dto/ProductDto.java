package com.commerce.dto;

import com.commerce.enums.PaymentOptions;
import com.commerce.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
