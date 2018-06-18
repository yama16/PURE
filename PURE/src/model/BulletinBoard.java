package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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
	private List<Comment> commentList;
	private List<String> tagList;

	public BulletinBoard(){}
	public BulletinBoard(int id, String title, String accountId, Timestamp createdAt, int viewQuantity, int favoriteQuantity){
		this.id = id;
		this.title = title;
		this.accountId = accountId;
		this.createdAt = createdAt;
		this.viewQuantity = viewQuantity;
		this.favoriteQuantity = favoriteQuantity;
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
	public List<Comment> getCommentList(){ return commentList; }
	public void setCommentList(List<Comment> commentList){ this.commentList = commentList; }
	public List<String> getTagList(){ return tagList; }
	public void setTagList(List<String> tagList){ this.tagList = tagList; }
}
