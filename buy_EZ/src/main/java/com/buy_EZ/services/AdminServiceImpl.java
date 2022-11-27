package com.buy_EZ.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.exceptions.CategoryException;
import com.buy_EZ.exceptions.ProductException;
import com.buy_EZ.models.Admin;
import com.buy_EZ.models.AdminCurrentSession;
import com.buy_EZ.models.AdminDto;
import com.buy_EZ.models.Category;
import com.buy_EZ.models.Product;
import com.buy_EZ.repositories.AdminCurrentSessionRepo;
import com.buy_EZ.repositories.AdminRepo;
import com.buy_EZ.repositories.CategoryRepo;
import com.buy_EZ.repositories.ProductRepo;

import net.bytebuddy.utility.RandomString;


@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	private AdminCurrentSessionRepo adminCurrentSession;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
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
				category.setCategoryId(category.getCategoryName().split(" ")[0]+"_"+RandomString.make(7));
				return categoryRepo.save(category);
				
			}
			throw new AdminException("No admin is logged in with the id "+loggedInAdminId);
			
		}
		throw new CategoryException("A category is already present with the name "+category.getCategoryName());
	}

	@Override
	public Product insertProduct(Product product,String categoryName, String loggedInAdminId) throws AdminException, ProductException, CategoryException {
		// TODO Auto-generated method stub
		if(adminCurrentSession.findById(loggedInAdminId).get()!=null) {
			
			if(categoryRepo.findByCategoryName(categoryName)!=null) {
				
				Category c = categoryRepo.findByCategoryName(categoryName);
				
				c.getProducts().add(product);
				
				product.setCategory(c);
				product.setProductId("_"+RandomString.make(12)+"_");
				
				return productRepo.save(product);
				
			}
			throw new CategoryException("No categroy present with the name "+categoryName);
		}
		throw new AdminException("No admin is logged in with the id "+loggedInAdminId);
	}

	
	
}
