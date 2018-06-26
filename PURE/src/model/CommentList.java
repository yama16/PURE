package model;

import java.io.Serializable;

/**
 * コメントリストを保持するクラス。
 *
 *
 * @author 絶対死なない宮じマン
 */
public class CommentList extends AbstractList<Comment> implements Serializable {

	@Override
	public String toString() {
		// JSON形式に組み合わせる処理
		StringBuffer json = new StringBuffer();
		json.append("[");
		for(int i = 0; i < super.size(); i++) {
			if(i != 0){
				json.append(",");
			}
			json.append(super.get(i).toString());
		}
		json.append("]");

		return json.toString();
	}
}
