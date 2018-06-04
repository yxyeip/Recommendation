package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Dao.CustomerDao;
import Dao.OrderDao;
import entity.api.Customer;
import entity.api.Order;
import entity.api.OrderItem;

import entity.impl.OrderImpl;
import entity.impl.OrderItemImpl;
import util.CookieUtil;


/**
 * Servlet implementation class PlaceOrder
 */
@WebServlet("/PlaceOrder")
public class PlaceOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PlaceOrder() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		//Cookie cookie=CookieUtil.getCookieByName(request, "car");
		String cookie=request.getParameter("car");
		String remark=request.getParameter("remark");
		Gson gson=new Gson();
		List<OrderItemTemp> OrderList = gson.fromJson(cookie, new TypeToken<List<OrderItemTemp>>() {}.getType());;
		//new a order
		HttpSession session=request.getSession();
		if(session.getAttribute("userName")==null) {
			response.getWriter().append("请重新登录！");
		}
		String userName=session.getAttribute("userName").toString();
		Customer customer=CustomerDao.getCustomer(userName);
		Order order=new OrderImpl(customer.getId(), remark, null, null);
		//order set
		Set<OrderItem>orderSet=new HashSet<OrderItem>();
		for (OrderItemTemp orderItemTemp : OrderList) {
			orderSet.add(orderItemTemp.OrderItem(order.getId()));
		}
		order.setOrderItems(orderSet);
		((OrderImpl)order).price();
		//save order to session
		session.setAttribute("order", order);
		//save order to DB
		OrderDao orderDao=new OrderDao(order);
		if(orderDao.addOrder()) {
			response.getWriter().append(gson.toJson(order));
		}else {
			response.getWriter().append("failed");
		}
				
//		List<OrderItemTemp> orderItemImpl =gson.fromJson(cookie.getValue(), new TypeToken<List<OrderItemTemp>>() {}.getType());
//		List<Object> list=Gson.
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	// public static void jsonToList(String json) {
	// Gson gson = new Gson();
	// List<OrderItemTemp> persons = gson.fromJson(json,
	// (List<OrderItemTemp>).getType());//对于不是类的情况，用这个参数给出
	// for (OrderItemTemp person : persons) {
	// System.out.println(person);
	// }
	// }
}

class OrderItemTemp {
	public int id;
	public String name;
	public int number;
	public BigDecimal t_price;

	public OrderItemImpl OrderItem(String orderId) {
		return new OrderItemImpl(orderId, id, number);
	}
}
