package com.commerce.services.impl;

import com.commerce.models.User;
import com.commerce.repository.UserRepository;
import com.commerce.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    public static final String USER_NOT_FOUND = "User Not Found with username: %s";
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, usernameOrEmail)));
        return UserPrincipal.create(user);
    }
}
