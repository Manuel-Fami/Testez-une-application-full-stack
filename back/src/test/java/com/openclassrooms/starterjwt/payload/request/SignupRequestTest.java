package com.openclassrooms.starterjwt.payload.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SignupRequestTest {
     private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidSignupRequest() {
        SignupRequest request = new SignupRequest();
        request.setEmail("john.doe@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("securePassword123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "Expected no validation violations");
    }

    @Test
    void testInvalidEmail() {
        SignupRequest request = new SignupRequest();
        request.setEmail("invalidEmail");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("securePassword123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "Expected validation to have violations for email");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")),
                "Expected a violation for the email field");
    }

    @Test
    void testEmptyFirstName() {
        SignupRequest request = new SignupRequest();
        request.setEmail("john.doe@example.com");
        request.setFirstName("");
        request.setLastName("Doe");
        request.setPassword("securePassword123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "Expected validation to have violations for firstName");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")),
                "Expected a violation for the firstName field");
    }

    @Test
    void testTooShortLastName() {
        SignupRequest request = new SignupRequest();
        request.setEmail("john.doe@example.com");
        request.setFirstName("John");
        request.setLastName("Do");
        request.setPassword("securePassword123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "Expected validation to have violations for lastName");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")),
                "Expected a violation for the lastName field");
    }

    @Test
    void testTooShortPassword() {
        SignupRequest request = new SignupRequest();
        request.setEmail("john.doe@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("12345"); // Too short

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "Expected validation to have violations for password");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")),
                "Expected a violation for the password field");
    }

    @Test
    void testEqualSignupRequest() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("john.doe@example.com");
        request1.setFirstName("John");
        request1.setLastName("Doe");
        request1.setPassword("securePassword123");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("john.doe@example.com");
        request2.setFirstName("John");
        request2.setLastName("Doe");
        request2.setPassword("securePassword123");

        assertEquals(request1, request2, "Two SignupRequest objects with the same properties should be equal");
    }

    @Test
    void testNotEqualSignupRequestDifferentEmail() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("john.doe@example.com");
        request1.setFirstName("John");
        request1.setLastName("Doe");
        request1.setPassword("securePassword123");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("jane.doe@example.com"); // Different email
        request2.setFirstName("John");
        request2.setLastName("Doe");
        request2.setPassword("securePassword123");

        assertNotEquals(request1, request2, "SignupRequest objects with different emails should not be equal");
    }

    @Test
    void testNotEqualSignupRequestDifferentFirstName() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("john.doe@example.com");
        request1.setFirstName("John");
        request1.setLastName("Doe");
        request1.setPassword("securePassword123");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("john.doe@example.com");
        request2.setFirstName("Jane"); // Different first name
        request2.setLastName("Doe");
        request2.setPassword("securePassword123");

        assertNotEquals(request1, request2, "SignupRequest objects with different first names should not be equal");
    }

    @Test
    void testNotEqualSignupRequestDifferentLastName() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("john.doe@example.com");
        request1.setFirstName("John");
        request1.setLastName("Doe");
        request1.setPassword("securePassword123");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("john.doe@example.com");
        request2.setFirstName("John");
        request2.setLastName("Smith"); // Different last name
        request2.setPassword("securePassword123");

        assertNotEquals(request1, request2, "SignupRequest objects with different last names should not be equal");
    }

    @Test
    void testNotEqualSignupRequestDifferentPassword() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("john.doe@example.com");
        request1.setFirstName("John");
        request1.setLastName("Doe");
        request1.setPassword("securePassword123");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("john.doe@example.com");
        request2.setFirstName("John");
        request2.setLastName("Doe");
        request2.setPassword("differentPassword"); // Different password

        assertNotEquals(request1, request2, "SignupRequest objects with different passwords should not be equal");
    }

    @Test
    void testNotEqualSignupRequestNull() {
        SignupRequest request = new SignupRequest();
        request.setEmail("john.doe@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("securePassword123");

        assertNotEquals(request, null, "SignupRequest object should not be equal to null");
    }

    @Test
    void testNotEqualSignupRequestDifferentClass() {
        SignupRequest request = new SignupRequest();
        request.setEmail("john.doe@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("securePassword123");

        assertNotEquals(request, new Object(), "SignupRequest object should not be equal to an object of a different class");
    }

    // Hascode
    @Test
    void testHashCodeEqualObjects() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("john.doe@example.com");
        request1.setFirstName("John");
        request1.setLastName("Doe");
        request1.setPassword("securePassword123");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("john.doe@example.com");
        request2.setFirstName("John");
        request2.setLastName("Doe");
        request2.setPassword("securePassword123");

        assertEquals(request1.hashCode(), request2.hashCode(), "Two equal SignupRequest objects should have the same hashCode");
    }

    @Test
    void testHashCodeDifferentObjects() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("john.doe@example.com");
        request1.setFirstName("John");
        request1.setLastName("Doe");
        request1.setPassword("securePassword123");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("jane.doe@example.com"); // Different email
        request2.setFirstName("Jane"); // Different first name
        request2.setLastName("Smith"); // Different last name
        request2.setPassword("differentPassword"); // Different password

        assertNotEquals(request1.hashCode(), request2.hashCode(), "Two different SignupRequest objects should have different hashCodes");
    }

    @Test
    void testHashCodeConsistency() {
        SignupRequest request = new SignupRequest();
        request.setEmail("john.doe@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("securePassword123");

        int initialHashCode = request.hashCode();
        assertEquals(initialHashCode, request.hashCode(), "The hashCode should be consistent when called multiple times on the same object");
    }

    @Test
    void testHashCodeWithNullFields() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("john.doe@example.com");
        request1.setFirstName(null); // Null first name
        request1.setLastName("Doe");
        request1.setPassword("securePassword123");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("john.doe@example.com");
        request2.setFirstName(null); // Null first name
        request2.setLastName("Doe");
        request2.setPassword("securePassword123");

        assertEquals(request1.hashCode(), request2.hashCode(), "Two SignupRequest objects with same properties (including null) should have the same hashCode");
    }
}
