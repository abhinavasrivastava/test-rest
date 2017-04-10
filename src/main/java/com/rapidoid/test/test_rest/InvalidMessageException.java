package com.rapidoid.test.test_rest;

public class InvalidMessageException extends Exception{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7041543075520693864L;
	private String dvcMsgId;
	private int code;

	public InvalidMessageException(){

	}

	public InvalidMessageException(String message) {
		super(message);
	}
	
	public InvalidMessageException(int code, String dvcMsgId, String message) {
		super(message);
		this.code = code;
		this.dvcMsgId = dvcMsgId;
	}

	public InvalidMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMessageException(Throwable cause) {
		super(cause);
	}

	protected InvalidMessageException(String message, Throwable cause,
			boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public int getCode() {
		return code;
	}

	public String getDvcMsgId() {
		return dvcMsgId;
	}

}
