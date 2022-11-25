package com.buy_EZ.exceptions;

public class AdminException extends Exception{

	private String message;

	public AdminException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
