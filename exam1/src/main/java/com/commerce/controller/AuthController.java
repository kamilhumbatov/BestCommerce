package com.commerce.controller;

import com.commerce.dto.AuthenticationResponse;
import com.commerce.dto.LoginRequest;
import com.commerce.dto.ResponseModel;
import com.commerce.dto.SignUpRequest;
import com.commerce.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseModel<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseModel.ok(new AuthenticationResponse(authService.authenticateUser(loginRequest)));
    }

    @PostMapping("/signup")
    public ResponseModel<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseModel.ok(authService.registerUser(signUpRequest));
    }
}
