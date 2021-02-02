package com.commerce.models;

import com.commerce.common.models.audit.UserDateAudit;
import com.commerce.enums.PaymentOptions;
import com.commerce.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_products")
public class Product extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private ProductCategory category;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    private BigDecimal price;
    @NotNull
    private String description;
    @NotNull
    private String inventory;
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private PaymentOptions paymentOptions;
    @NotNull
    private String deliveryOptions;
}
