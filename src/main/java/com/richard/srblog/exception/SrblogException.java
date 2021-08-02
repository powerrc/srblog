package com.richard.srblog.exception;

/**
 * The exception class for the system
 * @extends {RuntimeException}
 * @author Richard
 *
 */
public class SrblogException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 697045452121681095L;
	
	protected String errorCode;
	
	protected String errorMsg;
	
	public SrblogException(BaseErrorInfo errorInfoInterface) {
		super(errorInfoInterface.getCode());
		this.errorCode = errorInfoInterface.getCode();
		this.errorMsg = errorInfoInterface.getMessage();
	}
		
	public SrblogException(String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;
	}
	
	public SrblogException(String errorCode, String errorMsg) {
		super(errorCode);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public SrblogException setErrorCode(String errorCode) {
		this.errorCode = errorCode;
		return this;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public SrblogException setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		return this;
	}

}
