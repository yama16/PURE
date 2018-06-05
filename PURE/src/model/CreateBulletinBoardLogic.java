package model;

import dao.BulletinBoardsDAO;

/**
 *
 * @author furukawa
 *
 */
public class CreateBulletinBoardLogic {

	public boolean execute(BulletinBoard bulletinBoard){
		BulletinBoardsDAO dao = new BulletinBoardsDAO();
		return dao.create(bulletinBoard);
	}

}
