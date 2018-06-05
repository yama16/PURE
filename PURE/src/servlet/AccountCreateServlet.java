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

/**
 * Servlet implementation class AccountCreateServlet
 */
@WebServlet("/AccountCreateServlet")
public class AccountCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/account_create.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		String nicname = request.getParameter("nicname");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");

		//現在時刻の取得
		Timestamp now = new Timestamp(System.currentTimeMillis());

		//アカウント情報の作成
		Account account = new Account(id,nicname,pass,now,now);

		//セッションスコープにアカウント情報を保存
		HttpSession session = request.getSession();
		session.setAttribute("createAccount", account);

		//確認画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/confirm.jsp");
		dispatcher.forward(request, response);

	}

}
