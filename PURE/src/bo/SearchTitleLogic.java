package bo;

import dao.BulletinBoardsDAO;
import model.BulletinBoardList;

/**
 * 掲示板をタイトルで検索するロジック。
 * @author furukawa
 *
 */
public class SearchTitleLogic {

	/**
	 * 引数のキーワードで掲示板のタイトルを検索する。
	 * @param keyword 検索する文字列
	 * @return 検索結果の掲示板のリスト
	 */
	public BulletinBoardList execute(String keyword){
		BulletinBoardsDAO dao = new BulletinBoardsDAO();
		BulletinBoardList list = dao.findByKeyword(keyword);
		return list;
	}

}
