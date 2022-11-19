package com.buy_EZ.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import net.bytebuddy.utility.RandomString;

@Data
@Entity
public class Product {
   
	@Id
	private String productId;
	private String productName;
	private Double price;
	private String color;
	private String dimension;
	private String specification;
	private String manufacturer;
	private Integer quantity;
	@ManyToOne
	private Category category;
	
	
	public Product(String productName, Double price, String color, String dimension, String specification, String manufacturer, Integer quantity, Category category) {
		this.category = category;
		this.productId = productName+"_"+RandomString.make(7);
		this.productName = productName;
		this.price = price;
		this.color = color;
		this.dimension = dimension;
		this.manufacturer = manufacturer;
		this.quantity = quantity;
	}
	
	
	
}
