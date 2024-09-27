package com.openclassrooms.starterjwt.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import static org.hamcrest.Matchers.is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthControllerTest {
    @Autowired
	private MockMvc mockMvc;

    @Test
    public void testLoginSuccess() throws Exception {	 
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john@email.com");
        loginRequest.setPassword("password");
        String jsonLoginRequest = new ObjectMapper().writeValueAsString(loginRequest);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonLoginRequest)) 
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.username").value(loginRequest.getEmail()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("DOE"))
                .andExpect(jsonPath("$.admin").value(false));
    }

    @Test
    public void testLoginInvalidUserShouldReturnUnauthorized() throws Exception {	 
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("invalidUser@test.com");
        loginRequest.setPassword("invalidUserPwd");
        String jsonLoginRequest = new ObjectMapper().writeValueAsString(loginRequest);

        mockMvc.perform(post("/api/auth/login") 
                   .contentType(MediaType.APPLICATION_JSON) 
                   .content(jsonLoginRequest)) 
                   .andExpect(status().isUnauthorized()); 
    }

    @Test
    public void testRegisterAlreadyExistingUserShouldReturnBadRequest() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("john@email.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("DOE");
        signupRequest.setPassword("password");
        String jsonSignupRequest = new ObjectMapper().writeValueAsString(signupRequest);
        
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSignupRequest)) 
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Email is already taken!")));
    }

    @Test
    public void testRegisterMissingParameterShouldReturnBadRequest() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("newuser@email.com");
        signupRequest.setFirstName("new");
        signupRequest.setLastName("USER");
        String jsonSignupRequest = new ObjectMapper().writeValueAsString(signupRequest);

        
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSignupRequest)) 
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("newuser@email.com");
        signupRequest.setFirstName("new");
        signupRequest.setLastName("USER");
        signupRequest.setPassword("password");
        String jsonSignupRequest = new ObjectMapper().writeValueAsString(signupRequest);
        
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSignupRequest)) 
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("User registered successfully!")));
    }
}
