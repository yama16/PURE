package model;

import java.util.List;

import dao.CommentsDAO;

/**
 * 掲示板の新しいコメントを取ってくるクラス。
 * @author furukawa
 *
 */
public class GetNewCommentLogic {

	/**
     * 引数で指定した掲示板のコメントの中から、引数で指定したコメントより新しいコメントのリストを返すメソッド。
     *
     * @param bulletin_board_id 検索する掲示板のID
     * @param comment_id 最新のコメントのID
     * @return 引数のcomment_idよりも最新のコメントのリストを返す。
     * 引数のcomment_idよりも最新のコメントが無ければnullを返す。
     */
	public List<Comment> execute(int bulletin_board_id, int comment_id){
		CommentsDAO dao = new CommentsDAO();
		return dao.findNewComment(bulletin_board_id, comment_id);
	}

}
