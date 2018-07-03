package bo;

import java.util.List;

import dao.CommentsDAO;

public class GetPureCommentsLogic {

	public List<Integer> execute(int bulletinBoardId){
		CommentsDAO dao = new CommentsDAO();
		List<Integer> list = dao.pureComments(bulletinBoardId);
		return list;
	}

}
