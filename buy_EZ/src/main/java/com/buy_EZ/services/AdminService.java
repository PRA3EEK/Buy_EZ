package com.buy_EZ.services;

import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.exceptions.CategoryException;
import com.buy_EZ.exceptions.OrderException;
import com.buy_EZ.exceptions.PaymentException;
import com.buy_EZ.exceptions.ProductException;
import com.buy_EZ.exceptions.ShipperException;
import com.buy_EZ.exceptions.SupplierException;
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

	//insert new Admin
	public AdminDto insertAdmin(User admin) throws AdminException;
	
	//insert new category 
	public Category insertCategory(Category category) throws AdminException, CategoryException;
	
	//insert a product 
	public Product insertProduct(Product product, String categoryName) throws AdminException, ProductException, CategoryException;

	//insert new sub category
	public SubCategory insertSubCategory(SubCategory subCategory, String parentCategoryName) throws CategoryException, AdminException;	

	//get user from order
    public User searchByOrder(String orderId) throws OrderException, AdminException;
    
    //add a payment type
    public Payment addPaymentType(Payment payment) throws AdminException, PaymentException;

    //add a new shipper 
    public Shipper addShipper(Shipper shipper) throws AdminException, ShipperException;
    
    //add a new supplier
    public Supplier addSupplier(Supplier supplier) throws AdminException, SupplierException;
    
    

}
