package com.ricardodev.forohub.api.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private record ValidationErrorData(String field, String error) {
		public ValidationErrorData(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}

	private ResponseEntity<ErrorResponse> buildResponse(ErrorResponse response,
			HttpStatus code) {
		return new ResponseEntity<ErrorResponse>(response, code);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
			DataIntegrityViolationException exception) {
		ErrorResponse response = new ErrorResponse();
		HttpStatus code = HttpStatus.BAD_REQUEST;
		response.setTitle("Payload Error");
		response.setDetail(exception.getMostSpecificCause().toString());
		response.setStatus(code);
		return buildResponse(response, code);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception) {
		ErrorResponse response = new ErrorResponse();
		HttpStatus code = HttpStatus.BAD_REQUEST;
		var errors = exception.getFieldErrors().stream()
				.map(ValidationErrorData::new).toList();
		response.setTitle("Validation Error");
		response.setDetail(exception.getDetailMessageCode());
		response.setStatus(code);
		response.setProperty("errors", errors);
		return buildResponse(response, code);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleSecurityException(Exception exception) {
		ErrorResponse response = new ErrorResponse();
		HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;

		if (exception instanceof BadCredentialsException) {
			response.setTitle("The username or password is incorrect");
			code = HttpStatus.UNAUTHORIZED;
		} else if (exception instanceof AccountStatusException) {
			response.setTitle("The account is locked");
			code = HttpStatus.FORBIDDEN;
		} else if (exception instanceof AccessDeniedException) {
			response.setTitle("You are not authorized to access this resource");
			code = HttpStatus.FORBIDDEN;
		} else if (exception instanceof JWTVerificationException) {
			if (exception instanceof SignatureVerificationException) {
				response.setTitle("The JWT signature is invalid");
			} else if (exception instanceof TokenExpiredException) {
				response.setTitle("The provided token has expired");
			} else if (exception instanceof JWTDecodeException) {
				response.setTitle("The provided token is invalid");
			} else {
				response.setTitle("JWT Token Validation Error");
			}
			code = HttpStatus.UNAUTHORIZED;
		} else if (exception instanceof EntityNotFoundException) {
			response.setTitle("Entity not found");
			code = HttpStatus.NOT_FOUND;
		} else if (exception instanceof HttpMessageNotReadableException
				|| exception instanceof DataValidationException) {
			response.setTitle("Validation Error");
			code = HttpStatus.BAD_REQUEST;
		} else {
			response.setTitle("Unknown internal server error.");
		}

		response.setStatus(code);
		response.setDetail(exception.getMessage());

		return buildResponse(response, code);
	}
}
