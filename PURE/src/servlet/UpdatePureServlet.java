package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.TogglePureLogic;
import model.Account;
import model.BulletinBoard;

/**
 * Servlet implementation class UpdatePureServlet
 */
@WebServlet("/UpdatePureServlet")
public class UpdatePureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータを取得
		int commentId = 0;
		try {
			commentId = Integer.parseInt(request.getParameter("commentId"));
		} catch(NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		// セッションスコープからアカウントと掲示板を取得
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("account");
		BulletinBoard bulletinBoard = (BulletinBoard) session.getAttribute("bulletinBoard");

		// PURE処理
		TogglePureLogic togglePureLogic = new TogglePureLogic();
		int result = togglePureLogic.execute(account.getId(), commentId, bulletinBoard.getId());

		// レスポンス処理
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(result);
		pw.close();
	}

}
