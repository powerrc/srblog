package com.richard.srblog.result;

import com.richard.srblog.enums.ResponseEnum;
import com.richard.srblog.exception.BaseErrorInfo; 

/**
 * The result body class.
 * All api requests would be wrapped into this class before it's returned
 * @author Richard
 *
 */
public class ResultBody {

	private String code;
	
	private String message;
	
	private Object result;
	
	public ResultBody() {
	}

	public ResultBody(BaseErrorInfo errorInfo) {
		this.code = errorInfo.getCode();
		this.message = errorInfo.getMessage();
	}
	
	public ResultBody(String _code ,String _message) {
		this.code = _code;
		this.message = _message;
	}


	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 * @return the resultbody
	 */
	public ResultBody setCode(String code) {
		this.code = code;
		return this;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 * @return the resultbody
	 */
	public ResultBody setMessage(String message) {
		this.message = message;
		return this;
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 * @return the resultbody
	 */
	public ResultBody setResult(Object result) {
		this.result = result;
		return this;
	}
	
	/**
	 * The function to return a success result
	 * @param data
	 * @return
	 */
	public static ResultBody success(Object data) {
		return new ResultBody(ResponseEnum.OK).setResult(data);
	}
	
	/**
	 * The function to return an error result
	 * @param errorInfo
	 * @return
	 */
	public static ResultBody error(BaseErrorInfo errorInfo) {
		return new ResultBody(errorInfo.getCode(),errorInfo.getMessage());
	}
	
	/**
	 * The function to return an error result
	 * @param code the error code
	 * @param message the error message
	 * @return
	 */
	public static ResultBody error(String code , String message) {
		return new ResultBody(code,message);
	}
	
}
