package com.richard.srblog.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * The token class
 * @author Richard
 *
 */
@Entity
public class Token implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3566071248948092809L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String tokenString;
	
	private Long userId;
	
	private Date expireTime;
	
	@OneToOne()
	@JoinColumn(name = "userId",referencedColumnName="id",insertable = false, updatable = false)
	private User user;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the token string
	 */
	public String getTokenString() {
		return tokenString;
	}

	/**
	 * @param tokenString the tokenString to set
	 *  @return the token
	 */
	public Token setTokenString(String tokenString) {
		this.tokenString = tokenString;
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
	 *  @return the token
	 */
	public Token setUserId(Long userId) {
		this.userId = userId;
		return this;
	}

	/**
	 * @return the expireTime
	 */
	public Date getExpireTime() {
		return expireTime;
	}

	/**
	 * @param expireTime the expireTime to set
	 * @return the token
	 */
	public Token setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
		return this;
	}

	/**
	 * @return the related user
	 */
	public User getUser() {
		return this.user;
	}
	
	/**
	 * To check if it's expired
	 * @return boolean
	 */
	public Boolean isExpired() {
		return new Date().getTime() >= this.getExpireTime().getTime();
	}
}
