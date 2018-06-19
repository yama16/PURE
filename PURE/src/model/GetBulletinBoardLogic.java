package model;

import java.util.List;

import dao.BulletinBoardsDAO;
import dao.CommentsDAO;
import dao.TagsDAO;

/**
 * 掲示板をデータベースから取得するクラス。
 * @author furukawa
 */
public class GetBulletinBoardLogic {

	/**
	 * 掲示板の情報を取得するロジック。
	 * BulletinBoardsDAOのfindByIdメソッドで掲示板のコメント以外の情報を取得する。
	 * 取得できたら、次にCommentsDAOのfindByBulletinBoardIdメソッドで掲示板のコメントのリストを取得する。
	 * @param bulletinBoardId 取得したい掲示板のID
	 * @return BulletinBoardのインスタンスを返す。掲示板が無ければnullを返す。
	 */
	public BulletinBoard execute(int bulletinBoardId){
		BulletinBoardsDAO bulletinBoardsDAO = new BulletinBoardsDAO();
		BulletinBoard bulletinBoard = bulletinBoardsDAO.findById(bulletinBoardId);
		if(bulletinBoard == null){
			return null;
		}
		CommentsDAO commentsDAO = new CommentsDAO();
		List<Comment> commentList = commentsDAO.findByBulletinBoardId(bulletinBoardId);
		if(commentList == null){
			return null;
		}
		bulletinBoard.setCommentList(commentList);
		TagsDAO tagsDAO = new TagsDAO();
		List<String> tagList = tagsDAO.findByBulletinBoardId(bulletinBoardId);
		if(tagList == null){
			return null;
		}
		bulletinBoard.setTagList(tagList);
		return bulletinBoard;
	}

}
