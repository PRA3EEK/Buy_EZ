package com.buy_EZ.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buy_EZ.models.AdminCurrentSession;

public interface AdminCurrentSessionRepo extends JpaRepository<AdminCurrentSession, String>{

	public AdminCurrentSession findByUsername(String username);
	
}
