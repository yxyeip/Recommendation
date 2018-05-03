package entity.impl;
/**
 * @author Ye xiuyun
 *
 */
import java.math.BigDecimal;

public class CustomerFoodRank {
	//private FoodImpl foodImpl;
	private String name;
	private BigDecimal price;
	private String description;
	private int rank;
	private int id;
	public CustomerFoodRank(String foodName, BigDecimal price,String description,int rank,int id) {
		this.name=foodName;
		this.price=price;
		this.description=description;
		this.rank=rank;
		this.id=id;
	}
}
