package com.richard.srblog.service;

import java.util.List;
import java.util.Optional;

import com.richard.srblog.domain.Post;

public interface PostService {
	
	/** @inheritdoc */
	public Post save(Post entity);
	
	/** @inheritdoc */
	public Optional<Post> findById(Long id);

	/**
	 * to find a post by user id
	 * @param userId the related user id
	 * @return
	 */
	public List<Post> findByUserId(Long userId);
	
	/** @inheritdoc */
	public void deleteById(Long id);

}
