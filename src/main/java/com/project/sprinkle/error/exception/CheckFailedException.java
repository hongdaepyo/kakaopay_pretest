package com.project.sprinkle.error.exception;

public class CheckFailedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public CheckFailedException(String errorMessage) {
		super(errorMessage);
	}
}
