package model;

import java.util.List;

import dao.BulletinBoardsDAO;

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
