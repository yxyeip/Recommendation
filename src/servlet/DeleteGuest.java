package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.GuestDao;

/**
 * Servlet implementation class DeleteGuest
 */
@WebServlet("/DeleteGuest")
public class DeleteGuest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteGuest() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=utf-8");
		HttpSession httpSession = request.getSession();
		if (httpSession.getAttribute("userName") == null) {
			response.getWriter().append("ÇëÖØÐÂµÇÂ½£¡");
		} else {
			String master = httpSession.getAttribute("userName").toString();
			String guest = request.getParameter("guestName");
			if(guest==null) {
				response.getWriter().append("É¾³ýÊ§°Ü£¬ÇëÉÔºóÖØÊÔ");
			}
			else if (GuestDao.deleteNewGuest(guest, master)) {
				response.getWriter().append("success");
			} else {
				response.getWriter().append("ºÃÓÑÒÑ±»É¾³ý£¬ÇëÎðÖØ¸´²Ù×÷");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
