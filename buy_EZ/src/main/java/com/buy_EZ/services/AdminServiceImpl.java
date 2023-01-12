package com.buy_EZ.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.exceptions.CategoryException;
import com.buy_EZ.exceptions.OrderException;
import com.buy_EZ.exceptions.PaymentException;
import com.buy_EZ.exceptions.ProductException;
import com.buy_EZ.exceptions.RoleException;
import com.buy_EZ.exceptions.ShipperException;
import com.buy_EZ.exceptions.SupplierException;
import com.buy_EZ.models.Admin;
import com.buy_EZ.models.AdminCurrentSession;
import com.buy_EZ.models.AdminDto;
import com.buy_EZ.models.Category;
import com.buy_EZ.models.CustomerCurrentSession;
import com.buy_EZ.models.ERole;
import com.buy_EZ.models.Order;
import com.buy_EZ.models.Payment;
import com.buy_EZ.models.Product;
import com.buy_EZ.models.Role;
import com.buy_EZ.models.Shipper;
import com.buy_EZ.models.SubCategory;
import com.buy_EZ.models.Supplier;
import com.buy_EZ.models.User;
import com.buy_EZ.repositories.AdminCurrentSessionRepo;
import com.buy_EZ.repositories.AdminRepo;
import com.buy_EZ.repositories.CategoryRepo;
import com.buy_EZ.repositories.CustomerCurrentSessionRepo;
import com.buy_EZ.repositories.CustomerRepo;
import com.buy_EZ.repositories.OrderRepo;
import com.buy_EZ.repositories.PaymentRepo;
import com.buy_EZ.repositories.ProductRepo;
import com.buy_EZ.repositories.RoleRepo;
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
     @Autowired
	private CustomerCurrentSessionRepo userSession;
     @Autowired
     private CustomerRepo userRepo;
     @Autowired
     private RoleRepo roleRepo;
	@Override
	public AdminDto insertAdmin(User admin) throws AdminException {
		// TODO Auto-generated method stub
		//finding the current session of the admin who is trying to insert the new admin
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		CustomerCurrentSession session =  userSession.findByUsername(name);
        
		
		if(session!=null) {
			
			if(userRepo.findByUsername(admin.getUsername())==null) {
				admin.setUserId("ad-cu"+"_"+RandomString.make(10));
				Set<Role> adminRoles = new HashSet<>();
				Role userRole = roleRepo.findByName(ERole.ROLE_USER).orElseThrow(() -> new RoleException("User Role not found"));
				Role adminRole = roleRepo.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RoleException("Admin Role not found"));
				
				adminRoles.add(userRole);
				adminRoles.add(adminRole);
				admin.setPassword(new BCryptPasswordEncoder().encode(admin.getPassword()));
				admin.setRoles(adminRoles);
				
				AdminDto details = new AdminDto(admin.getUserId(), admin.getUsername(), admin.getPassword()); 
				userRepo.save(admin);
				return details;
			}
			
			else {
				User userToAdmin = userRepo.findByUsername(admin.getUsername());
				Role adminRole = roleRepo.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RoleException("Admin Role not found"));
			    userToAdmin.getRoles().add(adminRole);
			    AdminDto details = new AdminDto(userToAdmin.getUserId(), userToAdmin.getUsername(), userToAdmin.getPassword()); 
				userRepo.save(userToAdmin);
				return details;
			}
			
		}else {
			throw new AdminException(name+" is not an authorized admin");
		}

	}

	@Override
	public Category insertCategory(Category category) throws AdminException, CategoryException {
		// TODO Auto-generated method stub
		if(categoryRepo.findByCategoryName(category.getCategoryName())==null) {
				category.setCategoryId(category.getCategoryName().split(" ")[0]+"_"+RandomString.make(7));
				return categoryRepo.save(category);	
			}	
		
		throw new CategoryException("A category is already present with the name "+category.getCategoryName());
	}

	@Override
	public Product insertProduct(Product product, String categoryName) throws AdminException, ProductException, CategoryException {
		// TODO Auto-generated method stub
		
		List<Product> products = productRepo.findAll();
		
		for(Product p:products)
		{
			if(p.getProductName().equals(product.getProductName()) && p.getColor().equals(product.getColor()) && p.getDimension().equals(product.getDimension()) && p.getSpecification().equals(product.getSpecification()))
			{
				throw new ProductException("product is already present");
			}
		}
				if(subCategoryRepo.findByName(categoryName)!=null)
				{
					
					SubCategory sc = subCategoryRepo.findByName(categoryName);
						Category c = sc.getParentCategory();
								

								sc.getProducts().add(product);
								product.setCategory(c);
								product.setSubCategory(sc);
								System.out.println(sc.getName());
								product.setProductId("_"+RandomString.make(12)+"_");
								
								return productRepo.save(product);
						
					
				}else
				{
					Category c = categoryRepo.findByCategoryName(categoryName);
					if(c!=null)
					{
						product.setCategory(c);
						c.getProducts().add(product);
						return productRepo.save(product);
					}
					throw new CategoryException("No category or sub category found with the name "+categoryName);
				}
			
	}

	public SubCategory insertSubCategory(SubCategory subCategory, String parentCategoryName) throws CategoryException, AdminException{
		
		
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
	@Override
	public User searchByOrder(String orderid) throws OrderException, AdminException{
		
			Optional<Order> oo = orderRepo.findById(orderid);
			
			if(oo.isPresent())
			{
				Order o = oo.get();
				return o.getUser();
			}
			throw new OrderException("No order has been placed with the order id "+orderid);			
		
		
	}

	@Override
	public Payment addPaymentType(Payment payment) throws AdminException, PaymentException {
		// TODO Auto-generated method stub
		if(paymentRepo.findByType(payment.getType()) == null)
			return paymentRepo.save(payment);
		
		throw new PaymentException("Payment type is already present");
	}

	@Override
	public Shipper addShipper(Shipper shipper) throws AdminException, ShipperException {
		// TODO Auto-generated method stub
      if(shipperRepo.findByCompanyName(shipper.getCompanyName()) == null)
			return shipperRepo.save(shipper);
    
      throw new ShipperException("Shipper already present");
	}
	
	public Supplier addSupplier(Supplier supplier) throws AdminException, SupplierException
	{
         if(supplierRepo.findByCompanyName(supplier.getCompanyName()) == null)
			return supplierRepo.save(supplier);

         throw new SupplierException("Supplier already present");
	}
}
