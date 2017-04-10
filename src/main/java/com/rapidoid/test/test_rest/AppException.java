package com.rapidoid.test.test_rest;

public class AppException extends Exception{
	
	private int code;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3776119945806058395L;

	public AppException(){

	}

	public AppException(String message) {
		super(message);
	}
	
	public AppException(int code, String message) {
		super(message);
		this.code = code;
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(Throwable cause) {
		super(cause);
	}

	protected AppException(String message, Throwable cause,
			boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public int getCode() {
		return code;
	}

}
