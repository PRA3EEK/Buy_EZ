package com.buy_EZ.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseCookie;

import com.buy_EZ.DTO.CustomerDto;
import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.exceptions.CustomerException;
import com.buy_EZ.exceptions.RoleException;
import com.buy_EZ.models.Admin;
import com.buy_EZ.models.AdminCurrentSession;
import com.buy_EZ.models.AdminDto;
import com.buy_EZ.models.CustomerCurrentSession;
import com.buy_EZ.models.SignupRequest;
import com.buy_EZ.models.User;

public interface LoginService {

	
	public CustomerDto customerRegister(SignupRequest request) throws CustomerException, RoleException;
	
	public Object[] customerLogin(CustomerDto customerDto) throws CustomerException;
	
	public ResponseCookie logout() throws CustomerException;
	
}
