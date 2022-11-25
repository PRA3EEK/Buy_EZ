package com.buy_EZ.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buy_EZ.models.Category;

public interface CategoryRepo extends JpaRepository<Category, String>{

	public Category findByCategoryName(String name);
	
}
