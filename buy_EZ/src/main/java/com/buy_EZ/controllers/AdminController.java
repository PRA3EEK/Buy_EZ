package com.buy_EZ.controllers;


import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.buy_EZ.models.AbstractUser;

@RestController
public class AdminController {

	
	@PostMapping("/check")
	public ResponseEntity<AbstractUser> getAdmin(@Valid @RequestBody AbstractUser au){
		
		return new ResponseEntity<AbstractUser>(au, HttpStatus.OK);
	}
	
}
