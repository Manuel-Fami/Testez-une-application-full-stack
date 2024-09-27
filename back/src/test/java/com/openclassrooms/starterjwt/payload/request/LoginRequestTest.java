package com.openclassrooms.starterjwt.payload.request;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginRequestTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john.doe@example.com");
        loginRequest.setPassword("securepassword");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        assertTrue(violations.isEmpty(), "Expected no validation violations for valid login request");
    }

    @Test
    public void testLoginRequestWithMissingEmail() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(null); // or use an empty string loginRequest.setEmail("");

        loginRequest.setPassword("securepassword");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        assertFalse(violations.isEmpty(), "Expected validation to have violations for missing email");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")),
                "Expected a violation for the email field");
    }

    @Test
    public void testLoginRequestWithMissingPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john.doe@example.com");
        loginRequest.setPassword(null); // or use an empty string loginRequest.setPassword("");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        assertFalse(violations.isEmpty(), "Expected validation to have violations for missing password");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")),
                "Expected a violation for the password field");
    }

    @Test
    public void testLoginRequestWithEmptyEmail() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(""); // setting email to an empty string
        loginRequest.setPassword("securepassword");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        assertFalse(violations.isEmpty(), "Expected validation to have violations for empty email");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")),
                "Expected a violation for the email field");
    }

    @Test
    public void testLoginRequestWithEmptyPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john.doe@example.com");
        loginRequest.setPassword(""); // setting password to an empty string

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        assertFalse(violations.isEmpty(), "Expected validation to have violations for empty password");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")),
                "Expected a violation for the password field");
    }
}
