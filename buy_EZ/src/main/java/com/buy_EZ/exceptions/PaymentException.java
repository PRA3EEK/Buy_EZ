package com.buy_EZ.exceptions;

public class PaymentException extends Exception{

	private String message;
	
	public PaymentException(String message) 
	{
		super();
		this.message = message;
	}
	
	public String getMessage() 
	{
		return message;
	}
	
	public void setMessage(String message) 
	{
		this.message = message;
	}
}
