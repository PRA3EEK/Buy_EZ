package com.buy_EZ.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productDtoTable")
public class ProductDTO {

	@Id
	private String productDtoId;
	private String productName;
	private Double price;
	private String color;
	private String dimension;
	private String specification;
	private String manufacturer;
	private Integer quantity;
	private Double ratings;
	private Integer numberOfRatings;
	private String categoryName;
	private String subCategoryName;
	private String imageUrl;
	
	
}
