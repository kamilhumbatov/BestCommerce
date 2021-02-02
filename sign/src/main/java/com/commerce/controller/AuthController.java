package com.commerce.controller;

import com.commerce.dto.AuthenticationResponse;
import com.commerce.dto.LoginRequest;
import com.commerce.dto.ResponseModel;
import com.commerce.dto.SignUpRequest;
import com.commerce.services.AuthService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("Sign API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseModel<AuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseModel.ok(new AuthenticationResponse(authService.authenticateUser(loginRequest)));
    }

    @PostMapping("/signup")
    public ResponseModel<String> registerUser(@Validated @RequestBody SignUpRequest signUpRequest) {
        return ResponseModel.ok(authService.registerUser(signUpRequest));
    }
}
