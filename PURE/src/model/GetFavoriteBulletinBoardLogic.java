package model;

import java.util.List;

import dao.FavoritesDAO;

/**
 * お気に入りの掲示板を取得するロジッククラス。
 * @author furukawa
 *
 */
public class GetFavoriteBulletinBoardLogic {


	public List<BulletinBoard> execute(String accountId){
		FavoritesDAO dao = new FavoritesDAO();
		List<BulletinBoard> list = dao.findByAccountId(accountId);
		return list;
	}

}
