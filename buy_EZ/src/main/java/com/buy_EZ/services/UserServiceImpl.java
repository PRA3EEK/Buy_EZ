package com.buy_EZ.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.buy_EZ.comparators.HighToLowByPrice;
import com.buy_EZ.comparators.HighToLowByRatings;
import com.buy_EZ.comparators.LowToHighByPrice;
import com.buy_EZ.comparators.LowToHighByRatings;
import com.buy_EZ.exceptions.CategoryException;
import com.buy_EZ.exceptions.CustomerException;
import com.buy_EZ.exceptions.ProductException;
import com.buy_EZ.models.Cart;
import com.buy_EZ.models.Category;
import com.buy_EZ.models.CustomerCurrentSession;
import com.buy_EZ.models.Product;
import com.buy_EZ.models.User;
import com.buy_EZ.repositories.CategoryRepo;
import com.buy_EZ.repositories.CustomerCurrentSessionRepo;
import com.buy_EZ.repositories.CustomerRepo;
import com.buy_EZ.repositories.ProductRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CustomerCurrentSessionRepo userSessionRepo;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public List<Product> searchProductsByname(String name, String loggedInUserId)
			throws ProductException, CustomerException {
		// TODO Auto-generated method stub
		Optional<User> uOptional = customerRepo.findById(loggedInUserId);

		if (uOptional.get() != null) {
			Optional<CustomerCurrentSession> cOptional = userSessionRepo.findById(loggedInUserId);
			if (cOptional.get() != null) {
				return productRepo.searchProductsByName(name, name.split(" ")[0]);
			}
			throw new CustomerException("Customer with id " + loggedInUserId + " is not logged in");
		}
		throw new CustomerException("No customer exists with the user id " + loggedInUserId);
	}

	@Override
	public List<Product> searchProductByCategory(String categoryName, String loggedInUserId)
			throws CategoryException, ProductException, CustomerException {
		if (customerRepo.findById(loggedInUserId).get() != null) {

			if (userSessionRepo.findById(loggedInUserId).get() != null) {
				Category c = categoryRepo.findByCategoryName(categoryName);

				if (c != null) {
					return productRepo.searchProductsByCategory(categoryName, categoryName.split(" ")[0]);
				}
				throw new CategoryException("No category is present with the name " + categoryName);
			}
			throw new CustomerException("You are not logged in with the id " + loggedInUserId);

		}
		throw new CustomerException("No customer present with the id " + loggedInUserId);

	}

	@Override
	public Product addRating(String productId, String loggedInUserId, Double rating)
			throws ProductException, CustomerException {
		// TODO Auto-generated method stub
          if(customerRepo.findById(loggedInUserId).get()!=null) {
        	  if (userSessionRepo.findById(loggedInUserId).get() != null) 
        	  {
        		  if(productRepo.findById(productId).get()!=null) 
        		  {
        			  Product p = productRepo.findById(productId).get();
        			  Double newRating = rating;
        			  Double prevRating = p.getRatings();
        			  Integer numOfRatings = p.getNumberOfRatings();
        			  p.setRatings((prevRating+newRating)/(numOfRatings+1));
        			  p.setNumberOfRatings((numOfRatings+1));
        			  return productRepo.save(p);
        		  }
        		  throw new ProductException("No product present with the id "+productId);
        	  }
        	  throw new CustomerException("You are not logged in with the id " + loggedInUserId);
          }
          throw new CustomerException("No customer present with the id " + loggedInUserId);
	}

	@Override
	public List<Product> sortProductsByPriceHighToLow(List<Product> products)
			throws ProductException{
		// TODO Auto-generated method stub
		Collections.sort(products, new HighToLowByPrice());
		if(products.size()==0) {
			throw new ProductException("Product list is empty");
		}
		return products;
	}

	@Override
	public List<Product> sortProductsByPriceLowToHigh(List<Product> products) throws ProductException {
		// TODO Auto-generated method stub
		Collections.sort(products, new LowToHighByPrice());
		if(products.size()==0) {
			throw new ProductException("Product list is empty");
		}
		return products;
	}

	@Override
	public List<Product> sortProductsByRatingsHighToLow(List<Product> products) throws ProductException {
		// TODO Auto-generated method stub
		Collections.sort(products, new HighToLowByRatings());
		if(products.size()==0) {
			throw new ProductException("Product list is empty");
		}
		return products;
	}

	@Override
	public List<Product> sortProductsByRatingsLowToHigh(List<Product> products) throws ProductException {
		// TODO Auto-generated method stub
		Collections.sort(products, new LowToHighByRatings());
		if(products.size()==0) {
			throw new ProductException("Product list is empty");
		}
		return products;
	}

	@Override
	public Product getProductDetailsById(String productId, String loggedInUserId) throws ProductException, CustomerException {
		// TODO Auto-generated method stub
		if(userSessionRepo.findById(loggedInUserId).get()!=null) {
			if(productRepo.findById(productId).get()!=null)
			{
				return productRepo.findById(productId).get();
			}
			throw new ProductException("No product present with the id "+productId);
		}
		throw new CustomerException("You are not logged in with the id "+loggedInUserId);
		
	}

	@Override
	public Product addToCart(String productId, String loggedInId, Integer quantity) throws ProductException, CustomerException {
		// TODO Auto-generated method stub
		if(userSessionRepo.findById(loggedInId).get()!=null) 
		{
			if(productRepo.findById(productId).get()!=null)
			{
				
				User customer = customerRepo.findById(loggedInId).get();
				Product p = productRepo.findById(productId).get();
				List<Product> products = customer.getCart().getProducts();
				List<Product> cartProducts = customer.getCart().getCartProducts();
				
				for(Product pro:cartProducts) 
				{
					if(pro.getProductId() == p.getProductId()) 
					{
						throw new ProductException("Product is already present in the cart");
					}
				}
				
				
				if(p.getQuantity()>quantity) 
				{
					p.setQuantity(p.getQuantity()-quantity);
					productRepo.save(p);
					customer.getCart().getProducts().add(p);
					p.setQuantity(quantity);
					customer.getCart().getCartProducts().add(p);
					customerRepo.save(customer);
					return p;
				}
				throw new ProductException("only "+p.getQuantity()+" are left of "+p.getProductName());
				
			}
			throw new ProductException("No product present with the id "+productId);
		}
		throw new CustomerException("You are not logged in with the id "+loggedInId);
	}

	public Product deleteProductFromCart(String productId, String loggedInId) throws ProductException, CustomerException
	{
		
		Optional<User> op = customerRepo.findById(loggedInId);
		
		if(op.get()!=null)
		{
			Optional<CustomerCurrentSession> op2 = userSessionRepo.findById(loggedInId);
		    if(op2.get()!=null)
		    {
		    	User customer = op.get();
		    	
		    	List<Product> products = customer.getCart().getProducts();
		    	Product ogp = productRepo.findById(productId).get();
		    	Product res = null;
		    	if(ogp!=null)
		    	{
		    		for(Product p:products) 
			    	{
			    		if(p.getProductId() == ogp.getProductId()) 
			    		{
			    			ogp.setQuantity(ogp.getQuantity()+p.getQuantity());
			    			productRepo.save(ogp);
			    			res = p;
			    			products.remove(p);
			    			break;
			    		}
			    	}	
		    		customer.getCart().setProducts(products);
		    		customerRepo.save(customer);
		    	return res;   
		    	}else {
		    		
		    		for(Product p:products) 
		    		{
		    			if(p.getProductId() == productId)
		    			{
		    				products.remove(p);
		    				productRepo.save(p);
		    				res  = p;
		    				break;
		    			}
		    		}
		    		customer.getCart().setProducts(products);
		    		customerRepo.save(customer);
		        return res;
		    	}
		    	
		    	
		    }
		 throw new CustomerException("user is not logged in");
		}
	  throw new CustomerException("User is not registered");
	}
	
	public Cart getCartDetails(String loggedInId) throws CustomerException 
	{
		
		Optional<User> user = customerRepo.findById(loggedInId);
		
		if(user.get()!=null) 
		{
			
			Optional<CustomerCurrentSession> op = userSessionRepo.findById(loggedInId);
			
			if(op.get()!=null) 
			{
				
				return user.get().getCart();
				
			}
			
			throw new CustomerException("You are not logged in");
			
		}
		
		throw new CustomerException("User is not registered");
		
	}
}
