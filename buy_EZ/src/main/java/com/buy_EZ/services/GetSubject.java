package com.buy_EZ.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.buy_EZ.models.User;
import com.buy_EZ.repositories.CustomerRepo;

public class GetSubject {
	


	public final static String getUsername()
	{
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	
	
}
