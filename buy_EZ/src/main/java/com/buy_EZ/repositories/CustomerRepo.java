package com.buy_EZ.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buy_EZ.models.User;

public interface CustomerRepo extends JpaRepository<User, String>{
	public User findByUsername(String username);
}
