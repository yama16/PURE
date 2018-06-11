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
import model.Login;
import model.LoginLogic;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");

		//Loginインスタンス（入力情報）の生成
		Login login = new Login(id,pass);

		//登録されているアカウント情報を取り出す
		LoginLogic loginLogic = new LoginLogic();
		Account account = loginLogic.execute(login);

		//アカウントが見つかった時
		if(account != null) {
			//セッションスコープにアカウント情報を保存
			HttpSession session = request.getSession();
			session.setAttribute("account", account);

			//個人設定画面にリダイレクト
			response.sendRedirect("/PURE/AccountHomeServlet");

		//アカウントが見つからない時
		}else{
			//入力情報を保持してloginページに再度アクセス
			request.setAttribute("login", login);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
		}
	}
}
