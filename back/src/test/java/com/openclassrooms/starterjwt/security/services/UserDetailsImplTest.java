package com.openclassrooms.starterjwt.security.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class UserDetailsImplTest {
    // equals
    @Test
    void testEquals_SameObject() {
        UserDetailsImpl user = UserDetailsImpl.builder()
                .id(1L)
                .username("user@example.com")
                .firstName("John")
                .lastName("Doe")
                .admin(true)
                .password("password")
                .build();
        assertEquals(user, user, "The same UserDetailsImpl object should be equal to itself");
    }

    @Test
    void testEquals_EqualObjects() {
        UserDetailsImpl user1 = UserDetailsImpl.builder()
                .id(1L)
                .username("user@example.com")
                .firstName("John")
                .lastName("Doe")
                .admin(true)
                .password("password")
                .build();

        UserDetailsImpl user2 = UserDetailsImpl.builder()
                .id(1L)
                .username("user@example.com")
                .firstName("John")
                .lastName("Doe")
                .admin(true)
                .password("password")
                .build();

        assertEquals(user1, user2, "Two UserDetailsImpl objects with the same ID should be equal");
    }

    @Test
    void testEquals_DifferentObjects() {
        UserDetailsImpl user1 = UserDetailsImpl.builder()
                .id(1L)
                .username("user1@example.com")
                .firstName("John")
                .lastName("Doe")
                .admin(true)
                .password("password1")
                .build();

        UserDetailsImpl user2 = UserDetailsImpl.builder()
                .id(2L)
                .username("user2@example.com")
                .firstName("Jane")
                .lastName("Doe")
                .admin(false)
                .password("password2")
                .build();

        assertNotEquals(user1, user2, "Two UserDetailsImpl objects with different IDs should not be equal");
    }

    @Test
    void testEquals_NullObject() {
        UserDetailsImpl user = UserDetailsImpl.builder()
                .id(1L)
                .username("user@example.com")
                .firstName("John")
                .lastName("Doe")
                .admin(true)
                .password("password")
                .build();

        assertNotEquals(user, null, "A UserDetailsImpl object should not be equal to null");
    }

    @Test
    void testEquals_DifferentClass() {
        UserDetailsImpl user = UserDetailsImpl.builder()
                .id(1L)
                .username("user@example.com")
                .firstName("John")
                .lastName("Doe")
                .admin(true)
                .password("password")
                .build();

        String otherObject = "Not a UserDetailsImpl";
        assertNotEquals(user, otherObject, "A UserDetailsImpl object should not be equal to an object of a different class");
    }

    @Test
    void testEquals_WithNullFields() {
        UserDetailsImpl user1 = UserDetailsImpl.builder()
                .id(1L)
                .build();

        UserDetailsImpl user2 = UserDetailsImpl.builder()
                .id(1L)
                .build();

        assertEquals(user1, user2, "Two UserDetailsImpl objects with the same ID and null fields should be equal");
    }

    @Test
    void testEquals_SameIdDifferentFields() {
        UserDetailsImpl user1 = UserDetailsImpl.builder()
                .id(1L)
                .username("user1@example.com")
                .firstName("John")
                .lastName("Doe")
                .admin(true)
                .password("password1")
                .build();

        UserDetailsImpl user2 = UserDetailsImpl.builder()
                .id(1L)
                .username("user2@example.com")
                .firstName("Jane")
                .lastName("Smith")
                .admin(false)
                .password("password2")
                .build();

        assertEquals(user1, user2, "Two UserDetailsImpl objects with the same ID should be equal, even if other fields differ");
    }

    //setter
    @Test
    void testSetId() {
        UserDetailsImpl user = new UserDetailsImpl();
        Long id = 1L;
        user.setId(id);
        assertEquals(id, user.getId(), "The ID should be correctly set.");
    }

    @Test
    void testSetUsername() {
        UserDetailsImpl user = new UserDetailsImpl();
        String username = "user@example.com";
        user.setUsername(username);
        assertEquals(username, user.getUsername(), "The username should be correctly set.");
    }

    @Test
    void testSetFirstName() {
        UserDetailsImpl user = new UserDetailsImpl();
        String firstName = "John";
        user.setFirstName(firstName);
        assertEquals(firstName, user.getFirstName(), "The first name should be correctly set.");
    }

    @Test
    void testSetLastName() {
        UserDetailsImpl user = new UserDetailsImpl();
        String lastName = "Doe";
        user.setLastName(lastName);
        assertEquals(lastName, user.getLastName(), "The last name should be correctly set.");
    }

    @Test
    void testSetAdmin() {
        UserDetailsImpl user = new UserDetailsImpl();
        Boolean admin = true;
        user.setAdmin(admin);
        assertEquals(admin, user.getAdmin(), "The admin status should be correctly set.");
    }

    @Test
    void testSetPassword() {
        UserDetailsImpl user = new UserDetailsImpl();
        String password = "password";
        user.setPassword(password);
        assertEquals(password, user.getPassword(), "The password should be correctly set.");
    }
}
