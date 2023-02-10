package com.buy_EZ.services;

import java.util.List;

import com.buy_EZ.DTO.CartDTO;
import com.buy_EZ.exceptions.CategoryException;
import com.buy_EZ.exceptions.CustomerException;
import com.buy_EZ.exceptions.PaymentException;
import com.buy_EZ.exceptions.ProductException;
import com.buy_EZ.models.Address;
import com.buy_EZ.models.Cart;
import com.buy_EZ.models.Category;
import com.buy_EZ.models.Order;
import com.buy_EZ.models.Payment;
import com.buy_EZ.models.Product;
import com.buy_EZ.models.ProductDTO;
import com.buy_EZ.models.SubCategory;
import com.buy_EZ.models.User;


public interface UserService {

    //search prducts by name	
	public List<Product> searchProductsByname(String name) throws ProductException, CustomerException;
	
	//search prducts by category name
	public List<Product> searchProductByCategory(String categoryName) throws CategoryException, ProductException, CustomerException;
	
	//add rating to a product
	public Product addRating(String productId, Double rating) throws ProductException, CustomerException;
	
	//sort by price high to low
	public List<Product> sortProductsByPriceHighToLow(List<Product> products) throws ProductException;
	
	//sort by price low to high
	public List<Product> sortProductsByPriceLowToHigh(List<Product> products) throws ProductException;
	
	//sort by ratings high to low
	public List<Product> sortProductsByRatingsHighToLow(List<Product> products) throws ProductException;
	
	//sort by ratings low to high
	public List<Product> sortProductsByRatingsLowToHigh(List<Product> products) throws ProductException;
	
	//get products details by product id
	public Product getProductDetailsById(String id) throws ProductException, CustomerException;
	
	//add product to cart
	public ProductDTO addToCart(String productId, Integer quantity) throws ProductException, CustomerException;
	
	//remove product from the cart 
	public Product deleteProductFromCart(String productId) throws ProductException, CustomerException;
	
	//get user cart details
	public CartDTO getCartDetails() throws CustomerException;
	
	//place order
	public Order placeOrder(String paymentType, Address address) throws CustomerException, PaymentException;
	
	//get products from a perticular order
	public List<ProductDTO> getProductsFromOrder(String orderId);
	
	//get all categories
	public List<Category> getAllCategories();
	
	//get all subcategories
	public List<SubCategory> getAllSubCategories();
	
	//get all products
	public List<Product> getAllProducts(int pageNumber, int size);
	
	//get userdetails
	public User getUserDetails() throws CustomerException;
	
	//get all orders of a user
	public List<Order> getAllOrdersOfUser() throws CustomerException;
	
	//update products qauntity
	public Product updateProductQuantity(String productId, Integer quantity) throws ProductException;
	 
	//get all payment types
	public List<Payment> getAllPaymentType();
	
	//update address of a user
	public User updateAddress(Address address) throws CustomerException;
	
	//update username
	public User updateUsername(String newUsername) throws CustomerException;
	
	//update password
	public User updatePassword(String password) throws CustomerException;
	
	
}
