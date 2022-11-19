package com.buy_EZ.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import net.bytebuddy.utility.RandomString;

@Data
@Entity
public class Cart {
    @Id
	private String cartId;
	@OneToOne
    private User customer;
	@OneToMany()
	private List<Product> products = new ArrayList<>();
	
	public Cart(User customer) {
		this.cartId = customer.getUsername()+"_"+RandomString.make(7)+"_"+LocalDateTime.now().getYear();
		this.customer = customer;
	}
	
}
