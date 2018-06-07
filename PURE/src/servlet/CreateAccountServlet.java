package servlet;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.CreateAccountLogic;

/**
 * Servlet implementation class AccountCreateServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/createAccount.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Account account =(Account) session.getAttribute("createAccount");

		long now = System.currentTimeMillis();
		account.setCreated_at(new Timestamp(now));
		account.setUpdated_at(new Timestamp(now));
		CreateAccountLogic createAccountLogic = new CreateAccountLogic();
		boolean isCreated = createAccountLogic.execute(account);
		if(isCreated) {
			session.removeAttribute("createAccount");
			session.setAttribute("account", account);
			response.sendRedirect("/PURE/AccountHomeServlet");
		}else{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/createAccount.jsp");
			dispatcher.forward(request, response);
		}
	}

}
