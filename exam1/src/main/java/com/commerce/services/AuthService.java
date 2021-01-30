package com.commerce.services;

import com.commerce.dto.LoginRequest;
import com.commerce.dto.SignUpRequest;
import com.commerce.models.User;

public interface AuthService {
    User registerUser(SignUpRequest signUpRequest);

    String authenticateUser(LoginRequest loginRequest);
}
