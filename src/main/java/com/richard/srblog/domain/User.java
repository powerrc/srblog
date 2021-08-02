package com.richard.srblog.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The user class
 * @author Richard
 *
 */
@Entity
public class User extends BaseDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3917993524844901337L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String password;
	
	private String theme;

	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * 
	 * The jsonignore tag is must here, 
	 * so the the password won't be returned when it's returned as a json object
	 * @return
	 */
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	/**
	 * @return the theme
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * @param theme the theme to set
	 * @return the User object
	 */
	public User setTheme(String theme) {
		this.theme = theme;
		return this;
	}
	
	
}
