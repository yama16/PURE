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

@WebServlet("/ConfirmCreateAccountServlet")
public class ConfirmCreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/PURE/CreateAccountServlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		String nickname = request.getParameter("nickname");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");

		//アカウント情報の作成
		Account account = new Account();
		account.setNickname(nickname);
		account.setId(id);
		account.setPassword(pass);

		//セッションスコープにアカウント情報を保存
		HttpSession session = request.getSession();
		session.setAttribute("createAccount", account);

		if(!nickname.matches(".{1,10}") || !id.matches("[a-zA-Z0-9]{6,12}") || !pass.matches("[a-zA-Z0-9]{8,16}")){
			response.sendRedirect("/PURE/CreateAccountServlet");
		}else{

			//確認画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/confirmCreateAccount.jsp");
			dispatcher.forward(request, response);
		}
	}

}
