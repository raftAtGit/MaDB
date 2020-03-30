package org.madb.api.controller.exception;

/** Thrown to indicate something is wrong with the HTTP request. Translated to HTTP 400 status code by {@link ExceptionHandlerAdvice}  */
@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {

	public BadRequestException(String message) {
		super(message);
	}
}
