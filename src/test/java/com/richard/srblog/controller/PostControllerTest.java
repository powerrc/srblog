package com.richard.srblog.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.richard.srblog.controller.api.PostController;
import com.richard.srblog.domain.Post;
import com.richard.srblog.result.ResultBody;
import com.richard.srblog.service.PostService;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

	
	@Autowired
	private PostController controller;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAll() throws Exception{
		//sign up a test user first , and then login to get tokenString
		 String testName = UUID.randomUUID().toString();
		 String testPassword = UUID.randomUUID().toString();

		 String testTitle = UUID.randomUUID().toString();
		 String testContent = UUID.randomUUID().toString();
		 String testDescription = UUID.randomUUID().toString();
		
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
		 
		 /** test insert a new post */
		 result  = this.mockMvc.perform(post("/api/post")
				    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				    .param("title", testTitle)
				    .param("description", testDescription)
				    .param("content", testContent)
				    .header("tokenString", tokenString))
    			.andReturn();
		 
		 assertTrue(HttpServletResponse.SC_OK==result.getResponse().getStatus());
		 
		 String postJson = result.getResponse().getContentAsString();
		 resultBody = objectMapper.readValue(postJson,ResultBody.class);
		 assertFalse(null==resultBody.getResult());
		 HashMap<String,Object> post = (HashMap<String,Object>)resultBody.getResult();
	
		 Long postId = Long.valueOf(post.get("id").toString());
		 
		 assertTrue(postId>0);
		 
		/** test to get the post just created*/
		 result  = this.mockMvc.perform(get("/api/post?id="+postId)
				    .contentType(MediaType.APPLICATION_FORM_URLENCODED))
				 	.andReturn();
		 assertTrue(HttpServletResponse.SC_OK==result.getResponse().getStatus());
		 
		 postJson = result.getResponse().getContentAsString();
		 resultBody = objectMapper.readValue(postJson,ResultBody.class);
		 assertFalse(null==resultBody.getResult());

         post = (HashMap<String,Object>)resultBody.getResult();
         
         assertTrue(testTitle.equals(post.get("title").toString()));
         assertTrue(testDescription.equals(post.get("description").toString()));
         assertTrue(testContent.equals(post.get("content").toString()));
         
         /** test to delete the post*/
         result  = this.mockMvc.perform(delete("/api/post?id="+postId.toString())
        		 	.header("tokenString", tokenString)
				    .contentType(MediaType.APPLICATION_FORM_URLENCODED))
				 	.andReturn();
         assertTrue(HttpServletResponse.SC_OK==result.getResponse().getStatus());
		 
         Optional<Post> reload = postService.findById(postId);
         
         assertFalse(reload.isPresent());
			 
	}
}
