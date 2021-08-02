package com.richard.srblog.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.richard.srblog.domain.Comment;

/**
 * The DAO for comment
 * @extends {CrudRepository}
 * @author Richard
 *
 */
public interface CommentRepository extends CrudRepository<Comment, Long> {

	/**
	 * To find comments by a related post id
	 * @param postId
	 * @return {List}
	 */
	List<Comment> findByPostId(Long postId);

}
