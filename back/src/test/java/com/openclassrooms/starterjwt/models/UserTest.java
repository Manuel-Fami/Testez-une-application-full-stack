package com.openclassrooms.starterjwt.models;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

public class UserTest {
    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUserWithValidData() {
        User user = User.builder()
                .email("john.doe@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("securepassword123")
                .admin(false)
                .build();

        assertThat(validator.validate(user)).isEmpty();
    }

    @Test
    public void testUserWithInvalidEmail() {
        User user = User.builder()
                .email("invalid-email")
                .firstName("John")
                .lastName("Doe")
                .password("securepassword123")
                .admin(false)
                .build();

        // L'assertion devrait déclencher une violation de contrainte
        assertThrows(ConstraintViolationException.class, () -> {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    });
    }
    // equals

      @Test
    void testEquals_SameObject() {
        User user = new User(1L, "john.doe@example.com", "Doe", "John", "password", true, null, null);
        assertEquals(user, user, "The same User object should be equal to itself");
    }

    @Test
    void testEquals_EqualObjects() {
        User user1 = new User(1L, "john.doe@example.com", "Doe", "John", "password", true, null, null);
        User user2 = new User(1L, "john.doe@example.com", "Doe", "John", "password", true, null, null);
        assertEquals(user1, user2, "Two User objects with the same ID should be equal");
    }

    @Test
    void testEquals_DifferentObjects() {
        User user1 = new User(1L, "john.doe@example.com", "Doe", "John", "password", true, null, null);
        User user2 = new User(2L, "jane.doe@example.com", "Doe", "Jane", "password", false, null, null);
        assertNotEquals(user1, user2, "Two User objects with different IDs should not be equal");
    }

    @Test
    void testEquals_NullObject() {
        User user = new User(1L, "john.doe@example.com", "Doe", "John", "password", true, null, null);
        assertNotEquals(user, null, "A User object should not be equal to null");
    }

    @Test
    void testEquals_DifferentClass() {
        User user = new User(1L, "john.doe@example.com", "Doe", "John", "password", true, null, null);
        String otherObject = "Not a User";
        assertNotEquals(user, otherObject, "A User object should not be equal to an object of a different class");
    }

    //hashcode
    @Test
    void testHashCode_EqualObjects() {
        User user1 = new User(1L, "john.doe@example.com", "Doe", "John", "password", true, null, null);
        User user2 = new User(1L, "john.doe@example.com", "Doe", "John", "password", true, null, null);
        assertEquals(user1.hashCode(), user2.hashCode(), "Two equal User objects should have the same hashCode");
    }

    @Test
    void testHashCode_DifferentObjects() {
        User user1 = new User(1L, "john.doe@example.com", "Doe", "John", "password", true, null, null);
        User user2 = new User(2L, "jane.doe@example.com", "Doe", "Jane", "password", false, null, null);
        assertNotEquals(user1.hashCode(), user2.hashCode(), "Two different User objects should have different hashCodes");
    }

    @Test
    void testHashCode_Consistency() {
        User user = new User(1L, "john.doe@example.com", "Doe", "John", "password", true, null, null);
        int initialHashCode = user.hashCode();
        assertEquals(initialHashCode, user.hashCode(), "The hashCode should be consistent when called multiple times on the same object");
    }

    //toString
    @Test
    void testToString() {
        User user = new User(1L, "john.doe@example.com", "Doe", "John", "password", true, null, null);
        String expected = "User(id=1, email=john.doe@example.com, lastName=Doe, firstName=John, password=password, admin=true, createdAt=null, updatedAt=null)";
        assertEquals(expected, user.toString(), "The toString method should return the expected string representation of the User object");
    }

}
