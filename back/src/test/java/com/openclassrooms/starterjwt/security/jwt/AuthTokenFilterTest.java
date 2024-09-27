package com.openclassrooms.starterjwt.security.jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;

public class AuthTokenFilterTest {
     @InjectMocks
    private AuthTokenFilter authTokenFilter;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilterInternal_validJwt() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer valid.jwt.token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtUtils.validateJwtToken("valid.jwt.token")).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken("valid.jwt.token")).thenReturn("username");

        UserDetails userDetails = UserDetailsImpl.builder()
            .id(1L)
            .username("username")
            .password("password")
            .admin(true)
            .build();

        when(userDetailsService.loadUserByUsername("username")).thenReturn(userDetails);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        UsernamePasswordAuthenticationToken authenticationToken = 
            (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        assertNotNull(authenticationToken);
        assertEquals("username", authenticationToken.getName());
        assertEquals(userDetails, authenticationToken.getPrincipal());

        verify(filterChain, times(1)).doFilter(request, response);
    }

}
