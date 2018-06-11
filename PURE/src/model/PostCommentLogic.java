package model;

import dao.CommentsDAO;

/**
 * コメントをデータベースに追加するロジック。
 * @author furukawa
 *
 */
public class PostCommentLogic {

	/**
	 * コメントを追加する。
	 * @param comment 追加するコメント
	 * @return 追加できればtrueを返す。できなければfalseを返す。
	 */
	public boolean execute(Comment comment){
		CommentsDAO dao = new CommentsDAO();
		return dao.create(comment);
	}

}
