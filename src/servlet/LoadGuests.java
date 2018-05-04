package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

import com.google.gson.Gson;

import Dao.GuestDao;
import entity.api.Customer;

/**
 * Servlet implementation class LoadGuests
 */
@WebServlet("/LoadGuests")
public class LoadGuests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadGuests() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8"); 
		HttpSession httpSession=request.getSession();
		String master=httpSession.getAttribute("userName")==null?"yxyeip":httpSession.getAttribute("userName").toString();
		List<Customer>guestList=GuestDao.getNewGuest(master);
		Gson gson=new Gson();
		response.getWriter().append(gson.toJson(guestList));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
