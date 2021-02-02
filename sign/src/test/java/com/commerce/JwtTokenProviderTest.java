package com.commerce;

import com.commerce.common.enums.RoleName;
import com.commerce.common.exception.models.UserOrPasswordNotMatchException;
import com.commerce.models.Role;
import com.commerce.models.User;
import com.commerce.dto.LoginRequest;
import com.commerce.security.jwt.JwtTokenProvider;
import com.commerce.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(JwtTokenProvider.class)
@WebAppConfiguration
@ContextConfiguration(classes = {JwtTokenProvider.class,TestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JwtTokenProviderTest {

    private static final String USERNAME = "kamil";
    private static final String PASSWORD = "123";
    private static final Long USER_ID = 5L;

    private String pass="";

    private User user;
    private Role role;
    private LoginRequest loginRequest;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        role = new Role();
        role.setId(1);
        role.setName(RoleName.ROLE_MERCHANT);
        user = User.builder()
                .id(USER_ID)
                .username(USERNAME)
                .email(USERNAME)
                .build();
        loginRequest=LoginRequest.builder()
                .usernameOrEmail(USERNAME)
                .password(PASSWORD)
                .build();
        pass=passwordEncoder.encode(PASSWORD);
    }

    @Test
    public void shouldGenerateAuthToken() throws Exception {
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setRoles(Collections.singleton(role));
        when(userService.findByUsernameOrEmail(USERNAME,USERNAME)).thenReturn(Optional.of(user));

        assertThat(jwtTokenProvider.generateJwtToken(loginRequest)).isNotEmpty();
        verify(userService, times(1)).findByUsernameOrEmail(USERNAME,USERNAME);
    }

    @Test
    public void shouldUserOrPasswordNotMatchException() throws Exception {
        user.setPassword(passwordEncoder.encode(PASSWORD.concat(PASSWORD)));
        when(userService.findByUsernameOrEmail(USERNAME,USERNAME)).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> jwtTokenProvider.generateJwtToken(loginRequest))
                .isInstanceOf(UserOrPasswordNotMatchException.class)
                .hasMessage("Username or password is wrong!");
        verify(userService, times(1)).findByUsernameOrEmail(USERNAME,USERNAME);
    }
}
