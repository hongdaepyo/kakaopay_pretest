package com.project.sprinkle.error;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
	
	final private HttpStatus status;
	final private String message;
}
