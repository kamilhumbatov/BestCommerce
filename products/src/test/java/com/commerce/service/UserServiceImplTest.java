package com.commerce.service;

import com.commerce.common.models.User;
import com.commerce.repository.UserRepository;
import com.commerce.services.impl.UserServiceImpl;
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
@WebMvcTest(UserServiceImpl.class)
@ContextConfiguration(classes = {UserServiceImpl.class})
public class UserServiceImplTest {

    private static final String username = "kamil";
    private static final Long userId = 5L;

    private User user;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Before
    public void setUp() {
        user = User.builder().id(userId).username(username).email(username).build();
    }

    @Test
    public void givenUsernameOrEmail() {
        when(userRepository.findByUsernameOrEmail(username, username)).thenReturn(Optional.of(user));

        assertThat(userService.findByUsernameOrEmail(username, username).isPresent()).isEqualTo(true);
        verify(userRepository, times(1)).findByUsernameOrEmail(username, username);
    }

    @Test
    public void givenUsernameOrEmailAsUser() {
        when(userRepository.findByUsernameOrEmail(username, username)).thenReturn(Optional.of(user));

        assertThat(userService.findByUsernameOrEmail(username).getUsername()).isEqualTo(username);
        verify(userRepository, times(1)).findByUsernameOrEmail(username, username);
    }
}
