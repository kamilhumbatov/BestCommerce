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
    @NotNull(message = "Category must not be null!")
    private ProductCategory category;
    @NotNull(message = "Name must not be null!")
    @NotBlank(message = "Name must not be empty!")
    private String name;
    private BigDecimal price;
    @NotNull(message = "Description must not be null!")
    private String description;
    @NotNull(message = "Inventory must not be null!")
    private String inventory;
    @NotNull(message = "PaymentOptions must not be null!")
    private PaymentOptions paymentOptions;
    @NotNull(message = "DeliveryOptions must not be null!")
    private String deliveryOptions;
}
