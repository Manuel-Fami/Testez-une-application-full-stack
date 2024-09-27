package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;

public class TeacherServiceTest {
    @InjectMocks
    private TeacherService teacherService;

    @Mock
    private TeacherRepository teacherRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setFirstName("John");
        teacher1.setLastName("Doe");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setFirstName("Jane");
        teacher2.setLastName("Doe");

        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher1, teacher2));

        List<Teacher> teachers = teacherService.findAll();

        assertNotNull(teachers);
        assertEquals(2, teachers.size());
        assertEquals("John", teachers.get(0).getFirstName());
        assertEquals("Jane", teachers.get(1).getFirstName());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    void testFindById_WhenTeacherExists() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        Teacher foundTeacher = teacherService.findById(1L);

        assertNotNull(foundTeacher);
        assertEquals("John", foundTeacher.getFirstName());
        verify(teacherRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_WhenTeacherDoesNotExist() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        Teacher foundTeacher = teacherService.findById(1L);

        assertNull(foundTeacher);
        verify(teacherRepository, times(1)).findById(1L);
    }
}
