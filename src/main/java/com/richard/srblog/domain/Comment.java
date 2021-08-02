package com.richard.srblog.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The comment class
 * @author Richard
 *
 */
@Entity
public class Comment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4548814730796224972L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long postId;
	
	private Long userId;
	
	private String content;
	
	private Date createTime;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @return the postId
	 */
	public Long getPostId() {
		return postId;
	}

	/**
	 * @param postId the postId to set
	 * @return the comment object
	 */
	public Comment setPostId(Long postId) {
		this.postId = postId;
		return this;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 * @return the comment object
	 */
	public Comment setUserId(Long userId) {
		this.userId = userId;
		return this;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 * @return the comment object
	 */
	public Comment setContent(String content) {
		this.content = content;
		return this;
	}


	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}


	/**
	 * @param createTime the createTime to set
	 * @return the comment object
	 */
	public Comment setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}
}
