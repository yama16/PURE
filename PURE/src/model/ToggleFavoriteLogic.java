package model;

import dao.FavoritesDAO;

/**
 * お気に入りを追加、削除するロジッククラス。
 * @author furukawa
 *
 */
public class ToggleFavoriteLogic {

	/**
     * 引数で指定したアカウントIDと掲示板IDのレコードがfavoriteテーブルにあれば削除し、無ければ追加する。
     * @param accountId お気に入りを押したアカウントのID
     * @param bulletinBoardId お気に入りを押された掲示板のID
     * @return お気に入りに追加されれば1、削除されれば-1、エラーなどで処理できなければ0を返す。
     */
	public int execute(String accountId, int bulletinBoardId){
		FavoritesDAO dao = new FavoritesDAO();
		return dao.toggle(accountId, bulletinBoardId);
	}

}
