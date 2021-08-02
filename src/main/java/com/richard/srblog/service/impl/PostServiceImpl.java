package com.richard.srblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richard.srblog.dao.PostRepository;
import com.richard.srblog.domain.Post;
import com.richard.srblog.service.BaseService;
import com.richard.srblog.service.PostService;

/**
 * The domain service class implement for post
 * @author Richard
 *
 */
@Service("PostService")
public class PostServiceImpl extends BaseService<Post> implements PostService {
	
	@Autowired
	protected PostRepository repository;

	@Override
	protected PostRepository getRepository() {
		return this.repository;
	}

	/** @inheritdoc */
	@Override
	public List<Post> findByUserId(Long userId) {
		return this.getRepository().findByUserId(userId);
	}

}
