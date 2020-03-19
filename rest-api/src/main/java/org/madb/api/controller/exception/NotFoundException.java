package org.madb.api.controller.exception;

@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {

	public NotFoundException(String message) {
		super(message);
	}
	
	public NotFoundException(Integer id) {
		super("resource not found, id: " + id);
	}
	
}
