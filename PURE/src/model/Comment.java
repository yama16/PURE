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

	public Comment(int id, int bulletin_board_id, String comment, Timestamp created_at, int pure_quantity){
		this.id = id;
		this.bulletin_board_id = bulletin_board_id;
		this.comment = comment;
		this.created_at = created_at;
		this.pure_quantity = pure_quantity;
	}

	public int getId(){ return id; }
	public int getBulletin_board_id(){ return bulletin_board_id; }
	public String getAccount_id(){ return account_id; }
	public String getComment(){ return comment; }
	public Timestamp getCreated_at(){ return created_at; }
	public int getPure_quantity(){ return pure_quantity; }

}
