package bo;

import dao.FavoritesDAO;

/**
 *
 * @author furukawa
 *
 */
public class IsFavoriteLogic {

	/**
	 *
	 * @param accountId
	 * @param bulletinBoardId
	 * @return
	 */
	public boolean execute(String accountId, int bulletinBoardId){
		FavoritesDAO dao = new FavoritesDAO();
		return dao.find(accountId, bulletinBoardId);
	}

}
