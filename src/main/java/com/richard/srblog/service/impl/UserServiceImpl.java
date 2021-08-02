package com.richard.srblog.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richard.srblog.dao.UserRepository;
import com.richard.srblog.domain.User;
import com.richard.srblog.service.BaseService;
import com.richard.srblog.service.UserService;

/**
 * The domain service class implement for user
 * @author Richard
 *
 */
@Service("UserService")
public class UserServiceImpl  extends BaseService<User> implements UserService{

	
	@Autowired
	protected UserRepository repository;
	
	@Override
	protected UserRepository getRepository() {
		return this.repository;
	}
	
	/** @inheritdoc */
	@Override
	public Optional<User> findByName(String name) {
		return this.repository.findTopOneByNameOrderByIdDesc(name);
	}

}
