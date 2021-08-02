package com.richard.srblog.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.richard.srblog.domain.User;

@SpringBootTest
public class UserServiceTest {

	
	@Autowired
	private UserService service;
	
	@Test
	public void crudTest() throws Exception {
		String testName = UUID.randomUUID().toString();
		String testPassword = UUID.randomUUID().toString();
		
		User entity = new User().setName(testName)
								.setPassword(testPassword);
		
		User saved = service.save(entity);
		assertTrue(saved.getId()>0);
		
		Long savedId = saved.getId();
		
		Optional<User> reload = service.findById(savedId);
		
		assertTrue(reload.isPresent());
		
		assertTrue(testName.equals(reload.get().getName()));
		assertTrue(testPassword.equals(reload.get().getPassword()));
		
		reload = service.findByName(testName);
		assertTrue(reload.isPresent());
	}
}
