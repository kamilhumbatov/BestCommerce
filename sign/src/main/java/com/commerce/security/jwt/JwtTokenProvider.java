package com.commerce.security.jwt;

import com.commerce.common.exception.models.UserNotFoundException;
import com.commerce.common.models.User;
import com.commerce.services.UserService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${security.jwtProperties.secret}")
    private String jwtSecretKey;

    @Value("${security.jwtProperties.token-validity}")
    private int jwtExpirationInMs;

    private final UserService userService;

    public String generateJwtToken(String username) {
        User user = userService.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UserNotFoundException());
        Date now = new Date();
        Date expired=new Date(now.getTime() + jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();
    }
}
