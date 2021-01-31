package com.commerce.exception.models;

public class InvalidPasswordFormatException extends IllegalArgumentException {

    public InvalidPasswordFormatException() {
        super("Password must contain alphanumeric and a length of at least 6 characters and a maximum of 20 characters.");
    }
}