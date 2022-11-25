package com.buy_EZ.services;

import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.exceptions.CategoryException;
import com.buy_EZ.models.Admin;
import com.buy_EZ.models.AdminDto;
import com.buy_EZ.models.Category;

public interface AdminService {

	public AdminDto insertAdmin(Admin admin, String loggedInAdminid) throws AdminException;
	
	public Category insertCategory(Category category, String loggedInAdminId) throws AdminException, CategoryException;
	
}
