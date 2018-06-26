package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TagList implements Serializable {

	private List<String> tagList;

	public TagList(){
		tagList = new ArrayList<>();
	}
	public TagList(List<String> tagList){
		this.tagList = tagList;
	}

	public String get(int idx){
		if(idx >= tagList.size()){
			return null;
		}
		return tagList.get(idx);
	}

	public boolean add(String tag){
		if(tag == null){
			return false;
		}
		return tagList.add(tag);
	}

	public int size(){
		return tagList.size();
	}

	@Override
	public String toString(){
		StringBuffer json = new StringBuffer();
		json.append("[");
		for(int i = 0; i < tagList.size(); i++){
			if(i != 0){
				json.append(",");
			}
			json.append("\"")
				.append(tagList.get(i))
				.append("\"");
		}
		json.append("]");
		return json.toString();
	}

}
