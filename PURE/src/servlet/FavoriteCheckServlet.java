package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bo.GetFavoriteBulletinBoardLogic;
import model.BulletinBoard;

/**
 * Servlet implementation class FavoriteCheckServlet
 */
@WebServlet("/FavoriteCheckServlet")
public class FavoriteCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");

		List<BulletinBoard> favoriteList;
		GetFavoriteBulletinBoardLogic getBoard = new GetFavoriteBulletinBoardLogic();
		favoriteList = getBoard.execute(id);

		for(BulletinBoard b : favoriteList) {
			System.out.println(b.getTitle());
		}

		PrintWriter pw = response.getWriter();
		pw.print("{\"hoge\":123, \"ojoj\":\"OJOJ\"}");
		pw.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
