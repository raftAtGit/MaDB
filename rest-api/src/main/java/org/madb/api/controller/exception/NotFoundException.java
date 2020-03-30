package org.madb.api.controller.exception;

/** Thrown to indicate a requested object is not found. Translated to HTTP 404 status code by {@link ExceptionHandlerAdvice}  */
@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {

	public NotFoundException(String message) {
		super(message);
	}
	
	public NotFoundException(Integer id) {
		super("resource not found, id: " + id);
	}
	
}
