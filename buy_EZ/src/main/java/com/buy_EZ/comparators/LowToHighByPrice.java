package com.buy_EZ.comparators;

import java.util.Comparator;

import com.buy_EZ.models.Product;

public class LowToHighByPrice implements Comparator<Product>{

	@Override
	public int compare(Product o1, Product o2) {
		// TODO Auto-generated method stub
		return (int)(o1.getPrice()-o2.getPrice());
	}

	
	
}
