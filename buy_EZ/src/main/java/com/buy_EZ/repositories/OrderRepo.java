package com.buy_EZ.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buy_EZ.models.Order;

public interface OrderRepo extends JpaRepository<Order, String>{

}
