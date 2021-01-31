package com.commerce.models;

import com.commerce.enums.PaymentOptions;
import com.commerce.enums.ProductCategory;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private ProductCategory category;
    private String name;
    private String description;
    private String inventory;
    @Enumerated(EnumType.ORDINAL)
    private PaymentOptions paymentOptions;
    private String deliveryOptions;
}
