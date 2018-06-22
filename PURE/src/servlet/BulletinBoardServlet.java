package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BulletinBoard;
import model.GetBulletinBoardLogic;

/**
 * Servlet implementation class BulletinBoardServlet
 * @author furukawa
 */
@WebServlet("/BulletinBoardServlet")
public class BulletinBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストスコープに保存を取得
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println(id);



		// GetBulletinBoardLogicから掲示板オブジェクトを取得
		GetBulletinBoardLogic getBulletinBoard = new GetBulletinBoardLogic();
		BulletinBoard bulletinBoard = getBulletinBoard.execute(id);

		// nullではなかったらセッションスコープに掲示板オブジェクトを保存
		if(bulletinBoard != null) {
			HttpSession session = request.getSession();
			session.setAttribute("bulletinBoard", bulletinBoard);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/createBulletinBoard.jsp");
			dispatcher.forward(request, response);
		} else {
			// エラーメッセージを表示
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
