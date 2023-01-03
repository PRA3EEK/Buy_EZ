package com.buy_EZ.services;

import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.exceptions.CategoryException;
import com.buy_EZ.exceptions.OrderException;
import com.buy_EZ.exceptions.ProductException;
import com.buy_EZ.models.Admin;
import com.buy_EZ.models.AdminDto;
import com.buy_EZ.models.Category;
import com.buy_EZ.models.Payment;
import com.buy_EZ.models.Product;
import com.buy_EZ.models.Shipper;
import com.buy_EZ.models.SubCategory;
import com.buy_EZ.models.Supplier;
import com.buy_EZ.models.User;

public interface AdminService {

	public AdminDto insertAdmin(User admin) throws AdminException;
	
	public Category insertCategory(Category category, String loggedInAdminId) throws AdminException, CategoryException;
	
	public Product insertProduct(Product product, String categoryName, String subCategoryName, String loggedInAdminId) throws AdminException, ProductException, CategoryException;

	public SubCategory insertSubCategory(SubCategory subCategory, String parentCategoryName, String loggedInAdminId) throws CategoryException, AdminException;	

    public User searchByOrder(String orderId, String loggedInAdminId) throws OrderException, AdminException;
    
    public Payment addPaymentType(Payment payment, String loggedInAdminId) throws AdminException;

    public Shipper addShipper(Shipper shipper, String loggedInAdminId) throws AdminException;
 
    public Supplier addSupplier(Supplier supplier, String loggedInAdminId) throws AdminException;

}
