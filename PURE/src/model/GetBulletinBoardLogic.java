package model;

import java.util.List;

import dao.BulletinBoardsDAO;
import dao.CommentsDAO;

/**
 * 掲示板をデータベースから取得するクラス。
 * @author furukawa
 */
public class GetBulletinBoardLogic {

	/**
	 * 掲示板の情報を取得するロジック。
	 * BulletinBoardsDAOのfindByIdメソッドで掲示板のコメント以外の情報を取得する。
	 * 取得できたら、次にCommentsDAOのfindByBulletinBoardIdメソッドで掲示板のコメントのリストを取得する。
	 * @param id 取得したい掲示板のID
	 * @return BulletinBoardのインスタンスを返す。掲示板が無ければnullを返す。
	 */
	public BulletinBoard execute(int id){
		BulletinBoardsDAO bulletinBoardsDAO = new BulletinBoardsDAO();
		BulletinBoard bulletinBoard = bulletinBoardsDAO.findById(id);
		if(bulletinBoard == null){
			return null;
		}
		CommentsDAO commentsDAO = new CommentsDAO();
		List<Comment> commentList = commentsDAO.findByBulletinBoardId(id);
		bulletinBoard.setCommentList(commentList);
		return bulletinBoard;
	}

}
