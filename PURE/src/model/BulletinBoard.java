package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
	private CommentList commentList;
	private TagList tagList;

	public BulletinBoard(){}
	public BulletinBoard(int id, String title, String accountId, Timestamp createdAt, int viewQuantity, int favoriteQuantity){
		this.id = id;
		this.title = title;
		this.accountId = accountId;
		this.createdAt = createdAt;
		this.viewQuantity = viewQuantity;
		this.favoriteQuantity = favoriteQuantity;
	}

	@Override
	public String toString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/H:m:s");
		StringBuffer json = new StringBuffer();
		json.append("{\"id\":")
			.append(id)
			.append(", \"title\":\"")
			.append(title)
			.append("\", \"accountId\":\"")
			.append(accountId)
			.append("\", \"createdAt\":\"")
			.append(sdf.format(createdAt.getTime()))
			.append("\", \"viewQuantity\":")
			.append(viewQuantity)
			.append(", \"favoriteQuantity\":")
			.append(favoriteQuantity)
			.append(", \"commentList\":")
			.append(commentList.toString())
			.append(", \"tagList\":[");
		if(tagList != null){
			for(int i = 0; i < tagList.size(); i++){
				if(i != 0){
					json.append(", ");
				}
				json.append("\"");
				json.append(tagList.get(i));
				json.append("\"");
			}
		}
		json.append("]}");
		return json.toString();
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
