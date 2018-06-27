package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.GetNewCommentLogic;
import model.BulletinBoard;
import model.CommentList;

/**
 * Servlet implementation class GetNewCommentServlet
 */
@WebServlet("/GetNewCommentServlet")
public class GetNewCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GetNewCommentServletのdoGetメソッドが動きました");
		// セッションスコープから掲示板オブジェクトを取得
		HttpSession session = request.getSession();
		BulletinBoard bulletinBoard = (BulletinBoard) session.getAttribute("bulletinBoard");
		int bulletin_board_id = bulletinBoard.getId();
		int last_comment_id = bulletinBoard.getCommentList().size();
		System.out.println("last_comment_id:" + last_comment_id);

		GetNewCommentLogic logic = new GetNewCommentLogic();
		CommentList newCommentList = logic.execute(bulletin_board_id, last_comment_id);

		// JSON形式に変換
		String json = newCommentList.toString();
		// セッションスコープに保存した掲示板を更新
		bulletinBoard.getCommentList().setAll(newCommentList);

		// レスポンス処理
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
