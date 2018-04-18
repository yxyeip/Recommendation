package entity.impl;

import java.math.BigDecimal;
import entity.api.Food;

public class FoodImpl implements Food {
	private int Id;
	private String name;
	private BigDecimal price;
	private String description;
	private String kind;
	public FoodImpl() {
	}
	public FoodImpl(String kind,String name,BigDecimal price,String description) {
		this.kind=kind;
		this.name=name;
		this.price=price;
		this.description=description;
	}
	@Override
	public int getId() {		
		return Id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	public String  getKind() {
		return kind;
	}
}
