package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.OrderDao;
import entity.api.Order;

/**
 * Servlet implementation class PayAction
 */
@WebServlet("/PayAction")
public class PayAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayAction() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//response.sendRedirect(request.getContextPath()+"/login.html");
		//Order order=new order;
		response.setContentType("text/html;charset=utf-8");
		HttpSession session=request.getSession();
		Order order=(Order) session.getAttribute("order");
		if(order==null) {
			response.getWriter().append("0");
		}
		if(!order.getId().equals(request.getParameter("orderId"))) {
			response.getWriter().append("1");
		}
		OrderDao orderDao=new OrderDao( order);
		if(!orderDao.setOrderToComplete()) {
			response.getWriter().append("2");
		}else {
			response.getWriter().append("3");
		}
		//
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
