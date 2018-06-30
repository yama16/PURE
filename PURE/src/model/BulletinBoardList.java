package model;

import java.io.Serializable;

public class BulletinBoardList extends AbstractList<BulletinBoard> implements Serializable {

	public boolean addComment(Comment comment){
		int idx = indexOf(comment.getBulletinBoardId());
		if(idx == -1){
			return false;
		}
		return super.get(idx).getCommentList().add(comment);
	}

	public boolean addTag(String tag, int bulletinBoardId){
		int idx = indexOf(bulletinBoardId);
		if(idx == -1){
			return false;
		}
		return super.get(idx).getTagList().add(tag);
	}

	public int indexOf(int id){
		for(int i = 0; i < super.size(); i++){
			if(id == super.get(i).getId()){
				return i;
			}
		}
		return -1;
	}

	@Override
	public String toString(){
		StringBuffer json = new StringBuffer();
		json.append("[");
		for(int i = 0; i < super.size(); i++){
			if(i != 0){
				json.append(",");
			}
			json.append(super.get(i).toString());
		}
		json.append("]");
		return json.toString();
	}

}
