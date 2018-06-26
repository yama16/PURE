package bo;

import java.util.List;

import dao.CommentsDAO;
import model.BulletinBoard;

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
	public List<BulletinBoard> execute(String accountId){
		CommentsDAO dao = new CommentsDAO();
		List<BulletinBoard> list = dao.history(accountId);
		return list;
	}

}
