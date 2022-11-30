package com.buy_EZ.services;

import com.buy_EZ.DTO.CustomerDto;
import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.exceptions.CustomerException;
import com.buy_EZ.models.Admin;
import com.buy_EZ.models.AdminCurrentSession;
import com.buy_EZ.models.AdminDto;
import com.buy_EZ.models.CustomerCurrentSession;
import com.buy_EZ.models.User;

public interface LoginService {

	public AdminCurrentSession adminLogin (AdminDto adminDto) throws AdminException;
	
	public AdminDto adminRegister(Admin admin) throws AdminException;
	
	public CustomerDto customerRegister(User customer) throws CustomerException;
	
	public CustomerCurrentSession customerLogin(CustomerDto customerDto) throws CustomerException;
	
}
