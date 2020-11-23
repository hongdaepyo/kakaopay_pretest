package com.project.sprinkle.error.exception;

public class AlreadyReceivedTokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public AlreadyReceivedTokenException(String errorMessage) {
		super(errorMessage);
	}
}
