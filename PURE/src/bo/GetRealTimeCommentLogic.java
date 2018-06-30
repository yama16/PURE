package bo;

import java.sql.Timestamp;

import dao.CommentsDAO;
import model.BulletinBoardList;

public class GetRealTimeCommentLogic {

	public BulletinBoardList execute(Timestamp timestamp){
		CommentsDAO dao = new CommentsDAO();
		BulletinBoardList realTimeComment = dao.getRealTimeComment();
		return realTimeComment;
	}

}
