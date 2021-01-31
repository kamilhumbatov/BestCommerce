package com.commerce.models;

import com.commerce.common.models.audit.UserDateAudit;
import com.commerce.enums.PaymentOptions;
import com.commerce.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @Enumerated(EnumType.ORDINAL)
    private ProductCategory category;
    private String name;
    private BigDecimal price;
    private String description;
    private String inventory;
    @Enumerated(EnumType.ORDINAL)
    private PaymentOptions paymentOptions;
    private String deliveryOptions;
}
