package com.richard.srblog.service;

import java.util.Optional;

import com.richard.srblog.domain.Token;
import com.richard.srblog.domain.User;

public interface TokenService {

	/**
	 * To find a token by tokenString.
	 * @param tokenString
	 * @return
	 */
	public Optional<Token> findByTokenString(String tokenString);
	
	/**
	 * To get a token for a user
	 * @param user
	 * @return {Token}
	 */
	public Token addUser(User user);

	/** @inheritdoc */
	public void deleteById(Long id);
	
}
