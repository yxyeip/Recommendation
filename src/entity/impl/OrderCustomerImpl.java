package entity.impl;

import entity.api.OrderCustomer;

public class OrderCustomerImpl implements OrderCustomer{
	private String orderId;
	private long customerId;
	
	@Override
	public String getOrderId() {
		return orderId;
	}

	@Override
	public void setOrderId(String orderId) {
		this.orderId=orderId;
	}

	@Override
	public long getCustomerId() {
		return customerId;
	}

	@Override
	public void setCustomerId(long customerId) {
		this.customerId=customerId;
	}

}
