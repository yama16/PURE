package bo;

import dao.BulletinBoardsDAO;
import model.BulletinBoardList;

/**
 * お気に入りの掲示板を取得するロジッククラス。
 * @author furukawa
 *
 */
public class GetFavoriteBulletinBoardLogic {


	public BulletinBoardList execute(String accountId){
		BulletinBoardsDAO dao = new BulletinBoardsDAO();
		BulletinBoardList list = dao.findFavorite(accountId);
		return list;
	}

}
