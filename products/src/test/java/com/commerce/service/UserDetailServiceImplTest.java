package com.commerce.service;

import com.commerce.common.models.User;
import com.commerce.repository.UserRepository;
import com.commerce.services.impl.UserDetailsServiceImpl;
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
@WebMvcTest(UserDetailServiceImplTest.class)
@ContextConfiguration(classes = {UserDetailServiceImplTest.class})
public class UserDetailServiceImplTest {

    private static final String USERNAME = "kamil";
    private static final Long USER_ID = 5L;

    private User user;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Before
    public void setUp() {
        user = User.builder().id(USER_ID).username(USERNAME).email(USERNAME).build();
    }

    @Test
    public void givenUsernameOrEmail() {
        when(userRepository.findByUsernameOrEmail(USERNAME, USERNAME)).thenReturn(Optional.of(user));

        assertThat(userDetailsServiceImpl.loadUserByUsername(USERNAME).getUsername()).isEqualTo(USERNAME);
        verify(userRepository, times(1)).findByUsernameOrEmail(USERNAME, USERNAME);
    }
}
