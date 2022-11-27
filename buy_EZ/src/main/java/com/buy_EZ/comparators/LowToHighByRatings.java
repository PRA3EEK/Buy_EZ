package com.buy_EZ.comparators;

import java.util.Comparator;

import com.buy_EZ.models.Product;

public class LowToHighByRatings implements Comparator<Product>{

	@Override
	public int compare(Product o1, Product o2) {
		// TODO Auto-generated method stub
		if(o1.getRatings()>o2.getRatings()) {
			return 1;
		}else if(o1.getRatings()<o2.getRatings()) {
			return -1;
		}
		return 0;
	}

	
	
}
