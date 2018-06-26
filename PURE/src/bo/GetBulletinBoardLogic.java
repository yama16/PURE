package bo;

import dao.BulletinBoardsDAO;
import model.BulletinBoard;

/**
 * 掲示板をデータベースから取得するクラス。
 * @author furukawa
 */
public class GetBulletinBoardLogic {

	/**
	 * 掲示板の情報を取得するロジック。
	 * @param bulletinBoardId 取得したい掲示板のID
	 * @return BulletinBoardのインスタンスを返す。掲示板が無ければnullを返す。
	 */
	public BulletinBoard execute(int bulletinBoardId){
		BulletinBoardsDAO bulletinBoardsDAO = new BulletinBoardsDAO();
		BulletinBoard bulletinBoard = bulletinBoardsDAO.findById(bulletinBoardId);
		return bulletinBoard;
	}

}
