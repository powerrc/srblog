package com.richard.srblog.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.richard.srblog.domain.Comment;

@SpringBootTest
public class CommentServiceTest {

	@Autowired
	private CommentService service;
	
	@Test
	public void crudTest() throws Exception{
		
		String commentContent=UUID.randomUUID().toString();
		Long postId = 9393L;
		Long userId = 9494L;
		
		Comment entity = new Comment().setContent(commentContent)
									  .setPostId(postId)
									  .setCreateTime(new Date())
									  .setUserId(userId);
		Comment saved = service.save(entity);
		
		assertTrue(null!=saved);
		
		List<Comment> comments = service.findByPostId(postId);
		
		assertFalse(comments.isEmpty());
		
		HashSet<Long> hashset = new HashSet<>();
		for(Comment c: comments) {
			hashset.add(c.getId());
		}
		
		assertTrue(hashset.contains(saved.getId()));
		
		assertTrue(saved.getContent().equals(commentContent));
		
	}
	
}
