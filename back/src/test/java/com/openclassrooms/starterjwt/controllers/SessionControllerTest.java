package com.openclassrooms.starterjwt.controllers;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SessionControllerTest {
    
    @Autowired 
    private MockMvc mockMvc;
	
	@Test
	@WithMockUser
	public void testFindById_unexistingSession_shouldReturnNotFound() throws Exception {
        Long id = 9999L;
        
        mockMvc.perform(get("/api/session/{id}", id))
        	 .andExpect(status().isNotFound());
	}

    @Test
	@WithMockUser
	public void testFindById_success() throws Exception {
        Long id = 1L;
        String expectedName = "Séance de Yoga matin";
        Long expectedTeacherId = 1L;
        String expectedDescription = "Une séance de yoga revitalisante pour bien commencer la journée.";

        mockMvc.perform(get("/api/session/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(id.intValue()))) 
            .andExpect(jsonPath("$.name", is(expectedName))) 
            .andExpect(jsonPath("$.teacher_id", is(expectedTeacherId.intValue())))
            .andExpect(jsonPath("$.description", is(expectedDescription))); 
	}

    @Test
	@WithMockUser
	public void testFindAll_success() throws Exception {
		
        mockMvc.perform(get("/api/session"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", not(empty())));
		 
	}

    @Test
	@WithMockUser
	public void testCreate_success() throws Exception {
		 SessionDto sessionDto = new SessionDto();
	     sessionDto.setName("Test Session"); 
	     sessionDto.setDate(new Date());
	     sessionDto.setTeacher_id(1L);
	     sessionDto.setDescription("Test session.");
		
	     String sessionDtoJson = new ObjectMapper().writeValueAsString(sessionDto);
	     
	     mockMvc.perform(post("/api/session")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(sessionDtoJson))
	            .andExpect(status().isOk())
	     		.andExpect(jsonPath("$.name").value(sessionDto.getName()))
         		.andExpect(jsonPath("$.teacher_id").value(sessionDto.getTeacher_id()))
         		.andExpect(jsonPath("$.description").value(sessionDto.getDescription()));
		
	}

    @Test
	@WithMockUser
	public void testUpdate_success() throws Exception {
        Long id = 1L;
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Updated Session");
        sessionDto.setDescription("Updated session.");
        sessionDto.setDate(new Date()); 
        sessionDto.setTeacher_id(1L);
        
        String sessionDtoJson = new ObjectMapper().writeValueAsString(sessionDto);
        
        mockMvc.perform(put("/api/session/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(sessionDtoJson))
            .andExpect(status().isOk()); 
		
	}

    @Test
	@WithMockUser
	public void testDeleteSession_unexistingSession_shouldReturnNotFound() throws Exception {
		Long id = 9999L;
        
        mockMvc.perform(delete("/api/session/{id}", id))
            .andExpect(status().isNotFound());
	}

    @Test
	@WithMockUser
	public void testDeleteSession_success() throws Exception {
		Long id = 1L;
		
		mockMvc.perform(delete("/api/session/{id}", id))
   	 		.andExpect(status().isOk());
		
	}

    @Test
	@WithMockUser
	public void testParticipate_success() throws Exception {
		 Long sessionId = 1L;
	     Long userId = 2L;
			     
	     mockMvc.perform(post("/api/session/" + sessionId + "/participate/" + userId)
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());
	        		
	}
	
	@Test
	@WithMockUser
	public void testNoLongerParticipate_success() throws Exception {
		 Long sessionId = 2L;
	     Long userId = 3L;
			     
	     mockMvc.perform(delete("/api/session/" + sessionId + "/participate/" + userId)
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());
	        		
	}
}
