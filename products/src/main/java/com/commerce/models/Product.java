package com.commerce.models;

import com.commerce.common.models.User;
import com.commerce.enums.PaymentOptions;
import com.commerce.enums.ProductCategory;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "t_products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false,unique = false)
    private User user;
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
