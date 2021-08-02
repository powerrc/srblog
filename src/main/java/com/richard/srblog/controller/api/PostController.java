package com.richard.srblog.controller.api;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.richard.srblog.domain.Post;
import com.richard.srblog.domain.Token;
import com.richard.srblog.exception.SrblogException;
import com.richard.srblog.result.ResultBody;
import com.richard.srblog.service.PostService;
import com.richard.srblog.service.TokenService;

/**
 * To handle all post related ops
 * @class PostController
 * @author Richard
 *
 */
@RestController
@RequestMapping(value = "/api/post")
public class PostController extends ApiController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private TokenService tokenService;

	/**
	 * To fetch posts related to a user, in a list
	 * @param userId the related userId
	 * @return {ResultBody}
	 */
	@ResponseBody
	@GetMapping(value="/list")
	public ResultBody list(@RequestParam(required = false, name = "userId") String userId) {
		
		return ResultBody.success(postService.findByUserId(Long.valueOf(userId)));
	}
	
	
	/**
	 * To insert a new post for specific user 
	 * @param title the post title , required
	 * @param description the post description , required
	 * @param content the post content
	 * @param tokenString the tokenString in the header of http request
	 * @return {ResultBody}
	 */
	@ResponseBody
	@PostMapping
	public ResultBody insert(
			@RequestParam(required = true, name = "title") String title,
			@RequestParam(required = true, name = "description") String description,
			@RequestParam(required = true, name = "content") String content,
			@RequestHeader(name = "tokenString") String tokenString
		) {
		
		Optional<Token> token = tokenService.findByTokenString(tokenString);
		
		
		if(!token.isPresent()) {
			throw new SrblogException("403","Forbbiden");
		}
		
		Post newPost = new Post().setTitle(title)
								 .setDescription(description)
								 .setContent(content)
								 .setUserId(token.get().getUserId())
								 .setUpdateTime(new Date())
								 .setCreateTime(new Date());
		
		postService.save(newPost);
		
		return ResultBody.success(newPost);
	}
	
	/**
	 * To get a post by id
	 * @param id the post id ,required
	 * @return {ResultBody}
	 */
	@ResponseBody
	@GetMapping
	public ResultBody get(
			@RequestParam(required = true, name = "id") String id
		) {
		
		Optional<Post> post = postService.findById(Long.valueOf(id));
		
		if(!post.isPresent()) {
			throw new SrblogException("404","Post not found");
		}
		
		return ResultBody.success(post.get());
	}
	
	/**
	 * To delete a post by id
	 * @param id the post id
	 * @param tokenString the tokenString in the header of http request
	 * @return {ResultBody}
	 */
	@ResponseBody
	@DeleteMapping
	public ResultBody delete(
			@RequestParam(required = true, name = "id") String id,
			@RequestHeader(name = "tokenString") String tokenString
		) {
		
		Optional<Token> token = tokenService.findByTokenString(tokenString);
		
		
		if(!token.isPresent()) {
			throw new SrblogException("403","Forbbiden");
		}
		
		Optional<Post> post = postService.findById(Long.valueOf(id));
		
		if(!post.isPresent()) {
			throw new SrblogException("403","Post not found");
		}
		
		postService.deleteById(post.get().getId());
		
		return ResultBody.success("ok");
	}
}
