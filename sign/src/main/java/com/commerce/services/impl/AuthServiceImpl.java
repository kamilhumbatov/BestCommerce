package com.commerce.services.impl;

import com.commerce.dto.LoginRequest;
import com.commerce.dto.SignUpRequest;
import com.commerce.enums.RoleName;
import com.commerce.exception.models.EmailAddressAlreadyUseException;
import com.commerce.exception.models.InvalidPasswordFormatException;
import com.commerce.exception.models.UsernameAlreadyTakenException;
import com.commerce.models.User;
import com.commerce.security.jwt.JwtTokenProvider;
import com.commerce.services.AuthService;
import com.commerce.services.RoleService;
import com.commerce.services.UserService;
import com.commerce.utils.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String YOUR_REGISTRATION_WAS_SUCCESSFUL = "Your registration was successful!";
    private final UserService userService;
    private final RoleService roleService;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public String authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateJwtToken(authentication);
    }

    @Override
    public String registerUser(SignUpRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            throw new UsernameAlreadyTakenException();
        }
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailAddressAlreadyUseException();
        }
        if (!PasswordValidator.isValid(signUpRequest.getPassword())) {
            throw new InvalidPasswordFormatException();
        }
        User user = User.builder()
                .name(signUpRequest.getName())
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .roles(Collections.singleton(roleService.findByName(RoleName.ROLE_MERCHANT)))
                .build();


        userService.save(user);
        return YOUR_REGISTRATION_WAS_SUCCESSFUL;
    }
}
