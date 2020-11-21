package com.project.sprinkle.error.exception;

public class ExpiredException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ExpiredException(String errorMessage) {
		super(errorMessage);
	}
}
