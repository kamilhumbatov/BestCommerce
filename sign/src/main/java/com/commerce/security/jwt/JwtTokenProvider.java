package com.commerce.security.jwt;

import com.commerce.common.exception.models.UserOrPasswordNotMatchException;
import com.commerce.models.User;
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
import io.jsonwebtoken.security.Keys;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String NAME_KEY="name";
    private static final String AUTHORITIES_KEY="role";

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
            claims.put(NAME_KEY, user.getName());
            claims.put(AUTHORITIES_KEY, user.getRoles().iterator());
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getUsername())
                    .setIssuedAt(now)
                    .setExpiration(expired)
                    .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes()), SignatureAlgorithm.HS512)
                    .compact();
        } else {
            throw new UserOrPasswordNotMatchException();
        }
    }
}
