package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Dao.CustomerDao;
import entity.api.Customer;

/**
 * Servlet implementation class FindUserNames
 */
@WebServlet("/FindUserNames")
public class FindUserNames extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindUserNames() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
    	request.setCharacterEncoding("utf-8");
    	response.setContentType("application/json;charset=utf-8"); 
    	String username=request.getParameter("userName");
    	List<String>usernames=CustomerDao.getCustomerNameAcorrdingInput(username);
    	if(usernames.size()>6) {
    		usernames=usernames.subList(0, 6);
    	}
    	Gson gson=new Gson();
    	response.getWriter().append(gson.toJson(usernames));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
