package com.buy_EZ.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import net.bytebuddy.utility.RandomString;

@Data
@Entity
public class Category {
    @Id
	private String catagoryId;
	private String catagoryName;
	
	@OneToMany(mappedBy = "category")
	private List<Product> products = new ArrayList<>();
	
	public Category(String catagoryName) {
		this.catagoryName = catagoryName;
		this.catagoryId = catagoryName+"_"+RandomString.make(7);
	}
	
}
