package com.buy_EZ.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.exceptions.CategoryException;
import com.buy_EZ.models.Admin;
import com.buy_EZ.models.AdminCurrentSession;
import com.buy_EZ.models.AdminDto;
import com.buy_EZ.models.Category;
import com.buy_EZ.repositories.AdminCurrentSessionRepo;
import com.buy_EZ.repositories.AdminRepo;
import com.buy_EZ.repositories.CategoryRepo;

import net.bytebuddy.utility.RandomString;


@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	private AdminCurrentSessionRepo adminCurrentSession;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public AdminDto insertAdmin(Admin admin, String id) throws AdminException {
		// TODO Auto-generated method stub
		//finding the current session of the admin who is trying to insert the new admin
		Optional<AdminCurrentSession> session  = adminCurrentSession.findById(id);
		
		if(session.get()!=null) {
			
			if(adminRepo.findByUsername(admin.getUsername())==null) {
				admin.setAdminId(admin.getUsername()+"_"+RandomString.make(6));
				
				adminRepo.save(admin);
				
				AdminDto details = new AdminDto(admin.getAdminId(), admin.getUsername(), admin.getPassword()); 
				
				return details;
			}
			
			throw new AdminException("An admin is already present with the username "+admin.getUsername());
			
		}else {
			throw new AdminException("No admin is logged in with the id "+id);
		}
		
	}

	@Override
	public Category insertCategory(Category category, String loggedInAdminId) throws AdminException, CategoryException {
		// TODO Auto-generated method stub
		
		if(categoryRepo.findByCategoryName(category.getCategoryName())==null) {
			
			if(adminCurrentSession.findById(loggedInAdminId).get()!=null) {
				category.setCategoryId(category.getCategoryName()+"_"+RandomString.make(7));
				return categoryRepo.save(category);
				
			}
			throw new AdminException("No admin is logged in with the id "+loggedInAdminId);
			
		}
		throw new CategoryException("A category is already present with the name "+category.getCategoryName());
	}

	
	
}
