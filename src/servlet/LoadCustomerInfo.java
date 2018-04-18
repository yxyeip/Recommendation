package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Dao.CustomerDao;
import entity.api.Customer;
import entity.impl.CustomerImpl;

/**
 * Servlet implementation class LoadCustomerInfo
 */
@WebServlet("/LoadCustomerInfo")
public class LoadCustomerInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoadCustomerInfo() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		String username=getUserName(request);
		if(username==null) {
			response.sendRedirect(request.getContextPath() + "/logon.html");
		}else {
			Customer customer=new CustomerImpl();
			customer=CustomerDao.getCustomer(username);
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			//String string=gson.toJson(customer).toString();
			response.getWriter().append(gson.toJson(customer));
		}		
	}

	private String getUserName(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		String state = httpSession.getAttribute("state") == null ? "" : httpSession.getAttribute("state").toString();
		if (httpSession.isNew() || (!(state.equals("log in")) && !(state.equals("log on")))) {
			return null;
		}

		return httpSession.getAttribute("userName")==null?null:httpSession.getAttribute("userName").toString();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
