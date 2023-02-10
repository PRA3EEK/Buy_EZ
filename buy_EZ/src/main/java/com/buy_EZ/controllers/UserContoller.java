package com.buy_EZ.controllers;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
import com.buy_EZ.repositories.ProductRepo;
import com.buy_EZ.services.UserService;



@RestController
@RequestMapping("/buy_EZ/user")
@CrossOrigin(origins = "*")
public class UserContoller {

	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private UserService userService;
	
	@GetMapping("/search")
	public ResponseEntity<List<Product>> searchProduct(@RequestParam(value = "name") String name) throws ProductException, CustomerException{
		return new ResponseEntity<List<Product>>(userService.searchProductsByname(name), HttpStatus.OK);
	}
	@GetMapping("/search/category")
	public ResponseEntity<List<Product>> searchProductsByCategoryName(@RequestParam(value = "categoryName") String categoryName) throws CategoryException, ProductException, CustomerException{
		return new ResponseEntity<List<Product>>(userService.searchProductByCategory(categoryName), HttpStatus.OK);
	}
	@PutMapping("/add-rating")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Product> addRatings(@RequestParam(value = "productId") String productId, @RequestParam(value = "rating") Double rating) throws ProductException, CustomerException{
		return new ResponseEntity<Product>(userService.addRating(productId, rating), HttpStatus.OK);
	}
	@GetMapping("/search/HTL-price")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<Product>> searchProductsByNameSortHighToLow(@RequestParam(value = "name") String name) throws ProductException, CustomerException{
		List<Product> products = userService.searchProductsByname(name);
		return new ResponseEntity<List<Product>>(userService.sortProductsByPriceHighToLow(products), HttpStatus.OK);
	}
	@GetMapping("/search/LTH-price")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<Product>> searchProductsByNameSortLowToHigh(@RequestParam(value = "name") String name, @RequestParam(value = "loggedInId") String id) throws ProductException, CustomerException{
		List<Product> products = userService.searchProductsByname(name);
		return new ResponseEntity<List<Product>>(userService.sortProductsByPriceLowToHigh(products), HttpStatus.OK);
	}
	
	@GetMapping("/search/HTL-ratings")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<Product>> searchProductByNamesSortHighToLowRatings(@RequestParam(value = "name") String name, @RequestParam(value = "loggedInId") String id) throws ProductException, CustomerException{
		List<Product> products = userService.searchProductsByname(name);
		return new ResponseEntity<List<Product>>(userService.sortProductsByRatingsHighToLow(products), HttpStatus.OK);
	}
	
	@GetMapping("/search/LTH-ratings")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<Product>> searchProductByNamesSortLowToHighRatings(@RequestParam(value = "name") String name, @RequestParam(value = "loggedInId") String id) throws ProductException, CustomerException{
		List<Product> products = userService.searchProductsByname(name);
		return new ResponseEntity<List<Product>>(userService.sortProductsByRatingsLowToHigh(products), HttpStatus.OK);
	}
	
	@GetMapping("/searchById")
	public ResponseEntity<Product> getProductDetails(@RequestParam(value = "productId") String productId) throws ProductException, CustomerException{
		return new ResponseEntity<Product>(userService.getProductDetailsById(productId), HttpStatus.OK);
	}
	
	@PostMapping("/add-cart")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ProductDTO> addProductToCart(@RequestParam(value = "productId") String productId, @RequestParam(value = "quantity") Integer quant) throws ProductException, CustomerException{
		return new ResponseEntity<ProductDTO>(userService.addToCart(productId, quant), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-cart")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Product> deleteProductFromCart(@RequestParam(value = "productId") String productId) throws ProductException, CustomerException{
        return new ResponseEntity<Product>(userService.deleteProductFromCart(productId), HttpStatus.OK);	
	}
	
	@GetMapping("/cart")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<CartDTO> getCartDetails() throws CustomerException{
		return new ResponseEntity<CartDTO>(userService.getCartDetails(), HttpStatus.OK);
	}
	
	@PostMapping("/cart/order")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Order> placeOrder(@RequestBody Address address, @RequestParam("paymentType") String paymentType) throws CustomerException, PaymentException{
		System.out.println(address);
		return new ResponseEntity<Order>(userService.placeOrder(paymentType, address), HttpStatus.OK);
	}
	@GetMapping("order/products")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<ProductDTO>> getProductsFromOrder(@RequestParam("orderId") String orderId){
		return new ResponseEntity<List<ProductDTO>>(userService.getProductsFromOrder(orderId), HttpStatus.OK);
	}
	
	@GetMapping("/categories")
	
	public ResponseEntity<List<Category>> getAllCategories()
	{
		return new ResponseEntity<List<Category>>(userService.getAllCategories(), HttpStatus.OK);
	}
	
    @GetMapping("/subCategories")
	public ResponseEntity<List<SubCategory>> getAllSubCategories()
	{
		return new ResponseEntity<List<SubCategory>>(userService.getAllSubCategories(), HttpStatus.OK);
	}
    
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(@RequestParam("page") int pageNumber, @RequestParam("size") int size)
	{
		return new ResponseEntity<List<Product>>(userService.getAllProducts(pageNumber, size), HttpStatus.OK);
	}
	
	@GetMapping("/details")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<User> getUserDetails() throws CustomerException
	{
		return new ResponseEntity<User>(userService.getUserDetails(), HttpStatus.OK);
	}
	
	@GetMapping("/orders")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<Order>> getAllOrders() throws CustomerException
	{
		return new ResponseEntity<List<Order>>(userService.getAllOrdersOfUser(), HttpStatus.OK);
	}
	
	@PutMapping("/update-quantity")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Product> updateProductQuantityOfCart(@RequestParam("productId") String productId, @RequestParam("quantity") Integer quantity) throws ProductException
	{
		return new ResponseEntity<Product>(userService.updateProductQuantity(productId, quantity), HttpStatus.OK);
	}
	
	@GetMapping("/payments")
	public ResponseEntity<List<Payment>> getPayments()
	{
		return new ResponseEntity<List<Payment>>(userService.getAllPaymentType(), HttpStatus.OK);
	}
	
	@PutMapping("/update-address")
	public ResponseEntity<User> updateAddress(@RequestBody Address address) throws CustomerException
	{
		return new ResponseEntity<User>(userService.updateAddress(address), HttpStatus.OK);
	}
	
	@PutMapping("/update-username")
	public ResponseEntity<User> updateUsername(@RequestParam("username") String newUsername) throws CustomerException
	{
		return new ResponseEntity<User>(userService.updateUsername(newUsername), HttpStatus.OK);
	}
	@PutMapping("/update-password")
	public ResponseEntity<User> updatePassword(@RequestParam("password") String password) throws CustomerException
	{
		return new ResponseEntity<User>(userService.updatePassword(password), HttpStatus.OK);
	}
}
