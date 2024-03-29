package com.buy_EZ.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.buy_EZ.DTO.CartDTO;
import com.buy_EZ.comparators.HighToLowByPrice;
import com.buy_EZ.comparators.HighToLowByRatings;
import com.buy_EZ.comparators.LowToHighByPrice;
import com.buy_EZ.comparators.LowToHighByRatings;
import com.buy_EZ.exceptions.CategoryException;
import com.buy_EZ.exceptions.CustomerException;
import com.buy_EZ.exceptions.PaymentException;
import com.buy_EZ.exceptions.ProductException;
import com.buy_EZ.models.Address;
import com.buy_EZ.models.Cart;
import com.buy_EZ.models.Category;
import com.buy_EZ.models.Order;
import com.buy_EZ.models.OrderDetails;
import com.buy_EZ.models.Payment;
import com.buy_EZ.models.Product;
import com.buy_EZ.models.ProductDTO;
import com.buy_EZ.models.Shipper;
import com.buy_EZ.models.SubCategory;
import com.buy_EZ.models.Supplier;
import com.buy_EZ.models.User;
import com.buy_EZ.repositories.CategoryRepo;
import com.buy_EZ.repositories.CustomerCurrentSessionRepo;
import com.buy_EZ.repositories.CustomerRepo;
import com.buy_EZ.repositories.OrderDetailRepo;
import com.buy_EZ.repositories.OrderRepo;
import com.buy_EZ.repositories.PaymentRepo;
//import com.buy_EZ.repositories.ProductDtoRepo;
import com.buy_EZ.repositories.ProductRepo;
import com.buy_EZ.repositories.ShipperRepo;
import com.buy_EZ.repositories.SubCategoryRepo;
import com.buy_EZ.repositories.SupplierRepo;

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
//	@Autowired
//	private ProductDtoRepo productDtoRepo;
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private ShipperRepo shipperRepo;
	@Autowired
	private SupplierRepo supplierRepo;
	@Autowired
	private OrderDetailRepo orderDetailRepo;
	@Autowired
	private PaymentRepo paymentRepo;
	@Autowired
	private SubCategoryRepo subCategoryRepo;

	@Override
	public List<Product> searchProductsByname(String name) throws ProductException, CustomerException {
		// TODO Auto-generated method stub
              
		 
				Set<Product> products = new HashSet<>();
		
				
				List<Product> allProducts = productRepo.findAll();
				
				for(Product p:allProducts)
				{
					if(p.getProductName().toLowerCase().matches("(.*)"+name+"(.*)") || name.matches("(.*)"+p.getProductName().toLowerCase()+"(.*)"))
					{
						products.add(p);
					}
				}
			 
				List<SubCategory> allSubCategories = subCategoryRepo.findAll();

				for(SubCategory sc:allSubCategories)
				{
					if(sc.getName().toLowerCase().matches("(.*)"+name.toLowerCase()+"(.*)") || name.toLowerCase().matches("(.*)"+sc.getName().toLowerCase()+"(.*)"))
					{
						products.addAll(sc.getProducts());
					}
				}
				
				List<Category> allCategories = categoryRepo.findAll();

				for(Category c:allCategories)
				{
					if(c.getCategoryName().toLowerCase().matches("(.*)"+name.toLowerCase()+"(.*)") || name.toLowerCase().matches("(.*)"+c.getCategoryName().toLowerCase()+"(.*)"))
					{
						products.addAll(c.getProducts());
					}	
				}
				if(products.size() > 0) return new ArrayList<>(products);
				
				throw new ProductException("No product found");
	}

	@Override
	public List<Product> searchProductByCategory(String categoryName) throws CategoryException, ProductException, CustomerException {

		List<Product> res = new ArrayList<>();
		Pattern pattern = Pattern.compile(categoryName+"?");
        Matcher m = null; 
              List<SubCategory> allSubCategories = subCategoryRepo.findAll();
              if(allSubCategories.size()>0)
              {
                  for(SubCategory sc:allSubCategories)
                  {
                	  if(sc.getName().equalsIgnoreCase(categoryName))
                	  {
                		  res.addAll(sc.getProducts());
                	  }else
                	  {
                			 String[] nameSplit = sc.getName().split(" ");
                        	 for(String s:nameSplit)
                        	 {
                        		 m = pattern.matcher(s);
                        		 if(!s.equals("&")) {
                        			 if(s.equalsIgnoreCase(categoryName) || m.find())
                        			 {
                        				 res.addAll(sc.getProducts());
                        				 break;
                        			 }
                        		 }
                        	 
                	  }
                }
                  }
                  return res;
              }else
              {
                 List<Category> allCategories = categoryRepo.findAll();
                 if(allCategories.size()>0)
                 {
                	 for(Category c:allCategories)
                	 {
                		 if(c.getCategoryName().equalsIgnoreCase(categoryName))
                		 {
                			 res.addAll(c.getProducts());
                		 }else
                		 {
                			 String[] splitName = c.getCategoryName().split(" ");
                			 for(String s:splitName)
                			 {
                				m = pattern.matcher(s);
                				if(!s.equals("&"))
                				{
                					if(s.equals(categoryName) || m.find())
                					{
                						res.addAll(res);
                						break;
                					}
                				}
                				
                			 }
                		 }
                	 }
                	 return res;
                 }
                 throw new CategoryException("No category found with the name "+categoryName);
              }


			

	}

	@Override
	public Product addRating(String productId, Double rating) throws ProductException, CustomerException {
		// TODO Auto-generated method stub
    
		if(rating > 5 && rating<0) throw new ValidationException("Rating should be between 0 and 5");
				if (productRepo.findById(productId).isPresent()) {
					Product p = productRepo.findById(productId).get();
					Double newRating = rating;
					Double prevRating = p.getRatings();
					Integer numOfRatings = p.getNumberOfRatings();
					p.setRatings((prevRating + newRating) / (numOfRatings + 1));
					p.setNumberOfRatings((numOfRatings + 1));
					return productRepo.save(p);
				}
				throw new ProductException("No product present with the id " + productId);

	}

	@Override
	public List<Product> sortProductsByPriceHighToLow(List<Product> products) throws ProductException {
		// TODO Auto-generated method stub
		Collections.sort(products, new HighToLowByPrice());
		if (products.size() == 0) {
			throw new ProductException("Product list is empty");
		}
		return products;
	}

	@Override
	public List<Product> sortProductsByPriceLowToHigh(List<Product> products) throws ProductException {
		// TODO Auto-generated method stub
		Collections.sort(products, new LowToHighByPrice());
		if (products.size() == 0) {
			throw new ProductException("Product list is empty");
		}
		return products;
	}

	@Override
	public List<Product> sortProductsByRatingsHighToLow(List<Product> products) throws ProductException {
		// TODO Auto-generated method stub
		Collections.sort(products, new HighToLowByRatings());
		if (products.size() == 0) {
			throw new ProductException("Product list is empty");
		}
		return products;
	}

	@Override
	public List<Product> sortProductsByRatingsLowToHigh(List<Product> products) throws ProductException {
		// TODO Auto-generated method stub
		Collections.sort(products, new LowToHighByRatings());
		if (products.size() == 0) {
			throw new ProductException("Product list is empty");
		}
		return products;
	}

	@Override
	public Product getProductDetailsById(String productId) throws ProductException, CustomerException {
		// TODO Auto-generated method stub

			if (productRepo.findById(productId).get() != null) {
				return productRepo.findById(productId).get();
			}
			throw new ProductException("No product present with the id " + productId);


	}

	@Override
	public ProductDTO addToCart(String productId, Integer quantity)
			throws ProductException, CustomerException {
		// TODO Auto-generated method stub

			if (productRepo.findById(productId).isPresent()) {

			    String username = GetSubject.getUsername();
				User customer = customerRepo.findByUsername(username);
				if(customer==null) throw new CustomerException("No user found with the username "+username);
				
				Product p = productRepo.findById(productId).get();
				List<ProductDTO> products = customer.getCart().getCartProducts();

				for (ProductDTO pro : products) {
					if (pro.getProductDtoId().equals(p.getProductId())) {
						p.setQuantity(p.getQuantity() - quantity);
                        pro.setQuantity(pro.getQuantity()+quantity);
                        customerRepo.save(customer);
                        return pro;
					}
				}

				if (p.getQuantity() > quantity) {
					p.setQuantity(p.getQuantity() - quantity);
//					productRepo.save(p);
					ProductDTO pd = new ProductDTO(p.getProductId(), quantity);
					customer.getCart().getProducts().add(p);
					customer.getCart().getCartProducts().add(pd);
					productRepo.save(p);
					customerRepo.save(customer);
					return pd;
				}
				throw new ProductException("only " + p.getQuantity() + " are left of " + p.getProductName());

			}
			throw new ProductException("No product present with the id " + productId);

//	return null;
	}
	
	
	

	public Product deleteProductFromCart(String productId)
			throws ProductException, CustomerException {

		String username = GetSubject.getUsername();
		User customer = customerRepo.findByUsername(username);

		if (customer!=null) {
			
				

				List<Product> products = customer.getCart().getProducts();
				List<ProductDTO> cartProducts = customer.getCart().getCartProducts();
				Product ogp = productRepo.findById(productId).get();
				Product res = null;
				ProductDTO pd = cartProducts.stream().filter((product) -> product.getProductDtoId().equals(productId))
						.findAny().get();
				if (pd == null) {
					throw new ProductException("No product present in the cart with id " + productId);
				}

				if (ogp != null) {

					for (Product p : products) {
						if (p.getProductId().equals(ogp.getProductId())) {
							ogp.setQuantity(ogp.getQuantity() + pd.getQuantity());
							productRepo.save(ogp);
							res = p;
							products.remove(p);
							cartProducts.remove(pd);
							customerRepo.save(customer);
							return res;
						}
					}
					throw new ProductException("No product present in the cart with id " + productId);
				} else {

					for (Product p : products) {
						if (p.getProductId().equals(productId)) {
							products.remove(p);
							cartProducts.remove(pd);
							productRepo.save(p);
							res = p;
							customerRepo.save(customer);
							return res;

						}
					}
					throw new ProductException("No product present with the id " + productId);
				}

			}
			throw new CustomerException("User is not logged in");
		
	
	}

	public CartDTO getCartDetails() throws CustomerException {
     
		String username = GetSubject.getUsername();
		User u = customerRepo.findByUsername(username);
		

		if (u != null) {

		

				Cart cart = u.getCart();
				double totalAmount = 0;
				double totalPayAmount = 0;
				int num = 0;
				for(ProductDTO pdto:cart.getCartProducts())
				{
					Product p = productRepo.findById(pdto.getProductDtoId()).get();
					totalAmount += p.getMarket_price()*pdto.getQuantity();
					totalPayAmount += p.getSale_price()*pdto.getQuantity();
					num ++;
				}

       return new CartDTO(num, totalAmount, totalPayAmount, totalAmount-totalPayAmount, cart.getCartProducts());			

		}

		throw new CustomerException("User is not registered");

	}

	@Override
	public Order placeOrder(String paymentType, Address address) throws CustomerException, PaymentException {
		// TODO Auto-generated method stub
		String username = GetSubject.getUsername();
		if (customerRepo.findByUsername(username)!=null) {
	
				User customer = customerRepo.findByUsername(username);
				List<Shipper> shippers = shipperRepo.findAll();
				if (!customer.getCart().getCartProducts().isEmpty()) {
					Order o = new Order(customer, address);
					List<Product> products = customer.getCart().getProducts();
					List<ProductDTO> cartProducts = customer.getCart().getCartProducts();
					//providing the supplier according to the country and the number of orders;
					List<Supplier> suppliers = supplierRepo.findByCountry(customer.getCountry());
					Random r = new Random();
					int low = 0;
					int high = suppliers.size();
					int ind = r.nextInt(high - low) + low;
					Supplier s = null;
					if(ind>suppliers.size())
					{
						s = suppliers.get(0);
					}else
					{
						s = suppliers.get(ind);
					}
					//======================================================
					//providing the payment type to the order object by taking from the user;
					Payment payment = paymentRepo.findByType(paymentType);
					if(payment.getAllowed().equalsIgnoreCase("yes"))
					{ 
						payment.getOrders().add(o);
						o.setPayment(payment);
					}else
					{
						throw new PaymentException("Payment type"+paymentType+" is not allowed");
					}
					//making the order detail objects for each product and persisting in the database;
					orderRepo.save(o);
					for(int i=0; i<products.size() ;i++)
					{
						OrderDetails od = new OrderDetails();
						od.setOrder(o);
						od.setProduct(products.get(i));
						od.setQuantity(cartProducts.get(i).getQuantity());
						od.setSupplier(s);
						orderDetailRepo.save(od);
					}
					for(Shipper shipper:shippers)
					{
						//checking if there is any order which has to be shipped on the same date as this order
					  List<Order> ordersOfSameDate = shipper.getOrders().stream().filter(order -> order.getShipDate() == LocalDate.now()).toList();
					  if(ordersOfSameDate.size()>100)
					  {
						  continue;
					  }else
					  {
						  shipper.getOrders().add(o);
						  o.setShipper(shipper);
						  break;
					  }
					}
					List<Product> copyProducts = products;
					List<ProductDTO> copyUserProducts = cartProducts; 
					o.getProducts().addAll(copyProducts);
					o.getUserProducts().addAll(copyUserProducts);
					//=======================================================
//					cartProducts.stream().forEach(pd -> {
//						productDtoRepo.delete(pd);
//					});
					
					 orderRepo.save(o);
					//removing all the products from the cart collections

					
				
					//updating the customer with the new instance;
					customerRepo.save(customer);
					return o;
				}
				throw new CustomerException("No product is present in the cart");
	
		}
		throw new CustomerException("Invalid customer");
	}

    public List<ProductDTO> getProductsFromOrder(String orderId){
    	return orderRepo.findById(orderId).get().getUserProducts();
    }

	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryRepo.findAll();
	}

	@Override
	public List<Product> getAllProducts(int pageNumber, int size) {
		// TODO Auto-generated method stub
		Pageable page = PageRequest.of(pageNumber, size);
        return productRepo.findAll(page).getContent();
	}

	@Override
	public User getUserDetails() throws CustomerException {
		// TODO Auto-generated method stub
     String username = GetSubject.getUsername();
      User u = customerRepo.findByUsername(username);
      if(u != null)
      {
    	  return u;
      }
      throw new CustomerException("No customer found with the username "+username);
	}

	@Override
	public List<SubCategory> getAllSubCategories() {
		// TODO Auto-generated method stub
		return subCategoryRepo.findAll();
	}

	@Override
	public List<Order> getAllOrdersOfUser() throws CustomerException {
		// TODO Auto-generated method stub
		String username = GetSubject.getUsername();
		
		User customer = customerRepo.findByUsername(username);
		
		if(customer!=null)
		{
			return customer.getOrders();
		}
		throw new CustomerException("No customer found with the username "+username);
	}

	@Override
	public Product updateProductQuantity(String productId, Integer quantity) throws ProductException {
		// TODO Auto-generated method stub
		// getting product from the productRepo and setting its quantity back
		Product originalProduct = productRepo.findById(productId).get();
		//getting product same product from the user cart to update its value
		String username = GetSubject.getUsername();
		User u = customerRepo.findByUsername(username);
		List<ProductDTO> cartProducts =  u.getCart().getCartProducts();
		
		for(ProductDTO productDto:cartProducts)
		{
			if(productDto.getProductDtoId().equals(originalProduct.getProductId()))
			{
				originalProduct.setQuantity(originalProduct.getQuantity()+productDto.getQuantity());
				originalProduct.setQuantity(originalProduct.getQuantity() - quantity);
				productDto.setQuantity(quantity);
				customerRepo.save(u);
				return productRepo.save(originalProduct);
			}
		}
		
		throw new ProductException("No product found with the product id "+productId);
	}

	@Override
	public List<Payment> getAllPaymentType() {
		// TODO Auto-generated method stub
		return paymentRepo.findAll();
	}

	@Override
	public User updateAddress(Address address) throws CustomerException {
		// TODO Auto-generated method stub
		String username = GetSubject.getUsername();
		
		User user = customerRepo.findByUsername(username);
		
		if(user != null)
		{
			user.setAddress(address);
			return customerRepo.save(user);
		}
		throw new CustomerException("No customer found with the username "+username);
	}
	@Override
	public User updateUsername(String newUsername) throws CustomerException
	{
		
		String username = GetSubject.getUsername();
		
		if(customerRepo.findByUsername(username)!=null)
		{
			User user = customerRepo.findByUsername(username);
			user.setUsername(newUsername);
			return customerRepo.save(user);
		}
		throw new CustomerException("No user found with the username "+newUsername);
	}
	@Override
	public User updatePassword(String password) throws CustomerException{
		
         String username = GetSubject.getUsername();
		
		if(customerRepo.findByUsername(username)!=null)
		{
			User user = customerRepo.findByUsername(username);
			user.setPassword(new BCryptPasswordEncoder().encode(password));
			return customerRepo.save(user);
		}
		throw new CustomerException("No user found with the username "+username);
		
		
	}
}
