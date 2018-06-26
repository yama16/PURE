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
	 * @return 追加すれば1を返す。削除すれば-1を返す。失敗すれば0を返す。
	 */
	public int execute(String accountId, int commentId, int bulletinBoardId){
		PuresDAO dao = new PuresDAO();
		return dao.toggle(accountId, commentId, bulletinBoardId);
	}

}
