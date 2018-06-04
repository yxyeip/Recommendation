package entity.impl;

import Dao.FoodDao;
import entity.api.Food;
import entity.api.OrderItem;

public class OrderItemImpl implements OrderItem {
	private String orderId;
	private int foodId;
	private int foodNumber;
	
	private Food food;
	
	public OrderItemImpl(String orderId,int foodId,int foodNumber){
		food=FoodDao.getFoodById(foodId);
		this.orderId=orderId;
		this.foodId=foodId;
		this.foodNumber=foodNumber;
	}
	
	public Food getFood() {
		return food;
	}
	
	public void setFood(Food f) {
		this.food=f;
	}
	
	@Override
	public String getOrderId() {		
		return orderId;
	}

	@Override
	public int getFoodId() {
		return foodId;
	}

	@Override
	public int getFoodNumber() {
		return foodNumber;
	}



	@Override
	public void setOrderId(String orderId) {
		this.orderId=orderId;
	}

	@Override
	public void setFoodId(int foodId) {
		this.foodId=foodId;
	}

	@Override
	public void setFoodNumber(int foodNumber) {
		this.foodNumber=foodNumber;
	}

}
