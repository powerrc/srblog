package com.richard.srblog.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.richard.srblog.domain.Post;



/**
 * The DAO for post
 * @extends {CrudRepository}
 * @author Richard
 *
 */
public interface PostRepository extends CrudRepository<Post, Long> {

	/**
	 * To find posts of a related user id
	 * @param userId
	 * @return {List}
	 */
	List<Post> findByUserId(Long userId);
}
