package com.commerce.common.exception.models;

public class UsernameAlreadyTakenException extends IllegalArgumentException {

    public UsernameAlreadyTakenException() {
        super("Username is already taken!");
    }
}
