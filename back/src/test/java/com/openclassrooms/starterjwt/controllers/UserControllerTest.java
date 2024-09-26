package com.openclassrooms.starterjwt.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
public class UserControllerTest {
    @Autowired 
    private MockMvc mockMvc;
	
	@Test
	@WithMockUser
	public void testFindById_unexistingUser_shouldReturnNotfound() throws Exception {
		Long id = 9999L;
		
		mockMvc.perform(get("/api/user/" + id + "") 
                .contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isNotFound());
              
	}
	
	
	@Test
	@WithMockUser
	public void testFindById_success() throws Exception {
		Long id = 2L;

		mockMvc.perform(get("/api/user/" + id + "") 
				.contentType(MediaType.APPLICATION_JSON)) 
				.andExpect(status().isOk()) 
				.andExpect(jsonPath("$.id", is(2)))
				.andExpect(jsonPath("$.email", is("john@email.com"))) 
				.andExpect(jsonPath("$.lastName", is("DOE")))
				.andExpect(jsonPath("$.firstName", is("John"))); 
	} 
	
	@Test 
    @WithMockUser(username = "john@email.com") 
    public void testDelete_Success() throws Exception {
		Long id = 2L;
		
        mockMvc.perform(delete("/api/user/" + id + "") 
                .contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isOk()); 
    }
}
