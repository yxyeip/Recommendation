package servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class CheckUserState
 */
@WebServlet("/CheckUserState")
public class CheckUserState extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckUserState() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        HttpSession session=request.getSession();
        Gson gson = new Gson(); 
        if(session.isNew()||session.getAttribute("state")==null) {
        	UserState userState=new UserState("","not login");
        	response.getWriter().append(gson.toJson(userState));
        }
        else if(session.getAttribute("state").toString().equals("log in")) {//ÒÑ¾­µÇÂ¼
    	    UserState userState=new UserState(session.getAttribute("userName").toString(),"log in");
       		response.getWriter().append(gson.toJson(userState).toString());
        } else{//Î´µÇÂ¼
        	UserState userState=new UserState("","not login");
        	response.getWriter().append(gson.toJson(userState));
        } 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

class UserState{
	private String username;
	private String state;
	UserState(String username,String state){
		this.username=username;
		this.state=state;
	}
}


