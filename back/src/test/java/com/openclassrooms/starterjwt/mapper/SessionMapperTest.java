package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;

public class SessionMapperTest {
    @InjectMocks
    private SessionMapper sessionMapper = Mappers.getMapper(SessionMapper.class);

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToEntity() {
        // Mocking the Teacher and User services
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        
        when(teacherService.findById(1L)).thenReturn(teacher);

        User user = new User();
        user.setId(2L);
        when(userService.findById(2L)).thenReturn(user);

        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("Yoga Session");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);
        sessionDto.setDescription("This is a yoga session.");
        sessionDto.setUsers(Arrays.asList(2L));
        
        Session session = sessionMapper.toEntity(sessionDto);

        assertEquals(sessionDto.getId(), session.getId());
        assertEquals(sessionDto.getName(), session.getName());
        assertEquals(sessionDto.getDescription(), session.getDescription());
        assertEquals(sessionDto.getDate(), session.getDate());
        assertEquals(teacher, session.getTeacher());
        assertEquals(Collections.singletonList(user), session.getUsers());
    }

    @Test
    public void testToDto() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        
        User user = new User();
        user.setId(2L);
        
        Session session = new Session();
        session.setId(1L);
        session.setName("Yoga Session");
        session.setDate(new Date());
        session.setTeacher(teacher);
        session.setDescription("This is a yoga session.");
        session.setUsers(Arrays.asList(user));

        SessionDto sessionDto = sessionMapper.toDto(session);

        assertEquals(session.getId(), sessionDto.getId());
        assertEquals(session.getName(), sessionDto.getName());
        assertEquals(session.getDescription(), sessionDto.getDescription());
        assertEquals(session.getDate(), sessionDto.getDate());
        assertEquals(teacher.getId(), sessionDto.getTeacher_id());
        assertEquals(Collections.singletonList(user.getId()), sessionDto.getUsers());
    }
}
