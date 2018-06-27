package bo;

import dao.CommentsDAO;
import model.BulletinBoardList;

/**
 * アカウントのコメントの履歴を取得するロジック。
 * @author furukawa
 *
 */
public class GetCommentHistoryLogic {

	/**
	 *
	 * @param accountId
	 * @return
	 */
	public BulletinBoardList execute(String accountId){
		CommentsDAO dao = new CommentsDAO();
		BulletinBoardList list = dao.history(accountId);
		return list;
	}

}
