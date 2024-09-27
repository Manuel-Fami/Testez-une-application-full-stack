package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SessionTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testSessionWithValidData() {
        Teacher teacher = new Teacher(); // Assurez-vous de créer un objet Teacher valide ou de le moquer
        teacher.setId(1L); // Définir un ID pour le teacher

        Session session = Session.builder()
                .name("Yoga Session")
                .date(new Date())
                .description("A relaxing yoga session.")
                .teacher(teacher)
                .users(List.of()) // Optionnel : ajouter des utilisateurs si nécessaire
                .build();

        Set<ConstraintViolation<Session>> violations = validator.validate(session);
        
        // Vérifie qu'il n'y a pas de violations
        assertThat(violations).isEmpty();
    }

    @Test
    public void testSessionWithMissingName() {
        Session session = Session.builder()
                .date(new Date())
                .description("A session without a name.")
                .build();

        Set<ConstraintViolation<Session>> violations = validator.validate(session);
        
        // Vérifie qu'il y a une violation pour le champ name
        assertFalse(violations.isEmpty(), "Expected validation to have violations");
        assertTrue(violations.stream()
            .anyMatch(violation -> violation.getPropertyPath().toString().equals("name")),
            "Expected a violation for the name field");
    }

    @Test
    public void testSessionWithTooLongName() {
        Session session = Session.builder()
                .name("ThisNameIsWayTooooooLongAndShouldNotBeAllowed")
                .date(new Date())
                .description("A session with a long name.")
                .build();

        Set<ConstraintViolation<Session>> violations = validator.validate(session);
        
        // Vérifie qu'il y a une violation pour le champ name
        assertFalse(violations.isEmpty(), "Expected validation to have violations");
        assertTrue(violations.stream()
            .anyMatch(violation -> violation.getPropertyPath().toString().equals("name")),
            "Expected a violation for the name field due to size limit");
    }

    @Test
    public void testSessionWithMissingDate() {
        Session session = Session.builder()
                .name("Yoga Session")
                .description("A session without a date.")
                .build();

        Set<ConstraintViolation<Session>> violations = validator.validate(session);
        
        // Vérifie qu'il y a une violation pour le champ date
        assertFalse(violations.isEmpty(), "Expected validation to have violations");
        assertTrue(violations.stream()
            .anyMatch(violation -> violation.getPropertyPath().toString().equals("date")),
            "Expected a violation for the date field");
    }

    @Test
    public void testSessionWithMissingDescription() {
        Session session = Session.builder()
                .name("Yoga Session")
                .date(new Date())
                .build();

        Set<ConstraintViolation<Session>> violations = validator.validate(session);
        
        // Vérifie qu'il y a une violation pour le champ description
        assertFalse(violations.isEmpty(), "Expected validation to have violations");
        assertTrue(violations.stream()
            .anyMatch(violation -> violation.getPropertyPath().toString().equals("description")),
            "Expected a violation for the description field");
    }

    // Equals
    @Test
    void testEquals_SameObject() {
        Session session = new Session(1L, "Yoga Session", new Date(), "A relaxing yoga session", null, null, null, null);
        assertEquals(session, session, "The same Session object should be equal to itself");
    }

    @Test
    void testEquals_EqualObjects() {
        Session session1 = new Session(1L, "Yoga Session", new Date(), "A relaxing yoga session", null, null, null, null);
        Session session2 = new Session(1L, "Yoga Session", new Date(), "A relaxing yoga session", null, null, null, null);
        assertEquals(session1, session2, "Two Session objects with the same ID should be equal");
    }

    @Test
    void testEquals_DifferentObjects() {
        Session session1 = new Session(1L, "Yoga Session", new Date(), "A relaxing yoga session", null, null, null, null);
        Session session2 = new Session(2L, "Meditation Session", new Date(), "A calming meditation session", null, null, null, null);
        assertNotEquals(session1, session2, "Two Session objects with different IDs should not be equal");
    }

    @Test
    void testEquals_NullObject() {
        Session session = new Session(1L, "Yoga Session", new Date(), "A relaxing yoga session", null, null, null, null);
        assertNotEquals(session, null, "A Session object should not be equal to null");
    }

    @Test
    void testEquals_DifferentClass() {
        Session session = new Session(1L, "Yoga Session", new Date(), "A relaxing yoga session", null, null, null, null);
        String otherObject = "Not a Session";
        assertNotEquals(session, otherObject, "A Session object should not be equal to an object of a different class");
    }

    @Test
    void testEquals_WithNullFields() {
        Session session1 = new Session(1L, null, null, null, null, null, null, null);
        Session session2 = new Session(1L, null, null, null, null, null, null, null);
        assertEquals(session1, session2, "Two Session objects with the same ID and null fields should be equal");
    }

    //hashcode
    @Test
    void testHashCode_EqualObjects() {
        Session session1 = new Session(1L, "Yoga Session", new Date(), "A relaxing yoga session", null, null, null, null);
        Session session2 = new Session(1L, "Yoga Session", new Date(), "A relaxing yoga session", null, null, null, null);
        assertEquals(session1.hashCode(), session2.hashCode(), "Two equal Session objects should have the same hashCode");
    }

    @Test
    void testHashCode_DifferentObjects() {
        Session session1 = new Session(1L, "Yoga Session", new Date(), "A relaxing yoga session", null, null, null, null);
        Session session2 = new Session(2L, "Meditation Session", new Date(), "A calming meditation session", null, null, null, null);
        assertNotEquals(session1.hashCode(), session2.hashCode(), "Two different Session objects should have different hashCodes");
    }

    @Test
    void testHashCode_Consistency() {
        Session session = new Session(1L, "Yoga Session", new Date(), "A relaxing yoga session", null, null, null, null);
        int initialHashCode = session.hashCode();
        assertEquals(initialHashCode, session.hashCode(), "The hashCode should be consistent when called multiple times on the same object");
    }

    @Test
    void testHashCode_WithNullFields() {
        Session session1 = new Session(1L, null, null, null, null, null, null, null);
        Session session2 = new Session(1L, null, null, null, null, null, null, null);
        assertEquals(session1.hashCode(), session2.hashCode(), "Two Session objects with the same fields (including nulls) should have the same hashCode");
    }

    @Test
    void testHashCode_DifferentFieldsSameID() {
        Session session1 = new Session(1L, "Yoga Session", new Date(), "A relaxing yoga session", null, null, null, null);
        Session session2 = new Session(1L, "Meditation Session", new Date(), "A calming meditation session", null, null, null, null);
        assertEquals(session1.hashCode(), session2.hashCode(), "Two Session objects with the same ID but different fields should still have the same hashCode");
    }

}
