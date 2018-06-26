package model;

import java.io.Serializable;

public class TagList extends AbstractList<String> implements Serializable {

	@Override
	public String toString(){
		StringBuffer json = new StringBuffer();
		json.append("[");
		for(int i = 0; i < super.size(); i++){
			if(i != 0){
				json.append(",");
			}
			json.append("\"")
				.append(super.get(i))
				.append("\"");
		}
		json.append("]");
		return json.toString();
	}

}
