package com.artostapyshyn.automarketplace.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testAuthorizeSeller() throws Exception {
        String email = "email@gmail.com";
        String password = "password123";
        String response = "You're signed in successfully!";
        
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
        when(authenticationManager.authenticate(authRequest)).thenReturn(new UsernamePasswordAuthenticationToken(email, password));

        mockMvc.perform(post("/sign-in")
        		.contentType(MediaType.APPLICATION_JSON)
                .param("email", email)
                .param("password", password))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string(response));
    }

    @Test
    public void testUnauthorized() throws Exception {
        String email = "email@gmail.com";
        String password = "password123";

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
        when(authenticationManager.authenticate(authRequest)).thenThrow(new BadCredentialsException("Invalid credentials"));

        mockMvc.perform(post("/sign-in")
                .param("email", email)
                .param("password", password))
                .andExpect(status().isUnauthorized());
    }
}