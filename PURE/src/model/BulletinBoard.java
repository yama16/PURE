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
	private String account_id;
	private Timestamp created_at;
	private Timestamp updated_at;
	private int view_quantity;
	private int pure_quantity;

	public BulletinBoard(){}
	public BulletinBoard(int id, String title, String account_id, Timestamp created_at, Timestamp updated_at, int view_quantity, int pure_quantity){
		this.id = id;
		this.title = title;
		this.account_id = account_id;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.view_quantity = view_quantity;
		this.pure_quantity = pure_quantity;
	}

	public int getId(){ return id; }
	public void setId(int id){ this.id = id; }
	public String getTitle(){ return title; }
	public void setTitle(String title){ this.title = title; }
	public String getAccount_id(){ return account_id; }
	public void setAccount_id(String account_id){ this.account_id = account_id; }
	public Timestamp getCreated_at(){ return created_at; }
	public void setCreated_at(Timestamp created_at){ this.created_at = created_at; }
	public Timestamp getUpdated_at(){ return updated_at; }
	public void setUpdated_at(Timestamp updated_at){ this.updated_at = updated_at; }
	public int getView_quantity(){ return view_quantity; }
	public void setView_quantity(int view_quantity){ this.view_quantity = view_quantity; }
	public int getPure_quantity(){ return pure_quantity; }
	public void setPure_quantity(int pure_quantity){ this.pure_quantity = pure_quantity; }
}
