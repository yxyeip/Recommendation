package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.CustomerFoodRankDao;

/**
 * Servlet implementation class UploadRate
 */
@WebServlet("/UploadRank")
public class UploadRank extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadRank() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		int foodId=Integer.valueOf(request.getParameter("id"));
		String score=request.getParameter("score");
		int rank=Integer.valueOf(score);	
		HttpSession httpSession=request.getSession();
		if(httpSession.getAttribute("userName")==null)
		{
			response.getWriter().append("not log in");
		}else {
			String userName=httpSession.getAttribute("userName").toString();
			CustomerFoodRankDao evaluationDao=new CustomerFoodRankDao(userName,foodId,rank);
			if(evaluationDao.insertOrUpdate()==false)
				response.getWriter().append("insert failed");
			else
				response.getWriter().append("success");
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);	
	}

}
