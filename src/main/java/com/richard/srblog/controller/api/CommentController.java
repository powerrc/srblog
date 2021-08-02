package com.richard.srblog.controller.api;

import java.util.Date;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.richard.srblog.domain.Comment;
import com.richard.srblog.domain.Token;
import com.richard.srblog.exception.SrblogException;
import com.richard.srblog.result.ResultBody;
import com.richard.srblog.service.CommentService;
import com.richard.srblog.service.TokenService;

/**
 * The restful controller to handle comment related ops
 * CommentController
 * @author Richard
 *
 */
@RestController
@RequestMapping(value = "/api/comment")
public class CommentController extends ApiController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private TokenService tokenService;
	
	/**
	 * To insert a new comment for specific post 
	 * @param postId the related post id
	 * @param content the comment content
	 * @param tokenString the tokenString in the header of http request
	 * @return {ResultBody}
	 */
	@ResponseBody
	@PostMapping
	public ResultBody insert(
			@RequestParam(required = true, name = "postId") String postId,
			@RequestParam(required = true, name = "content") String content,
			@RequestHeader(name = "tokenString") String tokenString
		) {
				
		Optional<Token> token = tokenService.findByTokenString(tokenString);
		
		if(!token.isPresent()) {
			throw new SrblogException("403","Forbbiden");
		}
		
		if(token.get().isExpired()) {
			throw new SrblogException("440","Token expired");
		}
			
		
		Comment newComment = new Comment().setPostId(Long.valueOf(postId))
										  .setUserId(token.get().getUserId())
										  .setCreateTime(new Date())
										  .setContent(content);
		
		commentService.save(newComment);
		
		return ResultBody.success(newComment);
	}
	
	/**
	 * To fetch the comments related to a post, in a list
	 * @param postId the related post
	 * @return {ResultBody}
	 */
	@ResponseBody
	@GetMapping(value="/list")
	public ResultBody list(@RequestParam(required = true, name = "postId") String postId) {
				
		return ResultBody.success(commentService.findByPostId(Long.valueOf(postId)));
	}
}
