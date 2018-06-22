package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * ひとつのコメントの情報を保持するクラス。
 * @author furukawa
 *
 */
public class Comment implements Serializable{

	private int id;
	private int bulletinBoardId;
	private String nickName;
	private String accountId;
	private String comment;
	private Timestamp createdAt;
	private int pureQuantity;

	public Comment(){}
	public Comment(int id, int bulletinBoardId, String nickName, String accountId, String comment, Timestamp createdAt, int pureQuantity){
		this.id = id;
		this.bulletinBoardId = bulletinBoardId;
		this.nickName = nickName;
		this.accountId = accountId;
		this.comment = comment;
		this.createdAt = createdAt;
		this.pureQuantity = pureQuantity;
	}

	/**
	 * JSON形式にして返す。
	 * @return JSON形式にした文字列
	 */
	@Override
	public String toString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/H:m:s");
		StringBuffer json = new StringBuffer();
		json.append("{\"id\":")
			.append(id)
			.append(",\" bulletinBoardId\":")
			.append(bulletinBoardId)
			.append(", \"nickName\":\"")
			.append(nickName)
			.append("\", \"accountId\":\"")
			.append(accountId)
			.append("\", \"comment\":\"")
			.append(comment)
			.append("\", \"createdAt\":\"")
			.append(sdf.format(createdAt.getTime()))
			.append("\", \"pureQuantity\":")
			.append(pureQuantity)
			.append("}");
		return json.toString();
	}

	public int getId(){ return id; }
	public void setId(int id){ this.id = id; }
	public int getBulletinBoardId(){ return bulletinBoardId; }
	public void setBulletinBoardId(int bulletinBoardId){ this.bulletinBoardId = bulletinBoardId; }
	public String getNickName() { return nickName; }
	public void setNickName(String nickName) { this.nickName = nickName; }
	public String getAccountId(){ return accountId; }
	public void setAccountId(String accountId){ this.accountId = accountId; }
	public String getComment(){ return comment; }
	public void setComment(String comment){ this.comment = comment; }
	public Timestamp getCreatedAt(){ return createdAt; }
	public void setCreatedAt(Timestamp createdAt){ this.createdAt = createdAt; }
	public int getPureQuantity(){ return pureQuantity; }
	public void setPureQuantity(int pureQuantity){ this.pureQuantity = pureQuantity; }


}
