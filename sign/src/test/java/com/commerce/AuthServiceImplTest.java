package com.commerce;

import com.commerce.common.enums.RoleName;
import com.commerce.common.exception.models.EmailAddressAlreadyUseException;
import com.commerce.common.exception.models.InvalidPasswordFormatException;
import com.commerce.common.exception.models.UsernameAlreadyTakenException;
import com.commerce.models.Role;
import com.commerce.models.User;
import com.commerce.dto.LoginRequest;
import com.commerce.dto.SignUpRequest;
import com.commerce.security.jwt.JwtTokenProvider;
import com.commerce.services.RoleService;
import com.commerce.services.UserService;
import com.commerce.services.impl.AuthServiceImpl;
import com.commerce.services.kafka.KafkaSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthServiceImpl.class)
@ContextConfiguration(classes = {AuthServiceImpl.class})
public class AuthServiceImplTest {

    private static final Long USER_ID = 5L;
    private final String USERNAME = "kamil";
    private final String EMAIL = "kamil@bestcommerce.com";
    private final String PASSWORD = "a123456";
    private static final String YOUR_REGISTRATION_WAS_SUCCESSFUL = "Your registration was successful!";

    private LoginRequest loginRequest;
    private SignUpRequest signUpRequest;
    private User user;
    private User userNew;
    private Role role;

    @Autowired
    private AuthServiceImpl authService;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private KafkaSender kafkaSender;

    @Before
    public void setUp() {
        loginRequest = LoginRequest.builder()
                .usernameOrEmail(USERNAME)
                .password(PASSWORD)
                .build();
        signUpRequest = SignUpRequest.builder()
                .name(USERNAME)
                .username(USERNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
        role = new Role();
        role.setId(1);
        role.setName(RoleName.ROLE_MERCHANT);
        user = User.builder()
                .name(USERNAME)
                .username(USERNAME)
                .email(EMAIL)
                .roles(Collections.singleton(role))
                .build();
        userNew = User.builder()
                .id(USER_ID)
                .name(USERNAME)
                .username(USERNAME)
                .email(EMAIL)
                .roles(Collections.singleton(role))
                .password(passwordEncoder.encode(PASSWORD))
                .build();
    }

    @Test
    public void givenUsernameOrEmail() {
        String TOKEN="$2a$10$4.jV3xcv1tORCN9szDekKuJcQkJO.8zxti2lIJmoeSKxJRJ.sfd1i";
        when(tokenProvider.generateJwtToken(loginRequest)).thenReturn(TOKEN);

        assertThat(authService.authenticateUser(loginRequest)).isEqualTo(TOKEN);
        verify(tokenProvider, times(1)).generateJwtToken(loginRequest);
    }

    @Test
    public void registerUserName() {
        when(userService.existsByUsername(USERNAME)).thenReturn(false);
        when(userService.existsByEmail(EMAIL)).thenReturn(false);
        when(roleService.findByName(RoleName.ROLE_MERCHANT)).thenReturn(role);
        when(userService.save(user)).thenReturn(userNew);

        assertThat(authService.registerUser(signUpRequest)).isEqualTo(YOUR_REGISTRATION_WAS_SUCCESSFUL);
        verify(userService, times(1)).existsByUsername(USERNAME);
        verify(userService, times(1)).existsByEmail(EMAIL);
        verify(roleService, times(1)).findByName(RoleName.ROLE_MERCHANT);
        verify(userService, times(1)).save(user);
    }

    @Test
    public void registerUserNameUsernameAlreadyTakenException() {
        when(userService.existsByUsername(USERNAME)).thenReturn(true);

        assertThatThrownBy(() -> authService.registerUser(signUpRequest))
                .isInstanceOf(UsernameAlreadyTakenException .class)
                .hasMessage("Username is already taken!");
        verify(userService, times(1)).existsByUsername(USERNAME);
    }

    @Test
    public void registerUserNameEmailAddressAlreadyUseException() {
        when(userService.existsByUsername(USERNAME)).thenReturn(false);
        when(userService.existsByEmail(EMAIL)).thenReturn(true);

        assertThatThrownBy(() -> authService.registerUser(signUpRequest))
                .isInstanceOf(EmailAddressAlreadyUseException.class)
                .hasMessage("Email Address already in use!");
        verify(userService, times(1)).existsByUsername(USERNAME);
        verify(userService, times(1)).existsByUsername(USERNAME);
    }

    @Test
    public void registerUserNameInvalidPasswordFormatException() {
        when(userService.existsByUsername(USERNAME)).thenReturn(false);
        when(userService.existsByEmail(EMAIL)).thenReturn(false);

        signUpRequest.setPassword("123");
        assertThatThrownBy(() -> authService.registerUser(signUpRequest))
                .isInstanceOf(InvalidPasswordFormatException.class)
                .hasMessage("Password must contain alphanumeric and a length of at least 6 characters and a maximum of 20 characters.");
        verify(userService, times(1)).existsByUsername(USERNAME);
        verify(userService, times(1)).existsByUsername(USERNAME);
    }
}