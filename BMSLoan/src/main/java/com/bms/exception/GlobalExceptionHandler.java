package com.bms.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bms.model.ResponseForException;

import feign.RetryableException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		return errors;
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ResponseForException> invalidTokenException(InvalidTokenException invalidTokenException) {

		return new ResponseEntity<>(new ResponseForException(invalidTokenException.getMessage(), LocalDateTime.now(),
				HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(RetryableException.class)
	public ResponseEntity<ResponseForException> microServiceUnavailableException() {
		return new ResponseEntity<>(
				new ResponseForException("MicroServiceUnavailable", LocalDateTime.now(), HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Object> handleUnauthorizedExceptions(UnauthorizedException ex) {

		return new ResponseEntity<>(new ResponseForException("Un Authorized, Login Again ...", LocalDateTime.now(),
				HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(InitialDepositException.class)
	public ResponseEntity<ResponseForException> initialDepositException(InitialDepositException ex) {

		return new ResponseEntity<>(
				new ResponseForException("In sufficient deposit amount", LocalDateTime.now(), HttpStatus.BAD_REQUEST),
				HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UserNotLoggedInException.class)
	public ResponseEntity<ResponseForException> userNotLoggedInException(UserNotLoggedInException ex) {

		return new ResponseEntity<>(
				new ResponseForException(ex.getMessage(), LocalDateTime.now(), HttpStatus.UNAUTHORIZED), 
				HttpStatus.UNAUTHORIZED);
	}

}
