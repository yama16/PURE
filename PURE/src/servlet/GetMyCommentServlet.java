package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.GetCommentHistoryLogic;
import model.Account;
import model.BulletinBoardList;

/**
 * Servlet implementation class GetMyCommentServlet
 */
@WebServlet("/GetMyCommentServlet")
public class GetMyCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Account account = (Account)session.getAttribute("account");

		GetCommentHistoryLogic commentHistory = new GetCommentHistoryLogic();
		BulletinBoardList coments = commentHistory.execute(account.getId());

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(coments.toString());
		pw.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
