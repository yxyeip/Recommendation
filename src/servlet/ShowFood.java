package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

import com.google.gson.Gson;

import Dao.CustomerFoodRankDao;
import Dao.DBconn;
import Dao.FoodDao;
import entity.impl.CustomerFoodRank;
import entity.impl.FoodImpl;

/**
 * Servlet implementation class ShowFood
 */
@WebServlet(urlPatterns={"/ShowFood"})
public class ShowFood extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowFood() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");  
        String kind = new String(request.getParameter("kind").getBytes("iso8859-1"),"utf-8"); 
        response.setContentType("application/json;charset=utf-8"); 
        HttpSession httpSession=request.getSession();
        Gson gson=new Gson();
        if(httpSession.getAttribute("userName")==null) {
        	List<FoodImpl> allfood=FoodDao.getFoodListByKind(kind);
            response.getWriter().append(gson.toJson(allfood));
        }else {
        	List<CustomerFoodRank> customerFoodRates=CustomerFoodRankDao.selectCustomerFoodRank(httpSession.getAttribute("userName").toString(), kind);
        	response.getWriter().append(gson.toJson(customerFoodRates));
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

