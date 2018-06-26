package model;

import java.io.Serializable;

public class BulletinBoardList extends AbstractList<BulletinBoard> implements Serializable {

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
