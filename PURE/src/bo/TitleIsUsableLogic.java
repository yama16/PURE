package bo;

import dao.BulletinBoardsDAO;

/**
 *
 * @author furukawa
 *
 */
public class TitleIsUsableLogic {

	public boolean execute(String title){
		BulletinBoardsDAO dao = new BulletinBoardsDAO();
		return dao.titleIsUsable(title);
	}

}
