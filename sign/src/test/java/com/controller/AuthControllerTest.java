package com.controller;

import com.commerce.controller.AuthController;
import com.commerce.dto.LoginRequest;
import com.commerce.dto.SignUpRequest;
import com.commerce.services.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
@ContextConfiguration(classes = {AuthController.class})
public class AuthControllerTest {

    private static final String USERNAME = "kamil";
    private static final String PASSWORD = "a123456";
    private final String EMAIL = "kamil@bestcommerce.com";

    private static final String API_SIGNIN = "/api/auth/signin";
    private static final String API_SIGNUP = "/api/auth/signup";
    private static final String YOUR_REGISTRATION_WAS_SUCCESSFUL = "Your registration was successful!";

    private LoginRequest loginRequest;
    private SignUpRequest signUpRequest;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

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
    }

    @Test
    public void authSignInSuccsess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(API_SIGNIN)
                .content(asJsonString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists());
    }

    @Test
    public void registerUser() throws Exception {
        when(authService.registerUser(signUpRequest)).thenReturn(YOUR_REGISTRATION_WAS_SUCCESSFUL);
        mockMvc.perform(MockMvcRequestBuilders
                .post(API_SIGNUP)
                .content(asJsonString(signUpRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists());
    }

    private String asJsonString(final Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

}
