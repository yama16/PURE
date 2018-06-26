package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BulletinBoard;
import model.CommentList;
import model.GetNewCommentLogic;

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
		int last_comment_id = bulletinBoard.getCommentList().size() - 1;

		GetNewCommentLogic logic = new GetNewCommentLogic();
		CommentList newCommentList = logic.execute(bulletin_board_id, last_comment_id);

		// JSON形式に組み合わせる処理
		StringBuffer json = new StringBuffer();
		for(int i = 0; i < newCommentList.size(); i++) {
			json.append(newCommentList.get(i).toString());
			if(i != newCommentList.size() - 1) {
				json.append(",");
			}
		}

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
