package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;

public class UserMapperTest {
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    public void testToEntity() {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setEmail("john.doe@example.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setAdmin(false);
        dto.setPassword("securepassword123");
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());

        User user = userMapper.toEntity(dto);

        assertEquals(dto.getId(), user.getId());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getFirstName(), user.getFirstName());
        assertEquals(dto.getLastName(), user.getLastName());
        assertEquals(dto.isAdmin(), user.isAdmin());
        assertEquals(dto.getCreatedAt(), user.getCreatedAt());
        assertEquals(dto.getUpdatedAt(), user.getUpdatedAt());
        // Note: Password shouldn't be mapped if not part of User entity.
    }

    @Test
    public void testToDto() {
        User user = new User();
        user.setId(1L);
        user.setEmail("john.doe@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setAdmin(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UserDto dto = userMapper.toDto(user);

        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getFirstName(), dto.getFirstName());
        assertEquals(user.getLastName(), dto.getLastName());
        assertEquals(user.isAdmin(), dto.isAdmin());
        assertEquals(user.getCreatedAt(), dto.getCreatedAt());
        assertEquals(user.getUpdatedAt(), dto.getUpdatedAt());
    }

    @Test
    public void testToEntityList() {
        // Given
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(new UserDto(1L, "john@example.com", "Doe", "John", false, "password123", null, null));
        userDtos.add(new UserDto(2L, "jane@example.com", "Doe", "Jane", true, "password123", null, null));

        // When
        List<User> users = userMapper.toEntity(userDtos);

        // Then
        assertEquals(userDtos.size(), users.size());
        assertEquals(userDtos.get(0).getId(), users.get(0).getId());
        assertEquals(userDtos.get(0).getEmail(), users.get(0).getEmail());
        assertEquals(userDtos.get(0).getFirstName(), users.get(0).getFirstName());
        assertEquals(userDtos.get(0).getLastName(), users.get(0).getLastName());
        assertEquals(userDtos.get(0).getAdmin(), users.get(0).isAdmin());
    }

    @Test
    public void testToDtoList() {
        // Given
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "john@example.com", "Doe", "John", "password", false, null, null));
        users.add(new User(2L, "jane@example.com", "Doe", "Jane", "password", true, null, null));

        // When
        List<UserDto> userDtos = userMapper.toDto(users);

        // Then
        assertEquals(users.size(), userDtos.size());
        assertEquals(users.get(0).getId(), userDtos.get(0).getId());
        assertEquals(users.get(0).getEmail(), userDtos.get(0).getEmail());
        assertEquals(users.get(0).getFirstName(), userDtos.get(0).getFirstName());
        assertEquals(users.get(0).getLastName(), userDtos.get(0).getLastName());
        assertEquals(users.get(0).isAdmin(), userDtos.get(0).getAdmin());
    }

}
