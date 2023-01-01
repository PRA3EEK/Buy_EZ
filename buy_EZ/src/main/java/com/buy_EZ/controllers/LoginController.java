package com.buy_EZ.controllers;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.buy_EZ.models.SignupRequest;
import com.buy_EZ.models.User;
import com.buy_EZ.models.UserResponse;
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
	@Autowired
	private PasswordEncoder encoder;
	
	
	@PostMapping("/admin")
	public ResponseEntity<CustomerCurrentSession> adminLogin( @RequestBody AdminDto admin) throws AdminException {
		return new ResponseEntity<CustomerCurrentSession>(loginService.adminLogin(admin), HttpStatus.OK);
	}
	
	
	
	@PostMapping("/customer/register")
	public ResponseEntity<CustomerDto> registerCustomer(@RequestBody SignupRequest request) throws CustomerException{
		return new ResponseEntity<CustomerDto>(loginService.customerRegister(request), HttpStatus.OK);
	}
	
	
	@PostMapping("/customer")
	public ResponseEntity<?> loginCustomer(@Valid @RequestBody CustomerDto customerDto) throws CustomerException{

		Object[] result = loginService.customerLogin(customerDto);
		User u = (User)result[1];
		List<GrantedAuthority> roles = (List<GrantedAuthority>)result[2];
		
		System.out.println(roles);
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, result[0].toString()).body(new UserResponse(customerDto.getId(), customerDto.getUsername(), u.getEmail(), roles));
	}
	
	@PostMapping("/admin/register")
	public ResponseEntity<AdminDto> registerAdmin(@RequestBody Admin admin) throws AdminException{
		return new ResponseEntity<AdminDto>(loginService.adminRegister(admin), HttpStatus.OK);
	}
	
}
