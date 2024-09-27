package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TeacherTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTeacherWithValidData() {
        Teacher teacher = Teacher.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        Set<ConstraintViolation<Teacher>> violations = validator.validate(teacher);
        assertThat(violations).isEmpty(); // Vérifie qu'il n'y a pas de violations
    }

    @Test
    public void testTeacherWithMissingFirstName() {
        Teacher teacher = Teacher.builder()
                .lastName("Doe")
                .build();

        Set<ConstraintViolation<Teacher>> violations = validator.validate(teacher);
        
        // Vérifie que les violations contiennent une entrée pour le champ manquant
        assertFalse(violations.isEmpty(), "Expected validation to have violations");
        assertTrue(violations.stream()
            .anyMatch(violation -> violation.getPropertyPath().toString().equals("firstName")),
            "Expected a violation for the firstName field");
    }

    @Test
    public void testTeacherWithMissingLastName() {
        Teacher teacher = Teacher.builder()
                .firstName("John")
                .build();

        Set<ConstraintViolation<Teacher>> violations = validator.validate(teacher);
        
        // Vérifie que les violations contiennent une entrée pour le champ manquant
        assertFalse(violations.isEmpty(), "Expected validation to have violations");
        assertTrue(violations.stream()
            .anyMatch(violation -> violation.getPropertyPath().toString().equals("lastName")),
            "Expected a violation for the lastName field");
    }

    @Test
    public void testTeacherWithTooLongFirstName() {
        Teacher teacher = Teacher.builder()
                .firstName("ThisNameIsWayTooooLong")
                .lastName("Doe")
                .build();

        Set<ConstraintViolation<Teacher>> violations = validator.validate(teacher);
        
        // Vérifie qu'il y a une violation pour le champ firstName
        assertFalse(violations.isEmpty(), "Expected validation to have violations");
        assertTrue(violations.stream()
            .anyMatch(violation -> violation.getPropertyPath().toString().equals("firstName")),
            "Expected a violation for the firstName field due to size limit");
    }

    @Test
    public void testTeacherWithTooLongLastName() {
        Teacher teacher = Teacher.builder()
                .firstName("John")
                .lastName("ThisLastNameIsWayTooooLong")
                .build();

        Set<ConstraintViolation<Teacher>> violations = validator.validate(teacher);
        
        // Vérifie qu'il y a une violation pour le champ lastName
        assertFalse(violations.isEmpty(), "Expected validation to have violations");
        assertTrue(violations.stream()
            .anyMatch(violation -> violation.getPropertyPath().toString().equals("lastName")),
            "Expected a violation for the lastName field due to size limit");
    }

    //Equals
    @Test
    void testEquals_SameObject() {
        Teacher teacher = new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());
        assertEquals(teacher, teacher, "The same Teacher object should be equal to itself");
    }

    @Test
    void testEquals_EqualObjects() {
        Teacher teacher1 = new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());
        Teacher teacher2 = new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());
        assertEquals(teacher1, teacher2, "Two Teacher objects with the same ID should be equal");
    }

    @Test
    void testEquals_DifferentObjects() {
        Teacher teacher1 = new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());
        Teacher teacher2 = new Teacher(2L, "Smith", "Jane", LocalDateTime.now(), LocalDateTime.now());
        assertNotEquals(teacher1, teacher2, "Two Teacher objects with different IDs should not be equal");
    }

    @Test
    void testEquals_NullObject() {
        Teacher teacher = new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());
        assertNotEquals(teacher, null, "A Teacher object should not be equal to null");
    }

    @Test
    void testEquals_DifferentClass() {
        Teacher teacher = new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());
        String otherObject = "Not a Teacher";
        assertNotEquals(teacher, otherObject, "A Teacher object should not be equal to an object of a different class");
    }

    @Test
    void testEquals_WithNullFields() {
        Teacher teacher1 = new Teacher(1L, null, null, null, null);
        Teacher teacher2 = new Teacher(1L, null, null, null, null);
        assertEquals(teacher1, teacher2, "Two Teacher objects with the same ID and null fields should be equal");
    }

    //Hashcode
    @Test
    void testHashCode_EqualObjects() {
        Teacher teacher1 = new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());
        Teacher teacher2 = new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());
        assertEquals(teacher1.hashCode(), teacher2.hashCode(), "Two equal Teacher objects should have the same hashCode");
    }

    @Test
    void testHashCode_DifferentObjects() {
        Teacher teacher1 = new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());
        Teacher teacher2 = new Teacher(2L, "Smith", "Jane", LocalDateTime.now(), LocalDateTime.now());
        assertNotEquals(teacher1.hashCode(), teacher2.hashCode(), "Two different Teacher objects should have different hashCodes");
    }

    @Test
    void testHashCode_Consistency() {
        Teacher teacher = new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());
        int initialHashCode = teacher.hashCode();
        assertEquals(initialHashCode, teacher.hashCode(), "The hashCode should be consistent when called multiple times on the same object");
    }

    @Test
    void testHashCode_WithNullFields() {
        Teacher teacher1 = new Teacher(1L, null, null, null, null);
        Teacher teacher2 = new Teacher(1L, null, null, null, null);
        assertEquals(teacher1.hashCode(), teacher2.hashCode(), "Two Teacher objects with the same fields (including nulls) should have the same hashCode");
    }

    @Test
    void testHashCode_DifferentFieldsSameID() {
        Teacher teacher1 = new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());
        Teacher teacher2 = new Teacher(1L, "Smith", "Jane", LocalDateTime.now(), LocalDateTime.now());
        assertEquals(teacher1.hashCode(), teacher2.hashCode(), "Two Teacher objects with the same ID but different fields should still have the same hashCode");
    }
}
