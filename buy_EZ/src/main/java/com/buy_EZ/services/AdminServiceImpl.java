package com.buy_EZ.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.exceptions.CategoryException;
import com.buy_EZ.exceptions.OrderException;
import com.buy_EZ.exceptions.ProductException;
import com.buy_EZ.models.Admin;
import com.buy_EZ.models.AdminCurrentSession;
import com.buy_EZ.models.AdminDto;
import com.buy_EZ.models.Category;
import com.buy_EZ.models.Order;
import com.buy_EZ.models.Payment;
import com.buy_EZ.models.Product;
import com.buy_EZ.models.Shipper;
import com.buy_EZ.models.SubCategory;
import com.buy_EZ.models.Supplier;
import com.buy_EZ.models.User;
import com.buy_EZ.repositories.AdminCurrentSessionRepo;
import com.buy_EZ.repositories.AdminRepo;
import com.buy_EZ.repositories.CategoryRepo;
import com.buy_EZ.repositories.OrderRepo;
import com.buy_EZ.repositories.PaymentRepo;
import com.buy_EZ.repositories.ProductRepo;
import com.buy_EZ.repositories.ShipperRepo;
import com.buy_EZ.repositories.SubCategoryRepo;
import com.buy_EZ.repositories.SupplierRepo;

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
	
	@Autowired
	private SubCategoryRepo subCategoryRepo;
	
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private PaymentRepo paymentRepo;
	@Autowired
	private ShipperRepo shipperRepo;
	@Autowired
	private SupplierRepo supplierRepo;
	@Override
	public AdminDto insertAdmin(Admin admin, String loggedInAdminid) throws AdminException {
		// TODO Auto-generated method stub
		//finding the current session of the admin who is trying to insert the new admin
		Optional<AdminCurrentSession> session  = adminCurrentSession.findById(loggedInAdminid);
		
		if(session.get()!=null) {
			
			if(adminRepo.findByUsername(admin.getUsername())==null) {
				admin.setAdminId(admin.getUsername()+"_"+RandomString.make(6));
				
				adminRepo.save(admin);
				
				AdminDto details = new AdminDto(admin.getAdminId(), admin.getUsername(), admin.getPassword()); 
				
				return details;
			}
			
			throw new AdminException("An admin is already present with the username "+admin.getUsername());
			
		}else {
			throw new AdminException("No admin is logged in with the id "+loggedInAdminid);
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
	public Product insertProduct(Product product,String categoryName, String subCategoryName, String loggedInAdminId) throws AdminException, ProductException, CategoryException {
		// TODO Auto-generated method stub
		if(adminCurrentSession.findById(loggedInAdminId).get()!=null) 
		{
			
			if(categoryRepo.findByCategoryName(categoryName)!=null) 
			{
				if(subCategoryRepo.findByName(subCategoryName)!=null)
				{
					List<Product> products = productRepo.findAll();
					
					for(Product p:products)
					{
						if(p.getProductName().equals(product.getProductName()) && p.getColor().equals(product.getColor()) && p.getDimension().equals(product.getDimension()) && p.getSpecification().equals(product.getSpecification()))
						{
							throw new ProductException("product is already present");
						}
					}
						Category c = categoryRepo.findByCategoryName(categoryName);
							SubCategory sc = subCategoryRepo.findByName(subCategoryName);
								
								c.getProducts().add(product);
								sc.getProducts().add(product);
								product.setCategory(c);
								product.setSubCategory(sc);
								System.out.println(sc.getName());
								product.setProductId("_"+RandomString.make(12)+"_");
								
								return productRepo.save(product);
						
					
				}
				throw new CategoryException("No sub category is present with the name "+subCategoryName);
			}
			throw new CategoryException("No categroy present with the name "+categoryName);
		}
		throw new AdminException("No admin is logged in with the id "+loggedInAdminId);
	}

	public SubCategory insertSubCategory(SubCategory subCategory, String parentCategoryName, String loggedInAdminId) throws CategoryException, AdminException{
		
		if(adminCurrentSession.findById(loggedInAdminId).get()!=null)
		{
			if(categoryRepo.findByCategoryName(parentCategoryName)!=null)
			{
				if(subCategoryRepo.findByName(subCategory.getName())==null)
				{
					Category c = categoryRepo.findByCategoryName(parentCategoryName);
					c.getSubCategories().add(subCategory);
					subCategory.setParentCategory(c);
					subCategory.setSubCategoryId(UUID.randomUUID().toString());
				return subCategoryRepo.save(subCategory);
				}
				throw new CategoryException("sub category is already present with the name "+subCategory.getName());
			}
			throw new CategoryException("No category is present with the name "+parentCategoryName);
		}
     throw new AdminException("You are not logged in as an admin");	
	}
	
	public User searchByOrder(String orderid, String loggedInAdminId) throws OrderException, AdminException{
		
		if(adminCurrentSession.findById(loggedInAdminId).isPresent())
		{
			Optional<Order> oo = orderRepo.findById(orderid);
			
			if(oo.isPresent())
			{
				Order o = oo.get();
				return o.getUser();
			}
			throw new OrderException("No order has been placed with the order id "+orderid);			
		}
		throw new AdminException("You are no logged in as admin");
	}

	@Override
	public Payment addPaymentType(Payment payment, String loggedInAdminId) throws AdminException {
		// TODO Auto-generated method stub
		
		if(adminCurrentSession.findById(loggedInAdminId).isPresent())
		{
			return paymentRepo.save(payment);
			
		}
		throw new AdminException("Admin is not loggedIn");
	}

	@Override
	public Shipper addShipper(Shipper shipper, String loggedInAdminId) throws AdminException {
		// TODO Auto-generated method stub
		if(adminCurrentSession.findById(loggedInAdminId).isPresent())
		{
			return shipperRepo.save(shipper);
			
		}
		throw new AdminException("Admin is not loggedIn");
	}
	
	public Supplier addSupplier(Supplier supplier, String loggedInAdminId) throws AdminException
	{
		if(adminCurrentSession.findById(loggedInAdminId).isPresent())
		{
			return supplierRepo.save(supplier);
			
		}
		throw new AdminException("Admin is not loggedIn");
	}
}
