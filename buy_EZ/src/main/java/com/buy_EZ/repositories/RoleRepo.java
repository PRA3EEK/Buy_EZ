package com.buy_EZ.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buy_EZ.models.ERole;
import com.buy_EZ.models.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

	Optional<Role> findByName(ERole name);
	
}
