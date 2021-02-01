package com.commerce;

import com.commerce.security.jwt.JwtTokenProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void shouldGenerateAuthToken() throws Exception {
        String userName = jwtTokenProvider.getUserNameFromJwtToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhcm11ZCIsImlhdCI6MTYxMjEwNzU5NiwiZXhwIjoxNjEyNzEyMzk2fQ.rmGDNbhPwgdJRZt7CfvonNpO7czYgSCBe7Ol3K0Q5b0QrFgihjXoXIoPOmryn8DDA5cO-RdMJGAdEv13RGP-JQ");
        assertNotNull(userName);
    }
}
