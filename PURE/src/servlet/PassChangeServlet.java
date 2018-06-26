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

import bo.Login;
import bo.LoginLogic;
import bo.UpdateAccountLogic;
import model.Account;

@WebServlet("/PassChangeServlet")
public class PassChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pass = request.getParameter("pass");
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("account");
		System.out.println(pass);
		account.setPassword(pass);
		long now = System.currentTimeMillis();
		account.setUpdatedAt(new Timestamp(now));

		UpdateAccountLogic update = new UpdateAccountLogic();
		update.execute(account.getId(), account);

		Login login = new Login(account.getId(),account.getPassword());
		LoginLogic logic = new LoginLogic();
		account = logic.execute(login);

		session.setAttribute("account",account);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/accountHome.jsp");
		dispatcher.forward(request, response);
	}

}
