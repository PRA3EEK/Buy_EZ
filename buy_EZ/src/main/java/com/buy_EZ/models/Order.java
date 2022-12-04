package com.buy_EZ.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.utility.RandomString;

@Data
@Entity
@Table(name = "Orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
	
	@Id
	private String orderId;
	private LocalDateTime orderDate;
	private String orderStatus;
	private double billAmount;
	@ManyToOne
	private User user;
	@OneToMany
	private List<Product> products = new ArrayList<>();
	
	public Order(User customer) {
		double bill = 0;
		List<Product> products = customer.getCart().getProducts();
		List<ProductDTO> cartProducts = customer.getCart().getCartProducts();
		for(ProductDTO p:cartProducts) {
			bill+=p.getPrice()*p.getQuantity();
		}
		
		this.orderId = customer.getUsername()+"_"+RandomString.make(7)+"_"+LocalDateTime.now();
	    this.orderDate = LocalDateTime.now();
	    this.orderStatus = "Pending";
	    this.billAmount = bill;
	    this.user = customer;
	    this.products = products;
	
	}

	
}
