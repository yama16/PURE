package bo;

import java.util.List;

import dao.BulletinBoardsDAO;
import model.BulletinBoard;

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
	public List<BulletinBoard> execute(String accountId){
		BulletinBoardsDAO dao = new BulletinBoardsDAO();
		List<BulletinBoard> list = dao.findByAccountId(accountId);
		return list;
	}

}
