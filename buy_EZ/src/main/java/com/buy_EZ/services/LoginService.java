package com.buy_EZ.services;

import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.models.AdminCurrentSession;
import com.buy_EZ.models.AdminDto;

public interface LoginService {

	public AdminCurrentSession adminLogin (AdminDto adminDto) throws AdminException;
	
}
