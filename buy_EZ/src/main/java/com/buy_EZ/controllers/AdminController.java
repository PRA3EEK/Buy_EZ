package com.buy_EZ.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.exceptions.CategoryException;
import com.buy_EZ.models.Admin;
import com.buy_EZ.models.AdminDto;
import com.buy_EZ.models.Category;
import com.buy_EZ.repositories.AdminRepo;
import com.buy_EZ.services.AdminService;

import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
	private AdminRepo adminRepo;
    
    @Autowired
    private AdminService adminService;
	
    @PostMapping("/admin/{loggedInAdminId}")
	public ResponseEntity<AdminDto> insertAdmin(@Valid @RequestBody Admin admin, @PathVariable("loggedInAdminId") String adminId) throws AdminException {
    	return new ResponseEntity<AdminDto>(adminService.insertAdmin(admin, adminId), HttpStatus.OK);
	}
 
    
    @PostMapping("category/{loggedInAdminId}")
    public ResponseEntity<Category> insertCategory(@Valid @RequestBody Category category, @PathVariable("loggedInAdminId") String adminId) throws AdminException, CategoryException{
    	return new ResponseEntity<Category>(adminService.insertCategory(category, adminId), HttpStatus.OK);
    }
    
    @GetMapping("/msg")
	public String message() {
	
		return "Saved successfully";
	}
}
