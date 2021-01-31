package com.commerce.services.impl;

import com.commerce.common.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsernameOrEmail(String username, String email);
    User findByUsernameOrEmail(String usernameOrEmail);
}
