package com.buy_EZ.controllers;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buy_EZ.DTO.CustomerDto;
import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.exceptions.CustomerException;
import com.buy_EZ.jwt.JwtUtils;
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
@RequestMapping("/buy_EZ/auth")
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	
	@PostMapping("/admin")
	public ResponseEntity<CustomerCurrentSession> adminLogin( @RequestBody AdminDto admin) throws AdminException {
		return new ResponseEntity<CustomerCurrentSession>(loginService.adminLogin(admin), HttpStatus.OK);
	}
	
	
	
	@PostMapping("/register")
	public ResponseEntity<CustomerDto> registerCustomer(@RequestBody SignupRequest request) throws CustomerException{
		return new ResponseEntity<CustomerDto>(loginService.customerRegister(request), HttpStatus.OK);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> loginCustomer(@Valid @RequestBody CustomerDto customerDto) throws CustomerException{

		Object[] result = loginService.customerLogin(customerDto);
		User u = (User)result[1];
		List<GrantedAuthority> roles = (List<GrantedAuthority>)result[2];
		
		System.out.println(roles);
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, result[0].toString()).body(new UserResponse(customerDto.getId(), customerDto.getUsername(), u.getEmail(), roles, result[0].toString()));
	}
	
	@PostMapping("/admin/register")
	public ResponseEntity<AdminDto> registerAdmin(@RequestBody Admin admin) throws AdminException{
		return new ResponseEntity<AdminDto>(loginService.adminRegister(admin), HttpStatus.OK);
	}
	
	@DeleteMapping("/logout")
	public ResponseEntity<?> userLogout() throws CustomerException
	{	
      
//		System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, loginService.logout().toString()).body("User loggedOut succesfully");
	
	}
	
	@GetMapping("/getName")
	@PreAuthorize("hasRole('ADMIN')")
	public String method() {
       return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
	}
	
}
