package com.project.sprinkle.error.exception;

public class NotExistReceivableSprinkleException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NotExistReceivableSprinkleException(String errorMessage) {
		super(errorMessage);
	}
}
