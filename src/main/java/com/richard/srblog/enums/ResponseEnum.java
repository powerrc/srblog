package com.richard.srblog.enums;

import com.richard.srblog.exception.BaseErrorInfo;

/**
 * The enum of all definied response result
 * @author Richard
 *
 */
public enum ResponseEnum implements BaseErrorInfo {
	
	OK("200","OK"),
	WRONG_INPUT("400","Wrong input"),
	NO_MATCH("401","No matching record"),
	FORBIDDEN("403","Forbidden"),
	NOT_FOUND("404","Not found"),
	DUPLICATE("409","Duplicate"),
	SESSION_EXPIRED("409","Session expired"),
	SERVER_ERROR("500","Server error");
	
	private String code;
	
	private String message;

	ResponseEnum(String _code,String _message){
		this.code = _code;
		this.message = _message;
		
	}
	
	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
