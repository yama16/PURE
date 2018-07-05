package bo;

import java.util.List;

import dao.PuresDAO;

public class GetMyPureCommentListLogic {

	public List<Integer> execute(String accountId, int bulletinBoardId){
		PuresDAO dao = new PuresDAO();
		return dao.getPureCommentList(accountId, bulletinBoardId);
	}

}
