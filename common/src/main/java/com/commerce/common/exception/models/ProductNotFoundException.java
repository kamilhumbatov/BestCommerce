package com.commerce.common.exception.models;

public class ProductNotFoundException extends IllegalArgumentException {

    public ProductNotFoundException() {
        super("Product not found!");
    }
}