package com.commerce.common.exception.models;

public class AccessDinedProductException extends IllegalArgumentException {

    public AccessDinedProductException() {
        super("You have not access to get this product!");
    }
}
