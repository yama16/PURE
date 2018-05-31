package model;

import java.sql.Timestamp;

/**
 * ひとつのコメントの情報を保持するクラス。
 * @author furukawa
 *
 */
public class Comment {

	private int id;
	private int bulletin_board_id;
	private String account_id;
	private String comment;
	private Timestamp created_at;
	private int pure_quantity;

	public Comment(){}
	public Comment(int id, int bulletin_board_id, String account_id, String comment, Timestamp created_at, int pure_quantity){
		this.id = id;
		this.bulletin_board_id = bulletin_board_id;
		this.account_id = account_id;
		this.comment = comment;
		this.created_at = created_at;
		this.pure_quantity = pure_quantity;
	}

	public int getId(){ return id; }
	public void setId(int id){ this.id = id; }
	public int getBulletin_board_id(){ return bulletin_board_id; }
	public void setBulletin_board_id(int bulletin_board_id){ this.bulletin_board_id = bulletin_board_id; }
	public String getAccount_id(){ return account_id; }
	public void setAccount_id(String account_id){ this.account_id = account_id; }
	public String getComment(){ return comment; }
	public void setComment(String comment){ this.comment = comment; }
	public Timestamp getCreated_at(){ return created_at; }
	public void setCreated_at(Timestamp created_at){ this.created_at = created_at; }
	public int getPure_quantity(){ return pure_quantity; }
	public void setPure_quantity(int pure_quantity){ this.pure_quantity = pure_quantity; }


}
