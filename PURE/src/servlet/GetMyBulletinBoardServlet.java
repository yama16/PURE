package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.GetMyBulletinBoardLogic;
import model.Account;
import model.BulletinBoardList;

/**
 * Servlet implementation class GetMyBulletinBoardServlet
 */
@WebServlet("/GetMyBulletinBoardServlet")
public class GetMyBulletinBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("account");
		BulletinBoardList bulletinBoardList;
		GetMyBulletinBoardLogic bulletinBoardLogic = new GetMyBulletinBoardLogic();
		bulletinBoardList = bulletinBoardLogic.execute(account.getId());


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
