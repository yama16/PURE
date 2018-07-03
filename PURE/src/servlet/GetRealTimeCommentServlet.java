package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bo.GetRealTimeCommentLogic;
import model.BulletinBoardList;

/**
 * Servlet implementation class GetRialcommentServlet
 */
@WebServlet("/GetRealTimeCommentServlet")
public class GetRealTimeCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long now = 0;

		try{
			now = Long.parseLong(request.getParameter("time"));
		}catch(NumberFormatException e) {
			e.printStackTrace();
			now = System.currentTimeMillis();
		}

		GetRealTimeCommentLogic realTimeComments = new GetRealTimeCommentLogic();
		BulletinBoardList bulletinBoards = new BulletinBoardList();

		bulletinBoards = realTimeComments.execute(new Timestamp(now));

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(bulletinBoards.toString());
		pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
