package com.commerce.exception.models;

public class UsernameAlreadyTakenException extends IllegalArgumentException {

    public UsernameAlreadyTakenException() {
        super("Username is already taken!");
    }
}
