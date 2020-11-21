package com.project.sprinkle.error.exception;

public class UserIdEqualsReceiverIdException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UserIdEqualsReceiverIdException(String errorMessage) {
		super(errorMessage);
	}
}
