package com.commerce.common.exception.models;

public class UserOrPasswordNotMatchException extends IllegalArgumentException {

    public UserOrPasswordNotMatchException() {
        super("Username or password is wrong!");
    }
}