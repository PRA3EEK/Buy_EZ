package com.buy_EZ.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buy_EZ.models.OrderDetails;

public interface OrderDetailRepo extends JpaRepository<OrderDetails, Long>{

}
