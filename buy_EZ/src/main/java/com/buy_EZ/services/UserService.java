package com.buy_EZ.services;

import java.util.List;

import com.buy_EZ.exceptions.CategoryException;
import com.buy_EZ.exceptions.CustomerException;
import com.buy_EZ.exceptions.ProductException;
import com.buy_EZ.models.Cart;
import com.buy_EZ.models.Product;

public interface UserService {

	
	public List<Product> searchProductsByname(String name, String loggedInUserId) throws ProductException, CustomerException;
	
	public List<Product> searchProductByCategory(String categoryName, String loggedInUserId) throws CategoryException, ProductException, CustomerException;
	
	public Product addRating(String productId, String loggedInUserId, Double rating) throws ProductException, CustomerException;
	
	public List<Product> sortProductsByPriceHighToLow(List<Product> products) throws ProductException;
	
	public List<Product> sortProductsByPriceLowToHigh(List<Product> products) throws ProductException;
	
	public List<Product> sortProductsByRatingsHighToLow(List<Product> products) throws ProductException;
	
	public List<Product> sortProductsByRatingsLowToHigh(List<Product> products) throws ProductException;
	
	public Product getProductDetailsById(String id, String loggedInUserId) throws ProductException, CustomerException;
	
	public Product addToCart(String productId, String loggedInId, Integer quantity) throws ProductException, CustomerException;
	
	public Product deleteProductFromCart(String productId, String loggedInId) throws ProductException, CustomerException;
	
	public Cart getCartDetails(String loggedInId) throws CustomerException;
	
}
