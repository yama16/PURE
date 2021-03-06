package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.GetFavoriteBulletinBoardLogic;
import model.Account;
import model.BulletinBoardList;


@WebServlet("/FavoriteServlet")
public class FavoriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession();
		Account account = (Account)session.getAttribute("account");

		BulletinBoardList favoriteList;
		GetFavoriteBulletinBoardLogic getBoard = new GetFavoriteBulletinBoardLogic();
		favoriteList = getBoard.execute(account.getId());

		System.out.println(favoriteList.toString());

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(favoriteList.toString());
		pw.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	}

}
