package model;

import java.util.List;

import dao.CommentsDAO;

/**
 * 掲示板のコメントを取ってくる処理をするクラス。
 * @author furukawa
 *
 */
public class GetCommentLogic {

	/**
     * 掲示板IDからその掲示板のコメントのリストを返すメソッド。
     * @param bulletin_board_id 検索する掲示板のID
     * @return 検索した掲示板のコメントのリストを返す。コメントが無ければnullを返す。
     */
	public List<Comment> execute(int bulletin_board_id){
		CommentsDAO dao = new CommentsDAO();
		return dao.findByBulletin_board_id(bulletin_board_id);
	}

}
