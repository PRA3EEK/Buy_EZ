package com.buy_EZ.services;

import java.util.List;


import com.buy_EZ.exceptions.CategoryException;
import com.buy_EZ.exceptions.CustomerException;
import com.buy_EZ.exceptions.PaymentException;
import com.buy_EZ.exceptions.ProductException;
import com.buy_EZ.models.Cart;
import com.buy_EZ.models.Category;
import com.buy_EZ.models.Order;
import com.buy_EZ.models.Product;
import com.buy_EZ.models.ProductDTO;
import com.buy_EZ.models.SubCategory;
import com.buy_EZ.models.User;


public interface UserService {

	
	public List<Product> searchProductsByname(String name) throws ProductException, CustomerException;
	
	public List<Product> searchProductByCategory(String categoryName) throws CategoryException, ProductException, CustomerException;
	
	public Product addRating(String productId, Double rating) throws ProductException, CustomerException;
	
	public List<Product> sortProductsByPriceHighToLow(List<Product> products) throws ProductException;
	
	public List<Product> sortProductsByPriceLowToHigh(List<Product> products) throws ProductException;
	
	public List<Product> sortProductsByRatingsHighToLow(List<Product> products) throws ProductException;
	
	public List<Product> sortProductsByRatingsLowToHigh(List<Product> products) throws ProductException;
	
	public Product getProductDetailsById(String id) throws ProductException, CustomerException;
	
	public ProductDTO addToCart(String productId, Integer quantity) throws ProductException, CustomerException;
	
	public Product deleteProductFromCart(String productId) throws ProductException, CustomerException;
	
	public Cart getCartDetails() throws CustomerException;
	
	public Order placeOrder(String loggedInId, String paymentType) throws CustomerException, PaymentException;
	
	public List<ProductDTO> getProductsFromOrder(String orderId);
	
	public List<Category> getAllCategories();
	
	public List<SubCategory> getAllSubCategories();
	
	public List<Product> getAllProducts();
	
	public User getUserDetails() throws CustomerException;
	
	
}
