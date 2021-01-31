package com.commerce.common.exception.models;

public class UserNotFoundException extends IllegalArgumentException {

    public UserNotFoundException() {
        super("User not found!");
    }
}