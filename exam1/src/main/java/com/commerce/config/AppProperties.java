package com.commerce.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Data
@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String jwtSecret;
    private Long jwtExpirationMs;
}
