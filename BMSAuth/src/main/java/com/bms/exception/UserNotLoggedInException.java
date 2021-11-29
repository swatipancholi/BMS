package com.bms.exception;

@SuppressWarnings("serial")
public class UserNotLoggedInException extends RuntimeException {

	public UserNotLoggedInException(String message) {
		super(message);
	}

}
