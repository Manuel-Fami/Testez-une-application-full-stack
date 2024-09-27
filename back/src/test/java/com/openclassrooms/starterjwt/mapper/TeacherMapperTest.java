package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;

public class TeacherMapperTest {
    private TeacherMapper teacherMapper;

    @BeforeEach
    public void setUp() {
        teacherMapper = Mappers.getMapper(TeacherMapper.class);
    }

    @Test
    public void testToEntity() {
        TeacherDto dto = new TeacherDto();
        dto.setId(1L);
        dto.setLastName("Doe");
        dto.setFirstName("John");
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());

        Teacher teacher = teacherMapper.toEntity(dto);

        assertEquals(dto.getId(), teacher.getId());
        assertEquals(dto.getLastName(), teacher.getLastName());
        assertEquals(dto.getFirstName(), teacher.getFirstName());
        assertEquals(dto.getCreatedAt(), teacher.getCreatedAt());
        assertEquals(dto.getUpdatedAt(), teacher.getUpdatedAt());
    }

    @Test
    public void testToDto() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("Doe");
        teacher.setFirstName("John");
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());

        TeacherDto dto = teacherMapper.toDto(teacher);

        assertEquals(teacher.getId(), dto.getId());
        assertEquals(teacher.getLastName(), dto.getLastName());
        assertEquals(teacher.getFirstName(), dto.getFirstName());
        assertEquals(teacher.getCreatedAt(), dto.getCreatedAt());
        assertEquals(teacher.getUpdatedAt(), dto.getUpdatedAt());
    }
}
