package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;

/**
 * Servlet implementation class AccountDeleteServlet
 */
@WebServlet("/AccountDeleteConfirmServlet")
public class AccountDeleteConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("account");

		if(account != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/accountDeleteConfirm.jsp");
			dispatcher.forward(request, response);
		}else{
			response.sendRedirect("/PURE/HomeServlet");
		}
	}

}
