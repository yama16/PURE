package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.Login;
import model.LoginLogic;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/rogin.jsp");
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
		Account account;
		LoginLogic loginLogic = new LoginLogic();
		account = loginLogic.execute(login);

		//アカウントが見つかった時
		if(account != null) {
			System.out.println("OK");

		//アカウントが見つからない時
		}else{
			System.out.println("NO");
		}
	}

}
