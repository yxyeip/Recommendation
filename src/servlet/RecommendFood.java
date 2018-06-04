package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import Dao.CustomerDao;
import Dao.FoodDao;
import Dao.GuestDao;
import entity.api.Customer;
import entity.api.Food;
import entity.impl.FoodImpl;
import recommend.GeneratedMenu;

/**
 * Servlet implementation class RecommendFood
 */
@WebServlet("/RecommendFood")
public class RecommendFood extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommendFood() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		 response.setContentType("application/json;charset=utf-8");
		 HttpSession httpSession=request.getSession();
	        Gson gson=new Gson();
	        if(httpSession.getAttribute("userName")==null) {
	            //response.getWriter().append(gson.toJson(allfood));
	        }else {
	        	String master=httpSession.getAttribute("userName").toString();
	        	List<Food>foods=new ArrayList<Food>();
	        	List<Customer>cusomers=GuestDao.getGuestsOfMaster(master);
	        	cusomers.add(CustomerDao.getCustomer(master));
	        	GeneratedMenu generatedMenu=new GeneratedMenu(cusomers);
	        	
	        	List<Integer> foodIdList=generatedMenu.GeneratedMenu();
	        	foods=FoodDao.getFoodList(foodIdList);
	        	String string=gson.toJson(foods);
	        	response.getWriter().append(string);
	        }
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
