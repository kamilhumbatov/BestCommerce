package com.commerce.dto;

import com.commerce.enums.PaymentOptions;
import com.commerce.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private ProductCategory category;
    @NotNull(message = "Name must not be null!")
    @NotBlank(message = "Name must not be empty!")
    private String name;
    private BigDecimal price;
    private String description;
    private String inventory;
    private PaymentOptions paymentOptions;
    private String deliveryOptions;
}
