package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.DBconn;

/**
 * Servlet implementation class ShowFood
 */
@WebServlet("/ShowFood")
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

		//response.getWriter().append("Served at: ").append(request.getContextPath());
        request.setCharacterEncoding("UTF-8");  
        String kind = request.getParameter("kind");
        response.setContentType("text/html; charset=utf-8");  
        PrintWriter pw = response.getWriter(); 
       // pw.print(generateKindHtml(kind));
	}
/*
	private String generateKindHtml(String kind) {
		String html;
		String sql=String.format("select * from food where kinds= '%s'", kind);
		DBconn.init();
		ResultSet r=DBconn.selectSql(sql);
		try {
			while(r.next()) {
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBconn.closeConn();
		
	}
	*/
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
