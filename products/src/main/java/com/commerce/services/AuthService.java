package com.commerce.services;

import com.commerce.dto.LoginRequest;
import com.commerce.dto.SignUpRequest;

public interface AuthService {
    String registerUser(SignUpRequest signUpRequest);

    String authenticateUser(LoginRequest loginRequest);
}
