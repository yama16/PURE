package servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.BulletinBoard;
import model.CreateBulletinBoardLogic;

/**
 * Servlet implementation class CreateBulletinBoardServlet
 */
@WebServlet("/CreateBulletinBoardServlet")
public class CreateBulletinBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("account");
		String accountId = account.getId();
		String title = request.getParameter("title");
		long now = System.currentTimeMillis();
		List<String> test = new ArrayList<String>();
		test.add("test1");
		test.add("test2");

		System.out.println(account.getId());
		System.out.println("testBoardCreate");

		BulletinBoard bulletinBoard = new BulletinBoard();

		bulletinBoard.setTitle(title);
		bulletinBoard.setAccountId(accountId);
		bulletinBoard.setCreatedAt(new Timestamp(now));

		bulletinBoard.setTagList(test);

		CreateBulletinBoardLogic createBoardLogic = new CreateBulletinBoardLogic();
		createBoardLogic.execute(bulletinBoard);


	}

}