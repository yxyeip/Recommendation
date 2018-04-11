package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import Dao.CustomerDao;
import entity.api.*;
import entity.impl.CustomerImpl;
import util.*;

/**
 * Servlet implementation class LoginServlet
 */

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        
        String userName = request.getParameter("name");
        String password = request.getParameter("password");

        System.out.println(userName+"--"+password);

        if(StringUtil.isEmpty(password)||StringUtil.isEmpty(userName)){   	
            //session
            HttpSession session=request.getSession();
            session.setAttribute("error", "不能为空");          
            response.sendRedirect(request.getContextPath()+"/login.html");
            response.getWriter().print("用户名密码不能为空！");
            System.out.println("kong");
            return ;
        }

        try {
      //1成功0失败2用户不存在
        	int result=CustomerDao.login(userName, password);
            if(result==0){
                HttpSession session=request.getSession();
                session.setAttribute("state", "password error");
                session.setAttribute("userName", userName);
                //response.sendRedirect(request.getContextPath()+"/login.jsp");
                response.getWriter().print("密码错误！");
                System.out.println("password error");
            }else if (result==1){
                // 
                HttpSession session=request.getSession();
                session.setAttribute("state", "log in");
                session.setAttribute("userName", userName);
                response.sendRedirect(request.getContextPath()+"/main.html");
                System.out.println("success");
            }else {
            	HttpSession session=request.getSession();
                session.setAttribute("state ", "user not exist");
                session.setAttribute("userName", userName);
                response.sendRedirect(request.getContextPath()+"/logon.html");
                response.getWriter().print("用户名不存在，请注册！");
                System.out.println("user not exist");
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

}
