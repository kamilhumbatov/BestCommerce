package com.commerce;

import com.commerce.common.models.User;
import com.commerce.security.jwt.JwtTokenProvider;
import com.commerce.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(JwtTokenProvider.class)
@ContextConfiguration(classes = {JwtTokenProvider.class})
public class JwtTokenProviderTest {

    private static final String USERNAME = "kamil";
    private static final Long USER_ID = 5L;

    private User user;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private UserService userService;

    @Before
    public void setUp() {
        user = User.builder().id(USER_ID).username(USERNAME).email(USERNAME).build();
    }


    @Test
    public void shouldGenerateAuthToken() throws Exception {
        when(userService.findByUsernameOrEmail(USERNAME,USERNAME)).thenReturn(Optional.of(user));

        assertThat(jwtTokenProvider.generateJwtToken(USERNAME)).isNotEmpty();
        verify(userService, times(1)).findByUsernameOrEmail(USERNAME,USERNAME);
    }
}
