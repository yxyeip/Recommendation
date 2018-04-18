package entity.impl;

import java.math.BigDecimal;

public class CustomerFoodRate {
	//private FoodImpl foodImpl;
	private String name;
	private BigDecimal price;
	private String description;
	private int rate;
	public CustomerFoodRate(String foodName, BigDecimal price,String description,int rate) {
		this.name=foodName;
		this.price=price;
		this.description=description;
		this.rate=rate;
	}
}
