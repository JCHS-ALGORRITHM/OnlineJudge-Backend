package com.github.ioloolo.onlinejudge.common.validation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.github.ioloolo.onlinejudge.common.exception.ErrorResponse;

@ControllerAdvice
public class ValidationErrorHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidatedException(MethodArgumentNotValidException e) {

		FieldError error = e.getFieldError();

		return ResponseEntity.internalServerError().body(new ErrorResponse(error == null ? e : new Exception(error.getDefaultMessage())));
	}
}
