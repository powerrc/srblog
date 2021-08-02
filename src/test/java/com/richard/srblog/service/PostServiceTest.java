package com.richard.srblog.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.richard.srblog.domain.Post;

@SpringBootTest
public class PostServiceTest {

	@Autowired
	private PostService service;
	
	@Test
	public void crudTest() throws Exception{
		String testContent = UUID.randomUUID().toString();
		String testDesc = UUID.randomUUID().toString();
		String testTitle = UUID.randomUUID().toString();
		Long userId = 9394L;
		
		Post entity = new Post().setContent(testContent)
							  .setUpdateTime(new Date())
							  .setCreateTime(new Date())
							  .setDescription(testDesc)
							  .setTitle(testTitle)
							  .setUserId(userId);
		
		Post saved = service.save(entity);
		
		
		assertTrue(saved.getId()>0);
		
		Long savedId = saved.getId();
		
		Optional<Post> reload = service.findById(savedId);
		
		assertTrue(reload.isPresent());
		
		assertTrue(testContent.equals(reload.get().getContent()));
		assertTrue(testDesc.equals(reload.get().getDescription()));
		assertTrue(testTitle.equals(reload.get().getTitle()));
		assertTrue(userId.equals(reload.get().getUserId()));
		
		service.deleteById(savedId);
		
		reload = service.findById(savedId);
		
		assertFalse(reload.isPresent());
		
	}
}
