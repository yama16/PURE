package model;

import java.util.List;

import dao.BulletinBoardsDAO;

/**
 * 掲示板のリストをランキングで取得するロジック。
 * @author furukawa
 *
 */
public class GetRankingLogic {

	/**
	 * 引数に1を渡せばお気に入りの多い順に出力、2を渡せば閲覧数の多い順に出力する。
	 * @param order 何を基準に並べ替えるか指定する。1はお気に入り、2は閲覧数、他はエラー。
	 * @return 掲示板のリスト。0からランキング順。
	 */
	public List<BulletinBoard> execute(int order){
		BulletinBoardsDAO dao = new BulletinBoardsDAO();
		List<BulletinBoard> list = dao.ranking(order);
		return list;
	}

}
