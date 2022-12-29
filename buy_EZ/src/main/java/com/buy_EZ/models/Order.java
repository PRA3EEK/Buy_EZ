package com.buy_EZ.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private LocalDate shipDate;
	private LocalDate deliveryDate;
	private String orderStatus;
	private double billAmount;
	@ManyToOne
	private User user;
	@OneToMany@JsonIgnore
	private List<Product> products = new ArrayList<>();
	@ManyToOne
	private Shipper shipper;
	@ManyToOne
	private Payment payment;
	@ElementCollection
	private List<ProductDTO> userProducts = new ArrayList<>();
	public Order(User customer) {
		double bill = 0;
		List<Product> products = customer.getCart().getProducts();
		List<ProductDTO> cartProducts = customer.getCart().getCartProducts();
		for(ProductDTO p:cartProducts) {
			bill+=p.getSale_price()*p.getQuantity();
		}
		this.shipDate = LocalDate.now().plusDays(1);
		this.deliveryDate = LocalDate.now().plusDays(7);
		this.orderId = customer.getUsername()+"_"+RandomString.make(7)+"_"+LocalDateTime.now();
	    this.orderDate = LocalDateTime.now();
	    this.orderStatus = "Order placed";
	    this.billAmount = bill;
	    this.user = customer;
	    
	
	}

	
}
