package com.buy_EZ.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.buy_EZ.models.Supplier;

public interface SupplierRepo extends JpaRepository<Supplier, Long>{

	public List<Supplier> findByCountry(String countryName);
	
	
}
