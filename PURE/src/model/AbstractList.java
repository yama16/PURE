package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractList<E> implements Serializable {

	private List<E> list;

	public AbstractList(){
		list = new ArrayList<>();
	}
	public AbstractList(List<E> list){
		this.list = list;
	}

	public E get(int idx){
		if(idx >= list.size() || idx < 0){
			return null;
		}
		return list.get(idx);
	}

	public int size(){
		return list.size();
	}

	public boolean add(E e){
		if(e == null){
			return false;
		}
		if(contains(e)){
			return false;
		}
		return list.add(e);
	}

	public boolean contains(E e){
		return indexOf(e) >= 0;
	}

	public int indexOf(E e){
		if(e == null){
			return -1;
		}
		for(int i = 0; i < list.size(); i++){
			if(e.equals(list.get(i))){
				return i;
			}
		}
		return -1;
	}

	public boolean addAll(AbstractList<E> addList){
		boolean result = true;

		for(int i = 0; i < addList.size(); i++){
			if(!add(addList.get(i))){
				result = false;
			}
		}

		return result;
	}

}
