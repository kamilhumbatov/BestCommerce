package com.commerce;

import com.commerce.models.User;
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

    private static final String USERNAME = "kamil";
    private static final Long USER_ID = 5L;

    private User user;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Before
    public void setUp() {
        user = User.builder().id(USER_ID).username(USERNAME).email(USERNAME).build();
    }

    @Test
    public void givenUsernameOrEmail() {
        when(userRepository.findByUsernameOrEmail(USERNAME, USERNAME)).thenReturn(Optional.of(user));

        assertThat(userService.findByUsernameOrEmail(USERNAME, USERNAME).isPresent()).isEqualTo(true);
        verify(userRepository, times(1)).findByUsernameOrEmail(USERNAME, USERNAME);
    }

    @Test
    public void givenExistsByUsername() {
        when(userRepository.existsByUsername(USERNAME)).thenReturn(true);

        assertThat(userService.existsByUsername(USERNAME)).isEqualTo(true);
        verify(userRepository, times(1)).existsByUsername(USERNAME);
    }

    @Test
    public void givenExistsByEmail() {
        when(userRepository.existsByEmail(USERNAME)).thenReturn(true);

        assertThat(userService.existsByEmail(USERNAME)).isEqualTo(true);
        verify(userRepository, times(1)).existsByEmail(USERNAME);
    }


    @Test
    public void addUser() {
        when(userRepository.save(user)).thenReturn(user);

        assertThat(userService.save(user).getUsername()).isEqualTo(USERNAME);
        verify(userRepository, times(1)).save(user);
    }
}
