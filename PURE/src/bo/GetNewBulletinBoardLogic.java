package bo;

import dao.BulletinBoardsDAO;
import model.BulletinBoardList;

/**
 *
 * @author furukawa
 *
 */
public class GetNewBulletinBoardLogic {

	/**
	 *
	 * @return
	 */
	public BulletinBoardList execute(){
		BulletinBoardsDAO dao = new BulletinBoardsDAO();
		BulletinBoardList list = dao.getNewBulletinBoard();
		return list;
	}

}
