package entity.api;

public interface OrderCustomer {
	//order id, customer id
	public String getOrderId();
	public void setOrderId(String orderId);
	public long getCustomerId();
	public void setCustomerId(long customerId);
}
