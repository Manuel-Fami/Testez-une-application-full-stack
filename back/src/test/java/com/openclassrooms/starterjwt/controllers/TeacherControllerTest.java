package com.openclassrooms.starterjwt.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TeacherControllerTest {
    @Autowired 
    private MockMvc mockMvc;
	
	@Test
	@WithMockUser
	public void testFindById_unexistingTeacher_shouldReturnNotfound() throws Exception {
		Long id = 9999L;
		
		mockMvc.perform(get("/api/teacher/" + id + "") 
                .contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isNotFound());
              
	}
	
	@Test
	@WithMockUser
	public void testFindById_succes() throws Exception {
		Long id = 1L;
		
		mockMvc.perform(get("/api/teacher/" + id +"")
                .contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Margot")))
                .andExpect(jsonPath("$.lastName", is("DELAHAYE")));
              
	}
	
	@Test
	@WithMockUser
	public void testFindAll_sucess() throws Exception {
		 mockMvc.perform(get("/api/teacher") 
	             .contentType(MediaType.APPLICATION_JSON))
	             .andExpect(status().isOk())
	             .andExpect(jsonPath("$", hasSize(2)))
	             .andExpect(jsonPath("$[0].id", is(1)))
	             .andExpect(jsonPath("$[0].firstName", is("Margot")))
	             .andExpect(jsonPath("$[0].lastName", is("DELAHAYE")));
	}
}
