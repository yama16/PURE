package model;

import dao.PuresDAO;

/**
 * PUREとPURE解除をするクラス。
 * @author furukawa
 *
 */
public class TogglePureLogic {

	/**
	 * 既にPUREしていればそれを削除し、していなければ作成する。
	 * @param accountId アカウントのID
	 * @param commentId コメントのID
	 * @param bulletinBoardId 掲示板のID
	 * @return 成功すればtrueを返す。失敗すればfalseを返す。
	 */
	public boolean execute(String accountId, int commentId, int bulletinBoardId){
		PuresDAO dao = new PuresDAO();
		if(dao.find(accountId, commentId, bulletinBoardId)){
			return dao.delete(accountId, commentId, bulletinBoardId);
		}else{
			return dao.create(accountId, commentId, bulletinBoardId);
		}
	}

}
