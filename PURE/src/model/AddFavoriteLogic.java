package model;

import dao.FavoritesDAO;

/**
 * お気に入りを追加するロジッククラス。
 * @author furukawa
 *
 */
public class AddFavoriteLogic {

	/**
     * お気に入りを追加するロジック。
     * @param accountId お気に入りをしたアカウントのID
     * @param bulletinBoardId お気に入りした掲示板のID
     * @return お気に入りに追加できればtrue。できなければfalse。
     */
	public boolean execute(String accountId, int bulletinBoardId){
		FavoritesDAO dao = new FavoritesDAO();
		return dao.create(accountId, bulletinBoardId);
	}

}
