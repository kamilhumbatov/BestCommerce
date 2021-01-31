package com.commerce.services;

import com.commerce.common.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User save(User user);
}
