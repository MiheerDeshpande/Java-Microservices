package com.microservice.user.service.exceptions;

public class ResourceNotFoundException extends Exception{

	public ResourceNotFoundException() {
		super("Resource not found on server");
	}
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
