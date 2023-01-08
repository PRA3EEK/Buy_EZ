package com.buy_EZ.DTO;

import java.util.List;

import com.buy_EZ.models.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
  
	private Integer numberOfItems;
	private Double totalAmount;
	private Double payAmount;
	private Double save;
	private List<ProductDTO> cartProducts;
}
