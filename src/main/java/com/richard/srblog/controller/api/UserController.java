package com.richard.srblog.controller.api;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.richard.srblog.domain.Token;
import com.richard.srblog.domain.User;
import com.richard.srblog.exception.SrblogException;
import com.richard.srblog.result.ResultBody;
import com.richard.srblog.service.EncryptService;
import com.richard.srblog.service.TokenService;
import com.richard.srblog.service.UserService;

/**
 * The controller class to handle all user related ops
 * @author Richard
 *
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserController extends  ApiController{

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private EncryptService encryptService;
	
	/**
	 * To insert a new user 
	 * @param name the user name
	 * @param password the user password
	 * @return {ResultBody} with the newly created user object
	 */
	@ResponseBody
	@PostMapping
	public ResultBody insert(
			@RequestParam(required = true, name = "name") String name,
			@RequestParam(required = true, name = "password") String password
		) {
		
		Optional<User> user = userService.findByName(name);
		
		if(user.isPresent()) {
			throw new SrblogException("409","User name exists");
		}
		
		User newUser = new User().setName(name)
								 .setTheme("default")
								 .setPassword(encryptService.bcryptEncrypt(password));
		
		userService.save(newUser);
		
		return ResultBody.success(newUser);
	}

	/**
	 * To login a user
	 * @param name the user name
	 * @param password the password
	 * @return {ResultBody} with the related token object
	 */
	@ResponseBody
	@PostMapping(value="/login")
	public ResultBody login(
			@RequestParam(required = true, name = "name") String name,
			@RequestParam(required = true, name = "password") String password
		) {
		Optional<User> user = userService.findByName(name);
		
		if(!user.isPresent()) {
			throw new SrblogException("404","User not found");
		}
		
		if(!encryptService.bcryptCheck(password, user.get().getPassword())) {
			throw new SrblogException("401","Wrong password");
		}
		
		return ResultBody.success(tokenService.addUser(user.get()));
	}
	
	/**
	 * To update a theme value for current loggedin user
	 * @param theme the theme to update
	 * @param tokenString the tokenString in the header of http request
	 * @return
	 */
	@ResponseBody
	@PutMapping(value="/theme")
	public ResultBody postTheme(
			@RequestParam(required = true, name = "theme") String theme,
			@RequestHeader(name = "tokenString") String tokenString
		) {
		Optional<Token> token = tokenService.findByTokenString(tokenString);
		
		if(!token.isPresent()) {
			throw new SrblogException("403","Forbbiden");
		}
		
		Optional<User> user = Optional.of(token.get().getUser());
		if(!user.isPresent()) {
			throw new SrblogException("404","User not found");
		}
		
		user.get().setTheme(theme);
		
		userService.save(user.get());
		
		return ResultBody.success("ok");
	}
	
	/**
	 * To logout a user
	 * @param tokenString the tokenString in the header of http request
	 * @return {ResultBody}
	 */
	@ResponseBody
	@GetMapping(value="/logout")
	public ResultBody logout(@RequestHeader(name = "tokenString") String tokenString) {

		tokenService.findByTokenString(tokenString)
					.ifPresent(token->tokenService.deleteById(token.getId()));				
		
		
		return ResultBody.success("ok");
	}
	
	/**
	 * To verify if the current token string is till valid 
	 * If so , return the token object
	 * @param tokenString the tokenString in the header of http request
	 * @return {ResultBody}
	 */
	@ResponseBody
	@GetMapping(value="/islogin")
	public ResultBody islogin(@RequestHeader(name = "tokenString") String tokenString) {
		Optional<Token> token = tokenService.findByTokenString(tokenString);
		
		if(!token.isPresent()) {
			throw new SrblogException("403","Forbbiden");
		}
		
		if(token.get().isExpired()) {
			throw new SrblogException("420","Token expired");
		}
		
		return ResultBody.success(token.get());
	}
	
	/**
	 * To get a user by id
	 * @param id the user id , required
	 * @return {ResultBody}
	 */
	@ResponseBody
	@GetMapping
	public ResultBody get(@RequestParam(required = true, name = "id") String id) {
		Optional<User> user = userService.findById(Long.valueOf(id));
		
		if(!user.isPresent()) {
			throw new SrblogException("404","User not found");
		}	
		
		return ResultBody.success(user.get());
	}
	
	/**
	 * To fetch all users in a list
	 * @return {ResultBody}
	 */
	@ResponseBody
	@GetMapping(value="/list")
	public ResultBody list() {
				
		return ResultBody.success(userService.findAll());
	}
}
