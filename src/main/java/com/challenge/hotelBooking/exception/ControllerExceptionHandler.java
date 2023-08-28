package com.challenge.hotelBooking.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ErrorDetails> ressourceNotFoundException(ResourceNotFoundException ex,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
	}
}
