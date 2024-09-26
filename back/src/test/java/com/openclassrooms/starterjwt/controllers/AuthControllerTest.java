package com.openclassrooms.starterjwt.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

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

    // @Mock
    // private AuthenticationManager authenticationManager;

    // @Mock
    // private UserRepository userRepository;

    // @Mock
    // private JwtUtils jwtUtils;

    // @InjectMocks
    // private AuthController authController;

    // @BeforeEach
    // public void setup() {
    //     MockitoAnnotations.openMocks(this);
    //     mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    // }

    // @Test
    // public void testAuthenticateUser_Success() throws Exception {
    //     // Créer un LoginRequest
    //     String email = "yoga@studio.com"; // e-mail Admin
    //     String password = "test!1234"; // mot de passe Admin

    //     LoginRequest loginRequest = new LoginRequest();
    //     loginRequest.setEmail(email);
    //     loginRequest.setPassword(password);

    //     // Simuler l'authentification
    //     UserDetailsImpl userDetails = new UserDetailsImpl();
    //     userDetails.setUsername(email);
    //     userDetails.setId(1L);
    //     userDetails.setFirstName("Admin");
    //     userDetails.setLastName("Admin");

    //     Authentication authentication = mock(Authentication.class);
    //     when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
    //             .thenReturn(authentication);
    //     when(authentication.getPrincipal()).thenReturn(userDetails);
        
    //     String jwt = "mock-jwt-token";
    //     when(jwtUtils.generateJwtToken(authentication)).thenReturn(jwt);
        
    //     User user = new User();
    //     // user.setId(1L);
    //     user.setAdmin(true);
    //     when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

    //     // Exécuter le test
    //     mockMvc.perform(post("/api/auth/login")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.token").value(jwt))
    //             .andExpect(jsonPath("$.username").value(userDetails.getUsername()))
    //             .andExpect(jsonPath("$.firstName").value("Admin"))
    //             .andExpect(jsonPath("$.lastName").value("Admin"))
    //             .andExpect(jsonPath("$.admin").value(true));
    // }

    @Test
    public void testLogin_success() throws Exception {	 

        String email = "john@email.com";
        String password = "password";

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.username").value(email))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("DOE"))
                .andExpect(jsonPath("$.admin").value(false));
        
    }

    @Test
    public void testLogin_invalidUser_shouldReturnUnauthorized() throws Exception {	 
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
    public void testRegister_alreadyExistingUser_shouldReturnBadRequest() throws Exception {
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
    public void testRegister_missingParameter_shouldReturnBadRequest() throws Exception {
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
    public void testRegister_success() throws Exception {
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
