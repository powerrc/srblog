package com.richard.srblog.service;

import java.util.List;
import java.util.Optional;

import com.richard.srblog.domain.Comment;

public interface CommentService {

	/** @inheritdoc */
	public Comment save(Comment entity);

	/** @inheritdoc */
	public List<Comment> findByPostId(Long postId);
	
	/** @inheritdoc */
	public Optional<Comment> findById(Long id);
}
