package bo;

import java.util.List;

import dao.FavoritesDAO;

public class GetMyFavoriteListLogic {

	public List<Integer> execute(String accountId){
		FavoritesDAO dao = new FavoritesDAO();
		return dao.findByAccountId(accountId);
	}

}
