package com.buy_EZ.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.models.Admin;
import com.buy_EZ.models.AdminCurrentSession;
import com.buy_EZ.models.AdminDto;
import com.buy_EZ.repositories.AdminCurrentSessionRepo;
import com.buy_EZ.repositories.AdminRepo;
import com.buy_EZ.services.LoginService;
import com.buy_EZ.services.LoginServiceImpl;

import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	
	@PostMapping("/admin")
	public ResponseEntity<AdminCurrentSession> adminLogin( @RequestBody AdminDto admin) throws AdminException {
		
		return new ResponseEntity<AdminCurrentSession>(loginService.adminLogin(admin), HttpStatus.OK);
     
		
	}
	
}
