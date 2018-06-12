package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ひとつのコメントの情報を保持するクラス。
 * @author furukawa
 *
 */
public class Comment implements Serializable{

	private int id;
	private int bulletinBoardId;
	private String accountId;
	private String comment;
	private Timestamp createdAt;
	private int pureQuantity;

	public Comment(){}
	public Comment(int id, int bulletinBoardId, String accountId, String comment, Timestamp createdAt, int pureQuantity){
		this.id = id;
		this.bulletinBoardId = bulletinBoardId;
		this.accountId = accountId;
		this.comment = comment;
		this.createdAt = createdAt;
		this.pureQuantity = pureQuantity;
	}

	public int getId(){ return id; }
	public void setId(int id){ this.id = id; }
	public int getBulletinBoardId(){ return bulletinBoardId; }
	public void setBulletinBoardId(int bulletinBoardId){ this.bulletinBoardId = bulletinBoardId; }
	public String getAccountId(){ return accountId; }
	public void setAccountId(String accountId){ this.accountId = accountId; }
	public String getComment(){ return comment; }
	public void setComment(String comment){ this.comment = comment; }
	public Timestamp getCreatedAt(){ return createdAt; }
	public void setCreatedAt(Timestamp createdAt){ this.createdAt = createdAt; }
	public int getPureQuantity(){ return pureQuantity; }
	public void setPureQuantity(int pureQuantity){ this.pureQuantity = pureQuantity; }


}
