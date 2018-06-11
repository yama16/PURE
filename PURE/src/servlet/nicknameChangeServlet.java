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
import model.UpdateAccountLogic;

@WebServlet("/nicknameChangeServlet")
public class nicknameChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nickname = request.getParameter("newNickname");
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("account");
		account.setNickname(nickname);
		long now = System.currentTimeMillis();
		account.setUpdatedAt(new Timestamp(now));

		UpdateAccountLogic update = new UpdateAccountLogic();
		update.execute(account.getId(), account);

		session.setAttribute("account",account);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/accountHome.jsp");
		dispatcher.forward(request, response);
	}

}
