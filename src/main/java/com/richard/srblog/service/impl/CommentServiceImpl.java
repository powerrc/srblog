package com.richard.srblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richard.srblog.dao.CommentRepository;
import com.richard.srblog.domain.Comment;
import com.richard.srblog.service.BaseService;
import com.richard.srblog.service.CommentService;

/**
 * The domain service class implement for comment
 * @author Richard
 *
 */
@Service("CommentService")
public class CommentServiceImpl extends BaseService<Comment> implements CommentService{

	@Autowired
	protected CommentRepository repository;
	
	@Override
	protected CommentRepository getRepository() {
		return this.repository;
	}

	/** @inheritdoc */
	@Override
	public List<Comment> findByPostId(Long postId) {
		return this.getRepository().findByPostId(postId);
	}
}
