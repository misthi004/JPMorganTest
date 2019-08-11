package com.interview.jpmorgan.api;

public class ApiCallException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApiCallException(String message, Exception cause) {
        super(message,cause);
    }
}
