package com.project.sprinkle.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.sprinkle.error.exception.ExpiredException;
import com.project.sprinkle.error.exception.UsedTokenException;
import com.project.sprinkle.error.exception.UserIdEqualsReceiverIdException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
public class ErrorController {
	
	@ExceptionHandler({UserIdEqualsReceiverIdException.class})
	protected ResponseEntity<ErrorResponse> handleUserIdEqualsReceiverIdException(final UserIdEqualsReceiverIdException ex) {
		log.error("handleUserIdEqualsReceiverIdException", ex);
		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ExpiredException.class})
	protected ResponseEntity<ErrorResponse> handleExpiredException(final ExpiredException ex) {
		log.error("handleExpiredException", ex);
		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({UsedTokenException.class})
	protected ResponseEntity<ErrorResponse> handleUsedTokenException(final UsedTokenException ex) {
		log.error("handleUsedTokenException", ex);
		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}