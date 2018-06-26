package bo;

import dao.CommentsDAO;
import model.CommentList;

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
     * @param comment_id ブラウザに表示している中で最新のコメントのID
     * @return 引数のcomment_idよりも最新のコメントのリストを返す。
     */
	public CommentList execute(int bulletinBoardId, int commentId){
		CommentsDAO dao = new CommentsDAO();
		return dao.findNewComment(bulletinBoardId, commentId);
	}

}
