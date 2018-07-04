package model;

import java.io.Serializable;

public class TagList extends AbstractList<String> implements Serializable {

	@Override
	public boolean add(String tag){
		if(tag.equals("")){
			return false;
		}
		return super.add(tag);
	}

	@Override
	public String toString(){
		StringBuffer json = new StringBuffer();
		json.append("[");
		for(int i = 0; i < super.size(); i++){
			if(i != 0){
				json.append(",");
			}
			json.append("\"")
				.append(super.get(i).replace("\\", "\\\\").replace("\"", "\\\"").replace("\'", "\\\'"))
				.append("\"");
		}
		json.append("]");
		return json.toString();
	}

}
