package com.buy_EZ.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.utility.RandomString;

@Data
@Entity
@NoArgsConstructor
public class Cart {
    @Id
	private String cartId;
	@OneToOne(cascade = CascadeType.ALL)@JsonIgnore
    private User customer;
	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Product.class)@JsonIgnore
	private List<Product> products = new ArrayList<>();
    @OneToMany(targetEntity = ProductDTO.class, cascade = CascadeType.ALL)
	private List<ProductDTO> cartProducts = new ArrayList<>();
	
	
	public Cart(User customer) {
		this.cartId = customer.getUsername()+"_"+RandomString.make(7)+"_"+LocalDateTime.now().getYear();
		this.customer = customer;
	}
	
}
