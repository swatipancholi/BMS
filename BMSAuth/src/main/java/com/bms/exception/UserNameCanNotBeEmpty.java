package com.bms.exception;

/**Exception class*/
@SuppressWarnings("serial")
public class UserNameCanNotBeEmpty extends RuntimeException {

	public UserNameCanNotBeEmpty(String message) {
		super(message);
	}
}
