package com.buy_EZ.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buy_EZ.models.ProductDTO;

public interface ProductDtoRepo extends JpaRepository<ProductDTO, String>{

}
