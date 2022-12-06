package com.buy_EZ.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity@Data@AllArgsConstructor@NoArgsConstructor
public class OrderDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderDetailId;
	@OneToOne
	private Order order;
	@OneToOne
	private Product product;
	private Integer quantity;
    @OneToOne
	private Supplier supplier;
	
	
}
