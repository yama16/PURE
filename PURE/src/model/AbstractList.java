package model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractList<T> {

	private List<T> list;

	public AbstractList(){
		list = new ArrayList<>();
	}
	public AbstractList(List<T> list){
		this.list = list;
	}

	public T get(int idx){
		if(idx >= list.size()){
			return null;
		}
		return list.get(idx);
	}

	public int size(){
		return list.size();
	}

	public boolean add(T t){
		if(t == null){
			return false;
		}
		return list.add(t);
	}

	public boolean addAll(AbstractList<T> addList){
		boolean add = true;
		for(int i = 0; i < addList.size(); i++){
			if(!list.add(addList.get(i))){
				add = false;
			}
		}
		return add;
	}

}
