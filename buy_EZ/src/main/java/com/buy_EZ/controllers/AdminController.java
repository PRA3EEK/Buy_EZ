package com.buy_EZ.controllers;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
import com.buy_EZ.repositories.AdminRepo;
import com.buy_EZ.services.AdminService;
import com.buy_EZ.services.UserService;

import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/buy_EZ/admin")
public class AdminController {
	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private AdminService adminService;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AdminDto> insertAdmin(@Valid @RequestBody User admin) throws AdminException {
		return new ResponseEntity<AdminDto>(adminService.insertAdmin(admin), HttpStatus.OK);
	}

	@PostMapping("/add-category")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Category> insertCategory(@Valid @RequestBody Category category) throws AdminException, CategoryException {
		return new ResponseEntity<Category>(adminService.insertCategory(category), HttpStatus.OK);
	}

	@PostMapping("/add-product/{categoryName}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Product> insertProduct(@Valid @RequestBody Product product,
			@PathVariable("categoryName") String name)
			throws AdminException, ProductException, CategoryException {

		return new ResponseEntity<Product>(adminService.insertProduct(product, name),
				HttpStatus.CREATED);

	}

	
	@PostMapping("/add-subCategory")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<SubCategory> insertSubCategory(@RequestBody SubCategory subCategory, @RequestParam("parentCategoryName") String name) throws CategoryException, AdminException{
		
		return new ResponseEntity<SubCategory>(adminService.insertSubCategory(subCategory, name), HttpStatus.OK);
		
	}
	@GetMapping("/order/customer")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> getCustomerByOrder(@RequestParam("orderId") String orderId, @RequestParam("loggedInAdminId") String adminId) throws OrderException, AdminException{
		return new ResponseEntity<User>(adminService.searchByOrder(orderId, adminId), HttpStatus.OK);
	}
	
	@PostMapping("/add-payment")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Payment> addPayment(@Valid@RequestBody Payment payment) throws AdminException, PaymentException{
		return new ResponseEntity<Payment>(adminService.addPaymentType(payment), HttpStatus.CREATED);
	}
	@PostMapping("/add-shipper")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Shipper> addShipper(@Valid@RequestBody Shipper shipper) throws AdminException, ShipperException{
		return new ResponseEntity<Shipper>(adminService.addShipper(shipper), HttpStatus.CREATED);
	}
	@PostMapping("/add-supplier")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Supplier> addSupplier(@Valid@RequestBody Supplier supplier) throws AdminException, SupplierException
	{
		return new ResponseEntity<Supplier>(adminService.addSupplier(supplier), HttpStatus.CREATED);
	}
	@GetMapping("/msg")
	@PreAuthorize("hasRole('ADMIN')")
	public String message() {

		return "Saved successfully";
	}
}
