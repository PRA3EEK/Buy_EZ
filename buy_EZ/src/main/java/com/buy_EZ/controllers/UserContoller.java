package com.buy_EZ.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buy_EZ.exceptions.CategoryException;
import com.buy_EZ.exceptions.CustomerException;
import com.buy_EZ.exceptions.PaymentException;
import com.buy_EZ.exceptions.ProductException;
import com.buy_EZ.models.Cart;
import com.buy_EZ.models.Category;
import com.buy_EZ.models.Order;
import com.buy_EZ.models.Product;
import com.buy_EZ.models.ProductDTO;
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
	public ResponseEntity<List<Product>> searchProduct(@RequestParam(value = "name") String name, @RequestParam(value = "loggedInId") String id) throws ProductException, CustomerException{
		return new ResponseEntity<List<Product>>(userService.searchProductsByname(name, id), HttpStatus.OK);
	}
	@GetMapping("/search/category")
	public ResponseEntity<List<Product>> searchProductsByCategoryName(@RequestParam(value = "categoryName") String categoryName, @RequestParam(value = "loggedInId") String id) throws CategoryException, ProductException, CustomerException{
		return new ResponseEntity<List<Product>>(userService.searchProductByCategory(categoryName, id), HttpStatus.OK);
	}
	@PostMapping("/rating")
	public ResponseEntity<Product> addRatings(@RequestParam(value = "productId") String productId, @RequestParam(value = "loggedInId") String id, @RequestParam(value = "rating") Double rating) throws ProductException, CustomerException{
		return new ResponseEntity<Product>(userService.addRating(productId, id, rating), HttpStatus.OK);
	}
	@GetMapping("/search/HTL")
	public ResponseEntity<List<Product>> searchProductsByNameSortHighToLow(@RequestParam(value = "name") String name, @RequestParam(value = "loggedInId") String id) throws ProductException, CustomerException{
		List<Product> products = userService.searchProductsByname(name, id);
		return new ResponseEntity<List<Product>>(userService.sortProductsByPriceHighToLow(products), HttpStatus.OK);
	}
	@GetMapping("/search/LTH")
	public ResponseEntity<List<Product>> searchProductsByNameSortLowToHigh(@RequestParam(value = "name") String name, @RequestParam(value = "loggedInId") String id) throws ProductException, CustomerException{
		List<Product> products = userService.searchProductsByname(name, id);
		return new ResponseEntity<List<Product>>(userService.sortProductsByPriceLowToHigh(products), HttpStatus.OK);
	}
	
	@GetMapping("/search/HTL/ratings")
	public ResponseEntity<List<Product>> searchProductByNamesSortHighToLowRatings(@RequestParam(value = "name") String name, @RequestParam(value = "loggedInId") String id) throws ProductException, CustomerException{
		List<Product> products = userService.searchProductsByname(name, id);
		return new ResponseEntity<List<Product>>(userService.sortProductsByRatingsHighToLow(products), HttpStatus.OK);
	}
	
	@GetMapping("/search/LTH/ratings")
	public ResponseEntity<List<Product>> searchProductByNamesSortLowToHighRatings(@RequestParam(value = "name") String name, @RequestParam(value = "loggedInId") String id) throws ProductException, CustomerException{
		List<Product> products = userService.searchProductsByname(name, id);
		return new ResponseEntity<List<Product>>(userService.sortProductsByRatingsLowToHigh(products), HttpStatus.OK);
	}
	
	@GetMapping("/searchById")
	public ResponseEntity<Product> getProductDetails(@RequestParam(value = "productId") String productId, @RequestParam(value = "loggedInId") String id) throws ProductException, CustomerException{
		return new ResponseEntity<Product>(userService.getProductDetailsById(productId, id), HttpStatus.OK);
	}
	
	@PostMapping("/searchById/add")
	public ResponseEntity<ProductDTO> addProductToCart(@RequestParam(value = "productId") String productId, @RequestParam(value = "loggedInId") String id, @RequestParam(value = "quantity") Integer quant) throws ProductException, CustomerException{
		return new ResponseEntity<ProductDTO>(userService.addToCart(productId, id, quant), HttpStatus.OK);
	}
	
	@DeleteMapping("/searchById/delete")
	public ResponseEntity<Product> deleteProductFromCart(@RequestParam(value = "productId") String productId, @RequestParam(value = "loggedInId") String id) throws ProductException, CustomerException{
        return new ResponseEntity<Product>(userService.deleteProductFromCart(productId, id), HttpStatus.OK);	
	}
	
	@GetMapping("/cart")
	public ResponseEntity<Cart> getCartDetails(@RequestParam("loggedInId") String id) throws CustomerException{
		return new ResponseEntity<Cart>(userService.getCartDetails(id), HttpStatus.OK);
	}
	
	@PostMapping("/cart/order")
	public ResponseEntity<Order> placeOrder(@RequestParam("loggedInId") String id, @RequestParam("paymentType") String paymentType) throws CustomerException, PaymentException{
		return new ResponseEntity<Order>(userService.placeOrder(id, paymentType), HttpStatus.OK);
	}
	@GetMapping("order/products")
	public ResponseEntity<List<ProductDTO>> getProductsFromOrder(@RequestParam("orderId") String orderId){
		return new ResponseEntity<List<ProductDTO>>(userService.getProductsFromOrder(orderId), HttpStatus.OK);
	}
	
	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategories()
	{
		return new ResponseEntity<List<Category>>(userService.getAllCategories(), HttpStatus.OK);
	}
}
