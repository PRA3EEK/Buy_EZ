package com.buy_EZ.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;


@Entity
@Data
@NoArgsConstructor
public class SubCategory {
 
	@Id
	private String subCategoryId;
	private String name;
	@ManyToOne@JsonIgnore
	private Category parentCategory;
	@OneToMany(mappedBy = "subCategory")@JsonIgnore
	private List<Product> Products = new ArrayList<>();
	
	
	
}
