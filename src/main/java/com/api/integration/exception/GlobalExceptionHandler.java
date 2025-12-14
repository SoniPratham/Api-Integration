package com.api.integration.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<?> notValid(Throwable ex, HttpServletRequest request) {
		if (ex instanceof MethodArgumentNotValidException) {
			List<String> errors = new ArrayList<>();
			MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;

			exception.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));
			Map<String, List<String>> result = new HashMap<>();
			result.put("errors", errors);

			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

		} else if (ex instanceof NotFoundException) {
			List<String> errors = new ArrayList<>();
			NotFoundException exceptioNotFound = (NotFoundException) ex;

			String errorMessage = exceptioNotFound.getMessage();
			errors.add(errorMessage);
			Map<String, List<String>> result = new HashMap<>();
			result.put("errors", errors);

			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} else if (ex instanceof DataIntegrityViolationException) {
			List<String> errors = new ArrayList<>();
			DataIntegrityViolationException exception = (DataIntegrityViolationException) ex;

			String message = exception.getMessage();
			errors.add(message);
//			exception.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));
			Map<String, List<String>> result = new HashMap<>();
			result.put("errors", errors);

			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		else {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////					.body(ex.getMessage());
//					.body("Something went wrong...");

			List<String> errors = new ArrayList<>();
			Throwable exceptioNotFound = ex;

			String errorMessage = exceptioNotFound.getMessage();
			errors.add(errorMessage);
			Map<String, List<String>> result = new HashMap<>();
			result.put("errors", errors);

			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

	}
}
