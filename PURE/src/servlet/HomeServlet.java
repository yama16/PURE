package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bo.GetNewBulletinBoardLogic;
import bo.GetRankingLogic;
import model.BulletinBoardList;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GetNewBulletinBoardLogic  getNewBulletinBoardLogic = new GetNewBulletinBoardLogic();
		BulletinBoardList newBulletinBoardList = getNewBulletinBoardLogic.execute();

		GetRankingLogic getRankingLogic = new GetRankingLogic();
		BulletinBoardList rankingList = getRankingLogic.execute(1);

		request.setAttribute("newList", newBulletinBoardList);
		request.setAttribute("rankingList", rankingList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
		dispatcher.forward(request, response);
	}

}
