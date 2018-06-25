package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BulletinBoard;
import model.Comment;

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
		// セッションスコープから掲示板オブジェクトを取得
		HttpSession session = request.getSession();
		BulletinBoard bulletinBoard = (BulletinBoard) session.getAttribute("bulletinBoard");
		// フィールド
		Comment commentObj;
		int bulletin_board_id = bulletinBoard.getId();
		String comment = request.getParameter("comment");


		if(comment != null) {

		} else {
			// エラーメッセージを表示
		}

	}

}
