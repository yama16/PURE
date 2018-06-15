package model;

import dao.FavoritesDAO;

/**
 * お気に入りを削除するロジッククラス。
 * @author furukawa
 *
 */
public class DeleteFavoriteLogic {

	/**
     * お気に入りを削除するメソッド。
     * @param accountId お気に入りを削除するアカウントのID
     * @param bulletinBoardId 削除するお気に入りの掲示板のID
     * @return 削除できればtrue。できなければfalse。
     */
	public boolean execute(String accountId, int bulletinBoardId){
		FavoritesDAO dao = new FavoritesDAO();
		return dao.delete(accountId, bulletinBoardId);
	}

}
