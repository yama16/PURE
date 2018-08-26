package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.GetBulletinBoardLogic;
import bo.GetMyPureCommentListLogic;
import bo.GetPureCommentsLogic;
import bo.IsFavoriteLogic;
import model.Account;
import model.BulletinBoard;

/**
 * Servlet implementation class BulletinBoardServlet
 * @author furukawa
 */
@WebServlet("/BulletinBoardServlet")
public class BulletinBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// パラメータを取得
		int bulletinBoardId = Integer.parseInt(request.getParameter("id"));
		System.out.println("掲示板ID:" + bulletinBoardId + " がリクエストされました"); // テスト

		// セッションスコープからアカウント情報(id)を取得
		Account account = (Account) session.getAttribute("account");
		// 初期宣言
		boolean isFavorite = false;
		List<Integer> myPureCommentList;
		// アカウント情報を取得できた場合とできない場合で分岐
		if(account != null) {
			// myPureCommentListを取得し、リクエストスコープに保存
			GetMyPureCommentListLogic getMyPureCommentListLogic = new GetMyPureCommentListLogic();
			myPureCommentList = getMyPureCommentListLogic.execute(account.getId(), bulletinBoardId);
			request.setAttribute("myPureCommentList", myPureCommentList);

			// 掲示板をお気に入りに登録しているか判断
			IsFavoriteLogic isFavoriteLogic = new IsFavoriteLogic();
			isFavorite = isFavoriteLogic.execute(account.getId(), bulletinBoardId);
			request.setAttribute("isFavorite", isFavorite);

		} else {
			// isFavoriteをリクエストスコープに保存
			request.setAttribute("isFavorite", isFavorite);

			// 空の状態のmyPureCommentListをリクエストスコープに追加
			myPureCommentList = new ArrayList<>();
			request.setAttribute("myPureCommentList", myPureCommentList);
		}

		// GetBulletinBoardLogicにIDを渡し、掲示板オブジェクトを取得
		GetBulletinBoardLogic getBulletinBoard = new GetBulletinBoardLogic();
		BulletinBoard bulletinBoard = getBulletinBoard.execute(bulletinBoardId);

		// pureCommentListを取得し、リクエストスコープに保存
		GetPureCommentsLogic getPureCommentLogic = new GetPureCommentsLogic();
		List<Integer> pureCommentList = getPureCommentLogic.execute(bulletinBoardId);
		request.setAttribute("pureCommentList", pureCommentList);

		// nullではなかったらセッションスコープに掲示板オブジェクトを保存
		if(bulletinBoard != null) {
			session.setAttribute("bulletinBoard", bulletinBoard);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/createBulletinBoard.jsp");
			dispatcher.forward(request, response);
		} else {
			// エラーメッセージを表示
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
