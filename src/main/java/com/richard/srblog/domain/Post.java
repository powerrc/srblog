package com.richard.srblog.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The post class
 * @author Richard
 *
 */
@Entity
public class Post implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 945184735310236492L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;
	
	private String title;
	
	private String description;
	
	private String content="";
	
	private Date createTime;
	
	private Date updateTime;

	public Long getId() {
		return id;
	}

	/**
	 * to get the post title
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * To set the post title
	 * @param title
	 * @return {Post}
	 */
	public Post setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * To get the post description
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * To set the post description
	 * @param description
	 * @return {Post}
	 */
	public Post setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * To get the content of the post
	 * @return
	 */
	public String getContent() {
		return content;
	}

	/**
	 * To set the content of the post
	 * @param content
	 * @return {Post}
	 */
	public Post setContent(String content) {
		this.content = content;
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
	 */
	public Post setUserId(Long userId) {
		this.userId = userId;
		return this;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}


	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 * @return the post object
	 */
	public Post setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		return this;
	}
	
	/**
	 * @param createTime the createTime to set
	 * @return the post object
	 */
	public Post setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

}
