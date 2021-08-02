package com.richard.srblog.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.richard.srblog.domain.Token;
import com.richard.srblog.domain.User;

@SpringBootTest
public class TokenServiceTest {

	@Autowired
	private TokenService service;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void crudTest() throws Exception{
		String testName = UUID.randomUUID().toString();
		String testPassword = UUID.randomUUID().toString();
		
		User user = new User().setName(testName)
								.setPassword(testPassword);
		
		user = userService.save(user);
		
		Token token = service.addUser(user);
		
		assertTrue(null!=token);
		
		assertTrue(token.getId()>0);
		
		String tokenString = token.getTokenString();
		
		Optional<Token> reload = service.findByTokenString(tokenString);
		
		assertTrue(reload.isPresent());
		
		assertTrue(user.getId().equals(reload.get().getUser().getId()));
	}
}
