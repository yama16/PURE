package model;

import java.sql.Timestamp;

/**
 *
 * @author furukawa
 *
 */
public class BulletinBoard {

	private int id;
	private String title;
	private String accountId;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private int viewQuantity;
	private int pureQuantity;

	public BulletinBoard(){}
	public BulletinBoard(int id, String title, String accountId, Timestamp createdAt, Timestamp updatedAt, int viewQuantity, int pureQuantity){
		this.id = id;
		this.title = title;
		this.accountId = accountId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.viewQuantity = viewQuantity;
		this.pureQuantity = pureQuantity;
	}

	public int getId(){ return id; }
	public void setId(int id){ this.id = id; }
	public String getTitle(){ return title; }
	public void setTitle(String title){ this.title = title; }
	public String getAccountId(){ return accountId; }
	public void setAccountId(String accountId){ this.accountId = accountId; }
	public Timestamp getCreatedAt(){ return createdAt; }
	public void setCreatedAt(Timestamp createdAt){ this.createdAt = createdAt; }
	public Timestamp getUpdatedAt(){ return updatedAt; }
	public void setUpdatedAt(Timestamp updatedAt){ this.updatedAt = updatedAt; }
	public int getViewQuantity(){ return viewQuantity; }
	public void setViewQuantity(int viewQuantity){ this.viewQuantity = viewQuantity; }
	public int getPureQuantity(){ return pureQuantity; }
	public void setPureQuantity(int pureQuantity){ this.pureQuantity = pureQuantity; }
}
