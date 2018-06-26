package bo;

import dao.BulletinBoardsDAO;
import model.BulletinBoardList;

/**
 *
 * @author furukawa
 *
 */
public class GetMyBulletinBoardLogic {
	/**
	 *
	 * @param accountId
	 * @return
	 */
	public BulletinBoardList execute(String accountId){
		BulletinBoardsDAO dao = new BulletinBoardsDAO();
		BulletinBoardList list = dao.findByAccountId(accountId);
		return list;
	}

}
