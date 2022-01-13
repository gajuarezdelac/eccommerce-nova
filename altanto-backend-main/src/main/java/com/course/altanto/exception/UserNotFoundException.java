package com.course.altanto.exception;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
        super(message);
    }
}