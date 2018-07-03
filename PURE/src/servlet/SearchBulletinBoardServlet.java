package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bo.SearchTagLogic;
import bo.SearchTitleLogic;
import model.BulletinBoardList;

@WebServlet("/SearchBulletinBoardServlet")
public class SearchBulletinBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("search");
		String target = request.getParameter("searchSelect");
		System.out.println(keyword);
		System.out.println(target);

		if(target != null && keyword != null) {
			BulletinBoardList bulletinBoards = null;

			if(target != null && (target.equals("1") || target.equals("2"))) {
				if(target.equals("1")) {
					SearchTagLogic searchTagLogic = new SearchTagLogic();
					bulletinBoards = searchTagLogic.execute(keyword, true);

				}else if(target.equals("2")) {
					SearchTitleLogic searchTitleLogic = new SearchTitleLogic();
					bulletinBoards = searchTitleLogic.execute(keyword);
				}

				request.setAttribute("bulletinBoards", bulletinBoards);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/search.jsp");
				dispatcher.forward(request, response);

			}else{
				response.setContentType("application/json;charset=UTF-8");
				PrintWriter pw = response.getWriter();
				pw.print("検索できへんかったなもし");
				pw.close();
			}
		}else{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/search.jsp");
			dispatcher.forward(request, response);
		}
	}

}
