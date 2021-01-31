package com.commerce.exception.models;

public class EmailAddressAlreadyUseException extends IllegalArgumentException {

    public EmailAddressAlreadyUseException() {
        super("Email Address already in use!");
    }
}
