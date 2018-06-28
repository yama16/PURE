package model;

import java.io.Serializable;

/**
 * コメントリストを保持するクラス。
 *
 *
 * @author 絶対死なない宮じマン
 */
public class CommentList extends AbstractList<Comment> implements Serializable {

	public boolean setAll(CommentList setList){
		boolean set = true;

		for(int i = 0; i < setList.size(); i++){
			for(int j = 0; j < super.size(); j++) {
				if(true) {   // equals()メソッド追加待ちif( !setList.get(i).equals(super.get(j)) ) {}
					if(!super.add(setList.get(i))){
						set = false;
					}
				}
 			}
		}
		return set;
	}

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
