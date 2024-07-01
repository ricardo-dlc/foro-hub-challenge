package com.ricardodev.forohub.api.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.JWTDecodeException;
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

	@ExceptionHandler(Exception.class)
	public ProblemDetail handleSecurityException(Exception exception) {
		ProblemDetail errorDetail = null;

		// TODO send this stack trace to an observability tool
		exception.printStackTrace();

		if (exception instanceof BadCredentialsException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
			errorDetail.setProperty("description", "The username or password is incorrect");

			return errorDetail;
		}

		if (exception instanceof DataIntegrityViolationException) {
			var exceptionError = (DataIntegrityViolationException) exception;
			var cause = exceptionError.getMostSpecificCause();
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), exception.getMessage());
			errorDetail.setProperty("description", "A validation error occured. \n" + cause);
		}

		if (exception instanceof DataValidationException) {
			// var exceptionError = (DataIntegrityViolationException) exception;
			// var cause = exceptionError.getMostSpecificCause();
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), exception.getMessage());
			errorDetail.setProperty("description", "A validation error occured");
			// errorDetail.setProperty("reason", "A validation error occured");
		}

		if (exception instanceof AccountStatusException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
			errorDetail.setProperty("description", "The account is locked");
		}

		if (exception instanceof AccessDeniedException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
			errorDetail.setProperty("description", "You are not authorized to access this resource");
		}

		if (exception instanceof MethodArgumentNotValidException) {
			var exceptionAsErrors = (MethodArgumentNotValidException) exception;
			var errors = exceptionAsErrors.getFieldErrors().stream()
					.map(ValidationErrorData::new).toList();
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),
					exceptionAsErrors.getDetailMessageCode());
			errorDetail.setProperty("description", "Validation failed");
			errorDetail.setProperty("errors", errors);
		}

		if (exception instanceof SignatureVerificationException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),
					exception.getMessage());
			errorDetail.setProperty("description", "The JWT signature is invalid");
		}

		if (exception instanceof TokenExpiredException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
			errorDetail.setProperty("description", "The provided token has expired");
		}

		if (exception instanceof JWTDecodeException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),
					exception.getMessage());
			errorDetail.setProperty("description", "The provided token is invalid");
		}

		if (exception instanceof EntityNotFoundException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),
					exception.getMessage());
			errorDetail.setProperty("description", "Entity not found");
		}

		if (errorDetail == null) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
			errorDetail.setProperty("description", "Unknown internal server error.");
		}

		// errorDetail.setDetail(null);

		return errorDetail;
	}
}