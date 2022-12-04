package com.buy_EZ.services;

import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.exceptions.CategoryException;
import com.buy_EZ.exceptions.ProductException;
import com.buy_EZ.models.Admin;
import com.buy_EZ.models.AdminDto;
import com.buy_EZ.models.Category;
import com.buy_EZ.models.Product;
import com.buy_EZ.models.SubCategory;

public interface AdminService {

	public AdminDto insertAdmin(Admin admin, String loggedInAdminid) throws AdminException;
	
	public Category insertCategory(Category category, String loggedInAdminId) throws AdminException, CategoryException;
	
	public Product insertProduct(Product product, String categoryName, String subCategoryName, String loggedInAdminId) throws AdminException, ProductException, CategoryException;

	public SubCategory insertSubCategory(SubCategory subCategory, String parentCategoryName, String loggedInAdminId) throws CategoryException, AdminException;	
}
