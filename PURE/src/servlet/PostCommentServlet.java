package servlet;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.BulletinBoard;
import model.PostCommentLogic;

/**
 * Servlet implementation class PostCommentServlet
 */
@WebServlet("/PostCommentServlet")
public class PostCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションスコープから掲示板オブジェクトとアカウントオブジェクトを取得
		System.out.println("POST!!");
		HttpSession session = request.getSession();
		BulletinBoard bulletinBoard = (BulletinBoard) session.getAttribute("bulletinBoard");
		Account account = (Account) session.getAttribute("account");
		// フィールド
		PostCommentLogic postCommentLogic;
		int bulletinBoardId = bulletinBoard.getId();
		String accountId = account.getId();
		String comment = request.getParameter("comment");
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());

		if(comment != null) {
			postCommentLogic = new PostCommentLogic();
			if(postCommentLogic.execute(bulletinBoardId, accountId, comment, currentTime)) {
				System.out.println("newComment登録成功！！");
			} else {
				System.out.println("newComment登録失敗！！");
			}
		} else {
			// エラーメッセージを表示
		}

	}

}
