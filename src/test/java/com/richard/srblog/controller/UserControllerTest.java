package com.richard.srblog.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.richard.srblog.controller.api.UserController;
import com.richard.srblog.domain.User;
import com.richard.srblog.result.ResultBody;
import com.richard.srblog.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private UserController controller;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;

	
	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAll() throws Exception{
		
		 String testName = UUID.randomUUID().toString();
		 String testPassword = UUID.randomUUID().toString();
		 String testTheme = "blue";
		
		 /** test signup */
		MvcResult result  = this.mockMvc.perform(post("/api/user")
							    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
							    .param("name", testName)
							    .param("password", testPassword))
			            		.andReturn();
		assertTrue(HttpServletResponse.SC_OK==result.getResponse().getStatus());
		
		 /** test login */
		 result  = this.mockMvc.perform(post("/api/user/login")
				    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				    .param("name", testName)
				    .param("password", testPassword))
         			.andReturn();
		 assertTrue(HttpServletResponse.SC_OK==result.getResponse().getStatus());
		 
		 String tokenStringJson = result.getResponse().getContentAsString();
		 ObjectMapper objectMapper = new ObjectMapper();
		 ResultBody resultBody = objectMapper.readValue(tokenStringJson,ResultBody.class);
		 assertFalse(null==resultBody.getResult());
		 HashMap<String,Object> token = (HashMap<String,Object>)resultBody.getResult();
		 String tokenString = token.get("tokenString").toString();
		 
		 /** test change theme*/
		 result  = this.mockMvc.perform(put("/api/user/theme")
				    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				    .param("theme", testTheme)
				    .header("tokenString", tokenString))
   				.andReturn();
		 
		 assertTrue(HttpServletResponse.SC_OK==result.getResponse().getStatus());
		 
		 Optional<User> user = userService.findById(Long.valueOf(token.get("userId").toString()));
		 
		 assertTrue(user.isPresent());
		 
		 assertTrue(testTheme.equals(user.get().getTheme()));
		 
		 /** test logout */
		 result  = this.mockMvc.perform(get("/api/user/logout")
				    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				    .header("tokenString", tokenString))
      				.andReturn();
		 assertTrue(HttpServletResponse.SC_OK==result.getResponse().getStatus());
		 
		 /** test login with wrong password , it should fail */
		 result  = this.mockMvc.perform(post("/api/user/login")
				    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				    .param("name", testName)
				    .param("password", testPassword+"1"))
         			.andReturn();
		 assertFalse(HttpServletResponse.SC_OK==result.getResponse().getStatus());
		 
		 /** test sign up  with duplicate name , it should fail */
		 result  = this.mockMvc.perform(post("/api/user")
				 	   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				 	   .param("name", testName)
				 	   .param("password", testPassword))
         			   .andReturn();
		 assertFalse(HttpServletResponse.SC_OK==result.getResponse().getStatus());
	}
	
	
}
