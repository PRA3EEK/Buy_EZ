package com.buy_EZ.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buy_EZ.DTO.CustomerDto;
import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.exceptions.CustomerException;
import com.buy_EZ.models.Admin;
import com.buy_EZ.models.AdminCurrentSession;
import com.buy_EZ.models.AdminDto;
import com.buy_EZ.models.CustomerCurrentSession;
import com.buy_EZ.models.User;
import com.buy_EZ.repositories.AdminCurrentSessionRepo;
import com.buy_EZ.repositories.AdminRepo;
import com.buy_EZ.services.LoginService;
import com.buy_EZ.services.LoginServiceImpl;

import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	
	@PostMapping("/admin")
	public ResponseEntity<AdminCurrentSession> adminLogin( @RequestBody AdminDto admin) throws AdminException {
		return new ResponseEntity<AdminCurrentSession>(loginService.adminLogin(admin), HttpStatus.OK);
	}
	
	
	
	@PostMapping("/customer/register")
	public ResponseEntity<CustomerDto> registerCustomer(@RequestBody User customer) throws CustomerException{
		return new ResponseEntity<CustomerDto>(loginService.customerRegister(customer), HttpStatus.OK);
	}
	
	
	@PostMapping("/customer/login")
	public ResponseEntity<CustomerCurrentSession> loginCustomer(@RequestBody CustomerDto customerDto) throws CustomerException{
		return new ResponseEntity<CustomerCurrentSession>(loginService.customerLogin(customerDto), HttpStatus.OK);
	}
	
	@PostMapping("/admin/register")
	public ResponseEntity<AdminDto> registerAdmin(@RequestBody Admin admin) throws AdminException{
		return new ResponseEntity<AdminDto>(loginService.adminRegister(admin), HttpStatus.OK);
	}
	
}
