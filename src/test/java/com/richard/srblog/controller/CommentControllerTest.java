package com.richard.srblog.controller;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.richard.srblog.controller.api.CommentController;
import com.richard.srblog.domain.Comment;
import com.richard.srblog.result.ResultBody;
import com.richard.srblog.service.CommentService;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

	@Autowired
	private CommentController controller;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CommentService commentService;
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAddComment() throws Exception{
		//sign up a test user first , and then login to get tokenString
		 String testName = UUID.randomUUID().toString();
		 String testContent = UUID.randomUUID().toString();
		 Long postId = 9394L;
		 String testPassword = UUID.randomUUID().toString();
		
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
		 Long userId = Long.valueOf(token.get("userId").toString());
		 
		 /** test post comment*/
		 result  = this.mockMvc.perform(post("/api/comment")
				    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				    .param("content", testContent)
				    .param("postId",postId.toString())
				    .header("tokenString", tokenString))
   				.andReturn();
		 assertTrue(HttpServletResponse.SC_OK==result.getResponse().getStatus());
			
		 String resultJson = result.getResponse().getContentAsString();
		 resultBody = objectMapper.readValue(resultJson,ResultBody.class);
		 assertFalse(null==resultBody.getResult());
		 
		 HashMap<String,Object> comment = (HashMap<String,Object>)resultBody.getResult();
		 Long commentId = Long.valueOf(comment.get("id").toString());
		 
		 
		 Optional<Comment> reload  = commentService.findById(commentId);
		 assertTrue(reload.isPresent());
		 
		 assertTrue(userId==reload.get().getUserId());
		 assertTrue(testContent.equals(reload.get().getContent()));
	}
}
