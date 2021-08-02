package com.richard.srblog.service;

import java.util.Optional;

import com.richard.srblog.domain.User;

public interface UserService {

	/**
	 * To find a user by user name
	 * @param name
	 * @return
	 */
	public Optional<User> findByName(String name);

	/** @inheritdoc */
	public User save(User entity);
	
	/** @inheritdoc */
	public Iterable<User> findAll();
	
	/** @inheritdoc */
	public Optional<User> findById(Long id);

}
