package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		int id = Integer.parseInt(request.getParameter("id"));
		GetBulletinBoardLogic getBulletinBoard = new GetBulletinBoardLogic();
		BulletinBoard bulletinBoard = getBulletinBoard.execute(id);
		if(bulletinBoard != null) {
			request.setAttribute("bulletinBoard", bulletinBoard);
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
