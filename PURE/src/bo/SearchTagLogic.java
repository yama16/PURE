package bo;

import dao.BulletinBoardsDAO;
import model.BulletinBoardList;

/**
 * タグで掲示板を検索するロジック。
 * @author furukawa
 *
 */
public class SearchTagLogic {

    /**
     * 引数で指定したタグから掲示板を検索するメソッド。
     * 第2引数にtrueを指定すれば部分一致検索、falseを指定すれば完全一致検索になる。
     * @param tag 検索するタグ
     * @param partial 部分一致検索ならtrue、完全一致検索ならfalseを指定。
     * @return 検索した掲示板のリスト
     */
	public BulletinBoardList execute(String tag, boolean partial){
		BulletinBoardsDAO dao = new BulletinBoardsDAO();
		BulletinBoardList list = dao.findByTag(tag, partial);
		return list;
	}

}
