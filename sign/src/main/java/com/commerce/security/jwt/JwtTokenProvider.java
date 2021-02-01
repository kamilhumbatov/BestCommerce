package com.commerce.security.jwt;

import com.commerce.common.exception.models.UserOrPasswordNotMatchException;
import com.commerce.common.models.User;
import com.commerce.dto.LoginRequest;
import com.commerce.services.UserService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${security.jwtProperties.secret}")
    private String jwtSecretKey;

    @Value("${security.jwtProperties.token-validity}")
    private int jwtExpirationInMs;

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public String generateJwtToken(LoginRequest loginRequest) {
        User user = userService.findByUsernameOrEmail(
                loginRequest.getUsernameOrEmail(), loginRequest.getUsernameOrEmail())
                .orElseThrow(() -> new UserOrPasswordNotMatchException());

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            Date now = new Date();
            Date expired = new Date(now.getTime() + jwtExpirationInMs);
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getUsername())
                    .setIssuedAt(now)
                    .setExpiration(expired)
                    .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                    .compact();
        } else {
            throw new UserOrPasswordNotMatchException();
        }
    }
}
