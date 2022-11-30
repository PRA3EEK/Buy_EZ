package com.buy_EZ.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buy_EZ.models.SubCategory;

public interface SubCategoryRepo extends JpaRepository<SubCategory, String>{

	public SubCategory findByName(String name);
	
}
