package com.buy_EZ.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.buy_EZ.repositories.ProductRepo;
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
	@Embedded
	private Address deliveryAddress;
	@ManyToOne
	private User user;
	@ManyToMany
	private List<Product> products = new ArrayList<>();
	@ManyToOne
	private Shipper shipper;
	@ManyToOne
	private Payment payment;
	@ElementCollection
	private List<ProductDTO> userProducts = new ArrayList<>();
	public Order(User customer, Address address) {
		double bill = 0;
		List<Product> products = customer.getCart().getProducts();
		List<ProductDTO> cartProducts = customer.getCart().getCartProducts();
		int ind = 0;
		for(ProductDTO p:cartProducts) {
			bill+=(products.get(ind).getSale_price())*p.getQuantity();
			ind++;
		}
		this.shipDate = LocalDate.now().plusDays(1);
		this.deliveryDate = LocalDate.now().plusDays(7);
		this.orderId = customer.getUsername()+"_"+RandomString.make(7)+"_"+LocalDateTime.now();
	    this.orderDate = LocalDateTime.now();
	    this.orderStatus = "Order placed";
	    this.billAmount = bill;
	    this.user = customer;
	    this.deliveryAddress = address;
	    
	
	}

	
}
