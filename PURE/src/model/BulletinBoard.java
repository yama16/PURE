package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author furukawa
 *
 */
public class BulletinBoard implements Serializable {

	private int id;
	private String title;
	private String accountId;
	private Timestamp createdAt;
	private int viewQuantity;
	private int favoriteQuantity;
	private CommentList commentList;
	private TagList tagList;

	public BulletinBoard(){
		this.commentList = new CommentList();
		this.tagList = new TagList();
	}
	public BulletinBoard(int id, String title, String accountId, Timestamp createdAt, int viewQuantity, int favoriteQuantity, CommentList commentList, TagList tagList){
		this.id = id;
		this.title = title;
		this.accountId = accountId;
		this.createdAt = createdAt;
		this.viewQuantity = viewQuantity;
		this.favoriteQuantity = favoriteQuantity;
		this.commentList = commentList;
		this.tagList = tagList;
	}

	@Override
	public String toString(){
		StringBuffer json = new StringBuffer();
		json.append("{\"id\":")
			.append(id)
			.append(", \"title\":\"")
			.append(title.replace("\\", "\\\\").replace("\n", "\\n").replace("\"", "\\\""))
			.append("\", \"accountId\":\"")
			.append(accountId)
			.append("\", \"createdAt\":\"")
			.append(createdAt)
			.append("\", \"viewQuantity\":")
			.append(viewQuantity)
			.append(", \"favoriteQuantity\":")
			.append(favoriteQuantity)
			.append(", \"commentList\":")
			.append(commentList.toString())
			.append(", \"tagList\":")
			.append(tagList.toString())
			.append("}");
		return json.toString();
	}

	@Override
	public boolean equals(Object o){
		if(o == this) return true;
		if(o == null) return false;
		if(!(o instanceof BulletinBoard)) return false;
		BulletinBoard b = (BulletinBoard)o;
		if(!(this.id == b.id)){
			return false;
		}
		return true;
	}

	public int getId(){ return id; }
	public void setId(int id){ this.id = id; }
	public String getTitle(){ return title; }
	public void setTitle(String title){ this.title = title; }
	public String getAccountId(){ return accountId; }
	public void setAccountId(String accountId){ this.accountId = accountId; }
	public Timestamp getCreatedAt(){ return createdAt; }
	public void setCreatedAt(Timestamp createdAt){ this.createdAt = createdAt; }
	public int getViewQuantity(){ return viewQuantity; }
	public void setViewQuantity(int viewQuantity){ this.viewQuantity = viewQuantity; }
	public int getFavoriteQuantity(){ return favoriteQuantity; }
	public void setFavoriteQuantity(int favoriteQuantity){ this.favoriteQuantity = favoriteQuantity; }
	public CommentList getCommentList(){ return commentList; }
	public void setCommentList(CommentList commentList){ this.commentList = commentList; }
	public TagList getTagList(){ return tagList; }
	public void setTagList(TagList tagList){ this.tagList = tagList; }
}
