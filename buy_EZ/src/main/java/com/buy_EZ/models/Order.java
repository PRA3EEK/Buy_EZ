package com.buy_EZ.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import net.bytebuddy.utility.RandomString;

@Data
@Entity
public class Order {
    
	
	@Id
	private String orderId;
	private LocalDateTime orderDate;
	private String orderStatus;
	private double billAmount;
	@ManyToOne
	private User customer;
	@OneToMany
	private List<Product> products = new ArrayList<>();
	
	public Order(User customer) {
		double bill = 0;
		List<Product> products = customer.getCart().getProducts();
		for(Product p:products) {
			bill+=p.getPrice()*p.getQuantity();
		}
		
		this.orderId = customer.getUsername()+"_"+RandomString.make(7)+"_"+LocalDateTime.now();
	    this.orderDate = LocalDateTime.now();
	    this.orderStatus = "Pending";
	    this.billAmount = bill;
	    this.customer = customer;
	    this.products = customer.getCart().getProducts();
	
	}

	
}
