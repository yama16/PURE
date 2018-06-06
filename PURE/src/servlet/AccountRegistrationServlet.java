package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.CreateAccountLogic;

@WebServlet("/AccountRegistrationServlet")
public class AccountRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("createAccount");

		response.sendRedirect("/PURE/AccountCreateServlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Account account =(Account) session.getAttribute("createAccount");

		CreateAccountLogic createLogic = new CreateAccountLogic();
		boolean RegistrationCheck = createLogic.execute(account);
		if(RegistrationCheck) {
			session.removeAttribute("createAccount");
			response.sendRedirect("/PURE/AccountHomeServlet");
		}else{
			session.removeAttribute("createAccount");
			response.sendRedirect("/PURE/AccountCreateServlet");
		}
	}

}
