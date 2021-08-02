package com.richard.srblog.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.richard.srblog.enums.ResponseEnum;
import com.richard.srblog.exception.SrblogException;
import com.richard.srblog.result.ResultBody;

/**
 * The general exception handler to handle all kinds of exceptions
 * @class ApiExceptionHandler
 * @author Richard
 *
 */
@ControllerAdvice
public class ApiExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);
	
	/**
	 * To handle SrblogException
	 * @param req the http servlet request
	 * @param e the srblog exception
	 * @return {ResultBody}
	 */
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = SrblogException.class)
	public ResultBody exceptionHandler(HttpServletRequest req , SrblogException e) {
		logger.error("Sr blog Exception happens.",e);
		return ResultBody.error(e.getErrorCode(),e.getErrorMsg());
	}
	
	/**
	 * To handle any other exceptions
	 * @param req the http servlet request
	 * @param e the srblog exception
	 * @return {ResultBody}
	 */
	@ResponseBody
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public ResultBody otherExceptionHandler(HttpServletRequest req , Exception e) {
		logger.error("Other Exception happens.",e);
		return ResultBody.error(ResponseEnum.SERVER_ERROR);
	}
}
