package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Dao.CustomerDao;
import Dao.OrderDao;
import entity.api.Customer;
import entity.api.Order;

/**
 * Servlet implementation class ShowOrder
 */
@WebServlet("/ShowOrder")
public class ShowOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowOrder() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		String orderKind=new String(request.getParameter("orderKind").getBytes("iso8859-1"),"utf-8");
		HttpSession session=request.getSession();
		if(session.getAttribute("userName")==null) {
			response.getWriter().append("ÇëÖØÐÂµÇÂ¼£¡");
		}
		String userName=session.getAttribute("userName").toString();
		Customer customer=CustomerDao.getCustomer(userName);
		List<Order> orderList=OrderDao.getOrderAccordingKind(orderKind,customer.getId());
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();  
		response.getWriter().append(gson.toJson(orderList));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}

}
