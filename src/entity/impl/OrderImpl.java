package entity.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import entity.api.Order;
import entity.api.OrderCustomer;
import entity.api.OrderItem;
import util.IDUtil;

public class OrderImpl implements Order {
	private String id;
	private long userId;
	private Date time;
	private Boolean state;
	private String remark;
	private int rate;
	private BigDecimal price;
	
	private Set<OrderItem> orderItems = new HashSet<OrderItem>();
	private Set<OrderCustomer> customers= new HashSet<OrderCustomer>();
	public OrderImpl(long userID,String remark,Set<OrderItem> orderItems,Set<OrderCustomer> customers) {
		this.userId=userID;
		this.remark=remark;
		this.time=new Date();
		this.state=false;
		//Éú³ÉID
		this.id=IDUtil.generateMenuID(userID);
		this.rate=0;
		
		this.orderItems=orderItems;
		this.customers=customers;
		
		//price
		price();
		
	}
	
	public OrderImpl() {
	}

	public void price() {
		BigDecimal t_price=BigDecimal.valueOf(0.0);
		if(orderItems==null) return;
		Iterator<OrderItem> it = orderItems.iterator();
		while (it.hasNext()) {
			OrderItem orderItem =it.next();
			BigDecimal aBigDecimal=((OrderItemImpl)orderItem).getFood().getPrice();
			BigDecimal decimal=aBigDecimal.multiply(BigDecimal.valueOf(orderItem.getFoodNumber()));
			t_price=t_price.add(decimal);  
		}
		price= t_price;
	}
	@Override
	public BigDecimal getPrice() {
		return price;
	}
	@Override
	public void setId(String Id) {
		this.id=Id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setTime(Date date) {
		this.time=date;
	}

	@Override
	public Date getTime() {
		return time;
	}

	@Override
	public void setState(Boolean state) {
		this.state=state;
	}

	@Override
	public boolean getState() {
		return state;
	}
	@Override
	public void setRemark(String remark) {
		this.remark=remark;
	}
	@Override
	public String getRemark() {
		return remark;
	}
	
	@Override
	public void setUserId(long userId) {
		this.userId=userId;
	}
	@Override
	public long getUserId() {
		return userId;
	}
	@Override
	public void setRate(int rate) {
		this.rate=rate;
	}
	@Override
	public int getRate() {
		return rate;
	}

	@Override
	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems=orderItems;
	}

	@Override
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	@Override
	public void setOrderCustomer(Set<OrderCustomer> orderCustomers) {
		this.customers=orderCustomers;
	}

	@Override
	public Set<OrderCustomer> getOrderCustomers() {
		return customers;
	}

	@Override
	public void setPrice(BigDecimal price) {
		this.price=price;
	}
}
