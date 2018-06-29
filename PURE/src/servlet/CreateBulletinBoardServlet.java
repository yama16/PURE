package servlet;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.CreateBulletinBoardLogic;
import model.Account;
import model.BulletinBoard;
import model.TagList;

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
		System.out.println(title);
		long now = System.currentTimeMillis();

		TagList test = new TagList();
		for(int i = 1; i <= 6; i++) {
			String tag = request.getParameter("tag"+i);
			if(!(tag.equals(null))) {
				test.add(tag);
			}
		}

		System.out.println(account.getId());
		System.out.println("testBoardCreate");

		BulletinBoard bulletinBoard = new BulletinBoard();

		bulletinBoard.setTitle(title);
		bulletinBoard.setAccountId(accountId);
		bulletinBoard.setCreatedAt(new Timestamp(now));

		bulletinBoard.setTagList(test);

		System.out.println("lolo");

		CreateBulletinBoardLogic createBoardLogic = new CreateBulletinBoardLogic();
		createBoardLogic.execute(bulletinBoard);

		System.out.println("koko");
	}

}
