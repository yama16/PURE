package model;

import java.util.List;

/**
 * コメントリストを保持するクラス。
 *
 *
 * @author 絶対死なない宮じマン
 */
public class CommentList {
	private List<Comment> commentList;

	public Comment get(int index) {
		return commentList.get(index);
	}

	public boolean add(Comment comment) {
		return commentList.add(comment);
	}

	@Override
	public String toString() {
		// JSON形式に組み合わせる処理
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("commentArray:");
		json.append("[");
		for(int i = 0; i < commentList.size(); i++) {
			json.append(commentList.get(i).toString());
			if(i != commentList.size() - 1) {
				json.append(",");
			}
		}
		json.append("]");
		json.append("}");

		return json.toString();
	}
}
