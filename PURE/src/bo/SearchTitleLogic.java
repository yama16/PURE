package bo;

import java.util.List;

import dao.BulletinBoardsDAO;
import model.BulletinBoard;

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
	public List<BulletinBoard> execute(String keyword){
		BulletinBoardsDAO dao = new BulletinBoardsDAO();
		List<BulletinBoard> list = dao.findByKeyword(keyword);
		return list;
	}

}
