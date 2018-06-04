package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import Dao.CustomerDao;
import Dao.GuestDao;
import entity.api.Customer;

/**
 * Servlet implementation class AddGuests
 */
@WebServlet("/AddGuests")
public class AddGuests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddGuests() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String guestName=request.getParameter("guestName");
		HttpSession httpSession=request.getSession();
		if(httpSession.getAttribute("userName")==null)
		{
			response.getWriter().append("not log in");
		}else {
			String master=httpSession.getAttribute("userName").toString().trim();
			//1 success
			//2 already exist
			//3 user not exist
			//4 sql failed
			int result=GuestDao.addNewGuest(guestName, master);
			if(result==1) {
				Customer customer=CustomerDao.getCustomer(guestName);
				Gson gson=new Gson();
				response.getWriter().append(gson.toJson(customer));			
			}
			else if(result==2)
				response.getWriter().append("you already add this guest!");
			else if(result==3)
				response.getWriter().append("user not exit!");
			else {
				response.getWriter().append("system error!");
			}
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
