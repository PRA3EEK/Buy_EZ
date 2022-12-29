package com.buy_EZ.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buy_EZ.models.CustomerCurrentSession;

public interface CustomerCurrentSessionRepo extends JpaRepository<CustomerCurrentSession, String>{

	CustomerCurrentSession findByUsername(String username);
	
}
