package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.CustomerDao;
import entity.api.Customer;
import entity.impl.CustomerImpl;
import util.PasswordUtil;

/**
 * Servlet implementation class LogonServlet
 */
@WebServlet("/LogonServlet")
public class LogonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogonServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("utf-8");
		String name=request.getParameter("name");
		Boolean isMale=request.getParameter("sex").equals("male")?true:false;
		String password=PasswordUtil.encript(request.getParameter("password"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		try {
			date=sdf.parse(request.getParameter("birthday"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date birthday=date;
		int height=Integer.parseInt(request.getParameter("height"));
		int weight=Integer.parseInt(request.getParameter("weight"));
		Customer customer=new CustomerImpl(name,password,isMale,birthday,height,weight);
		
		HttpSession httpSession=request.getSession();

		
		if(CustomerDao.logon(customer)) {
			httpSession.setAttribute("state", "log on");
			httpSession.setAttribute("userName", name);
			response.sendRedirect("./main.html");
		}else {
			httpSession.setAttribute("state", "log on failed");
			httpSession.setAttribute("userName", name);
			response.sendRedirect("./logon.html");
			response.getWriter().append("<script>alert('用户名已存在，请重试！')</script>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
