package org.madb.api.controller.exception;

@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {

	public NotFoundException(String message) {
		super(message);
	}
	
	public NotFoundException(Long id) {
		super("resource not found, id: " + id);
	}
	
}
