package com.buy_EZ.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buy_EZ.models.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Long>{

	
	public Payment findByType(String typeName);
}
