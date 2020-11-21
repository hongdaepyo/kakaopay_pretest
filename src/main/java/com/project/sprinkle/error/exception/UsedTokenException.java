package com.project.sprinkle.error.exception;

public class UsedTokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UsedTokenException(String errorMessage) {
		super(errorMessage);
	}
}
