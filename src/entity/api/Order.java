package entity.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.junit.validator.PublicClassValidator;

import entity.impl.OrderCustomerImpl;
import entity.impl.OrderItemImpl;

public interface Order {
	// id,guest,time,state,rate
	public void setId(String Id);

	public String getId();

	public void setUserId(long userId);

	public long getUserId();

	public void setTime(Date date);

	public Date getTime();

	public void setState(Boolean state);

	public boolean getState();
	
	public void setRemark(String remark);
	
	public String getRemark();
	
	public void setRate(int rate);
	
	public int getRate();
	
	public void setOrderItems(Set<OrderItem> orderItems);
	
	public Set<OrderItem>  getOrderItems() ;
	
	public void setOrderCustomer(Set<OrderCustomer> orderCustomers);
	
	public Set<OrderCustomer> getOrderCustomers();
	
	public BigDecimal getPrice();
	
	public void setPrice(BigDecimal price);
}
