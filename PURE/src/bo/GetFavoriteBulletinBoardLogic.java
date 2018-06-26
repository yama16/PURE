package bo;

import java.util.List;

import dao.BulletinBoardsDAO;
import model.BulletinBoard;

/**
 * お気に入りの掲示板を取得するロジッククラス。
 * @author furukawa
 *
 */
public class GetFavoriteBulletinBoardLogic {


	public List<BulletinBoard> execute(String accountId){
		BulletinBoardsDAO dao = new BulletinBoardsDAO();
		List<BulletinBoard> list = dao.findFavorite(accountId);
		return list;
	}

}
