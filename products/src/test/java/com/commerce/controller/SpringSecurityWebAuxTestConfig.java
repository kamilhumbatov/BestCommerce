package com.commerce.controller;

import com.commerce.common.models.User;
import com.commerce.security.UserPrincipal;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        UserPrincipal basicActiveUser = new UserPrincipal(1L,"Basic User","user@bestcommerce.com","user@bestcommerce.com","", Arrays.asList(
                new SimpleGrantedAuthority("ROLE_MERCHANT")
        ));
        return new InMemoryUserDetailsManager(Arrays.asList(
                basicActiveUser
        ));
    }
}
