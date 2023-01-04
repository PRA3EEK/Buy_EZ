package com.buy_EZ.services;

import org.springframework.security.core.context.SecurityContextHolder;

public class GetSubject {

	public final static String getUsername()
	{
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
}
