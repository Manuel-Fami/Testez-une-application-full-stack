package com.openclassrooms.starterjwt.payload.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class JwtResponseTest {
     @Test
    void testSetToken() {
        JwtResponse jwtResponse = new JwtResponse("accessToken", 1L, "username", "firstName", "lastName", true);
        jwtResponse.setToken("newToken");
        assertEquals("newToken", jwtResponse.getToken());
    }

    @Test
    void testSetType() {
        JwtResponse jwtResponse = new JwtResponse("accessToken", 1L, "username", "firstName", "lastName", true);
        jwtResponse.setType("NewType");
        assertEquals("NewType", jwtResponse.getType());
    }

    @Test
    void testSetId() {
        JwtResponse jwtResponse = new JwtResponse("accessToken", 1L, "username", "firstName", "lastName", true);
        jwtResponse.setId(2L);
        assertEquals(2L, jwtResponse.getId());
    }

    @Test
    void testSetUsername() {
        JwtResponse jwtResponse = new JwtResponse("accessToken", 1L, "username", "firstName", "lastName", true);
        jwtResponse.setUsername("newUsername");
        assertEquals("newUsername", jwtResponse.getUsername());
    }

    @Test
    void testSetFirstName() {
        JwtResponse jwtResponse = new JwtResponse("accessToken", 1L, "username", "firstName", "lastName", true);
        jwtResponse.setFirstName("newFirstName");
        assertEquals("newFirstName", jwtResponse.getFirstName());
    }

    @Test
    void testSetLastName() {
        JwtResponse jwtResponse = new JwtResponse("accessToken", 1L, "username", "firstName", "lastName", true);
        jwtResponse.setLastName("newLastName");
        assertEquals("newLastName", jwtResponse.getLastName());
    }

    @Test
    void testSetAdmin() {
        JwtResponse jwtResponse = new JwtResponse("accessToken", 1L, "username", "firstName", "lastName", true);
        jwtResponse.setAdmin(false);
        assertFalse(jwtResponse.getAdmin());
    }
}
