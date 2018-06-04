package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entity.api.Food;
import entity.api.Order;
import entity.api.OrderCustomer;
import entity.api.OrderItem;
import entity.impl.FoodImpl;
import entity.impl.OrderImpl;
import entity.impl.OrderItemImpl;

public class OrderDao {
	private Order order;

	public OrderDao(Order order) {
		this.order = order;
	}

	/* add a new order, item and user to DB */
	public boolean addOrder() {
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sDate=simpleDateFormat.format(order.getTime());
		String sql = String.format(
				"INSERT INTO Recommand..f_order (id, time,state,remark,customer_id,price) VALUES ('%s', '%s','%b','%s',%d,%s)",
				order.getId(), sDate, order.getState(), order.getRemark(), order.getUserId(),order.getPrice().toString());
		Set<String> orderItemSqls = getOrderItemSqls(order.getOrderItems());
		Set<String> orderCustomerSqls = getOrderCustomerSqls(order.getOrderCustomers());

		DBconn.init();
		try {
			DBconn.conn.setAutoCommit(false); // 将自动提交设置为false
			Statement ps = DBconn.conn.createStatement();
			if (ps.executeUpdate(sql) != 1) // 执行插入order表
				return false;
			for (String itemSql : orderItemSqls) {
				if (ps.executeUpdate(itemSql) != 1)// 执行插入orderItem表
					return false;
			}
			if (orderCustomerSqls != null) {
				for (String customerSql : orderCustomerSqls) {
					if (ps.executeUpdate(customerSql) != 1)// 执行插入orderCustomer表
						return false;
				}
			}
			DBconn.conn.commit(); // 当两个操作成功后手动提交
		} catch (Exception e) {
			try {
				DBconn.conn.rollback();
			} catch (SQLException e1) {
				System.err.println("啊哈，回滚都不成功，什么鬼");
				e1.printStackTrace();
				return false;
			} // 一旦其中一个操作出错都将回滚，使两个操作都不成功
			e.printStackTrace();
			return false;
		} finally {
			DBconn.closeConn();
		}
		return true;
	}

	private Set<String> getOrderItemSqls(Set<OrderItem> orderItems) {
		Set<String> orderItemSqls = new HashSet<String>();
		for (OrderItem item : orderItems) {
			String sql = String.format(
					"insert into Recommand..orderitem(order_id,food_id,number) VALUES ('%s', %d,%d)",
					item.getOrderId(), item.getFoodId(), item.getFoodNumber());
			orderItemSqls.add(sql);
		}
		return orderItemSqls;
	}

	private Set<String> getOrderCustomerSqls(Set<OrderCustomer> orderCustomers) {
		Set<String> orderItemSqls = new HashSet<String>();
		if (orderCustomers == null)
			return null;
		for (OrderCustomer item : orderCustomers) {
			String sql = String.format("insert into Recommand..ordercustomer(order_id,customer_id) VALUES ('%s', %d)",
					item.getOrderId(), item.getCustomerId());
			orderItemSqls.add(sql);
		}
		return orderItemSqls;
	}

	/* delete order */
	public boolean deleteOrder() {
		String sql = String.format("DELETE FROM Recommand..f_order WHERE id='%s'", order.getId());
		DBconn.init();
		try {
			DBconn.conn.setAutoCommit(false); // 将自动提交设置为false
			Statement ps = DBconn.conn.createStatement();
			if (ps.executeUpdate(sql) != 1)
				return false;
			sql = String.format("DELETE FROM Recommand..orderitem where order_id='%s'", order.getId());
			if (ps.executeUpdate(sql) == 0)
				return false;
			sql = String.format("DELETE FROM Recommand..ordercustomer where order_id='%s'", order.getId());
			// if(ps.executeUpdate(sql)==0)
			// return false;
		} catch (Exception e) {
			try {
				DBconn.conn.rollback();
			} catch (SQLException e1) {
				System.err.println("啊哈，回滚都不成功，什么鬼");
				e1.printStackTrace();
				return false;
			} // 一旦其中一个操作出错都将回滚，使两个操作都不成功
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// change state
	public boolean setOrderToComplete() {
		DBconn.init();
		String sql = String.format("UPDATE Recommand..f_order SET state = 1 WHERE id = '%s'", order.getId());
		if (DBconn.addUpdDel(sql) != 1) {
			DBconn.closeConn();
			return false;
		}
		DBconn.closeConn();
		return true;
	}

	// change order
	public boolean rateOrder(int rate) {
		// if order is not complete
		DBconn.init();
		String sql = String.format("select * from Recommand..f_order where state=0 and id='%s'", order.getId());
		ResultSet rSet = DBconn.selectSql(sql);
		try {
			if (rSet.next()) {
				sql = String.format("UPDATE Recommand..f_order SET rate = %d WHERE id = '%s'", rate, order.getId());
				if (DBconn.addUpdDel(sql) != 1) {
					System.err.println("更新订单评分失败");
					return false;
				} else {
					return true;
				}
			} else {
				System.err.println("该订单未完成，不能评价");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.closeConn();
		}
		return true;
	}
	public static List<Order>getOrderAccordingKind(String orderKind,long customer_id) {
		List<Order> orderList=new ArrayList<Order>();
		String sql="";
		if(orderKind.equals("all")) {
			sql=String.format("select * from Recommand..f_order where customer_id='%d' order by time desc", customer_id);
		}else if(orderKind.equals("needPay")){
			sql=String.format("select * from Recommand..f_order where customer_id='%d' and state = 0 order by time desc", customer_id);
		}else {//needRate
			sql=String.format("select * from Recommand..f_order where customer_id='%d' and state = 1 and (rate is NULL or rate =0)order by time desc", customer_id);
		}
		DBconn.init();
		ResultSet rSet=DBconn.selectSql(sql);
		try {
			while(rSet.next()) {
				Set <OrderItem> orderItems=getFoodItemsByOrderId(rSet.getString("id"));
				Order order=new OrderImpl();
				order.setId(rSet.getString("id"));
				order.setOrderItems(orderItems);
				order.setState(rSet.getBoolean("state"));
				((OrderImpl)order).price();
				order.setRate(rSet.getInt("rate"));
				order.setRemark(rSet.getString("remark"));
				//Date time=rSet.getDate("time");
				Date time=rSet.getTimestamp("time");
				order.setTime(time);
				orderList.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBconn.closeConn();
		}
		return orderList;		
	}
	
	public static Set <OrderItem> getFoodItemsByOrderId(String orderId) {
		Set <OrderItem> orderItems=new HashSet<OrderItem>();
		String sql=String.format("select * from Recommand..orderitem join Recommand..food on food_id = id where order_id='%s'", orderId);
		DBconn.init();
		ResultSet rSet=DBconn.selectSql(sql);
		try {
			while(rSet.next()) {
				OrderItem orderItem=new OrderItemImpl(orderId, rSet.getInt("food_id"), rSet.getInt("number"));
				Food food=new FoodImpl();
				food.setDescription(rSet.getString("description"));
				food.setId(rSet.getInt("id"));
				food.setName(rSet.getString("name"));
				food.setPrice(rSet.getBigDecimal("price"));
				((OrderItemImpl)orderItem).setFood(food);
				orderItems.add(orderItem);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBconn.closeConn();
		}
		return orderItems;
	}
}
