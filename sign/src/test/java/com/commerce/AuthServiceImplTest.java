package com.commerce;

import com.commerce.common.enums.RoleName;
import com.commerce.common.exception.models.RolenameInvalidException;
import com.commerce.common.models.Role;
import com.commerce.repository.RoleRepository;
import com.commerce.security.jwt.JwtTokenProvider;
import com.commerce.services.RoleService;
import com.commerce.services.impl.AuthServiceImpl;
import com.commerce.services.impl.RoleServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthServiceImpl.class)
@ContextConfiguration(classes = {AuthServiceImpl.class})
public class AuthServiceImplTest {

    private final String USERNAME="kamil";
    private final String PASSWORD="123";

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Before
    public void setUp() {
    }

    @Test
    public void givenUsernameOrEmail() {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(USERNAME,PASSWORD));
        tokenProvider.generateJwtToken(authentication);
    }

}
