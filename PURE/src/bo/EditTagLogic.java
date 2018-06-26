package bo;

import dao.TagsDAO;
import model.TagList;

/**
 * 掲示板のタグを編集するロジック。
 * @author furukawa
 *
 */
public class EditTagLogic {

	/**
	 *
	 * @param bulletinBoardId
	 * @param tagList
	 * @return
	 */
	public boolean execute(int bulletinBoardId, TagList tagList){
		TagsDAO dao = new TagsDAO();
		boolean result = dao.deleteByBulletinBoardId(bulletinBoardId);
		if(result == false){
			return false;
		}
		return dao.createAll(bulletinBoardId, tagList);
	}

}
