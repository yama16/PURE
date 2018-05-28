package LoginModel;

import java.io.Serializable;

public class User implements Serializable{
	private String id;		//ログイン時のID
	private String pass;	//ログイン時のパスワード
	private String name;   //ログイン時の名前

	//コンストラクター
	public User() {}
	public User(String id, String pass) {
		this.id = id;
		this.pass = pass;
	}
	public User(String id, String pass, String name) {
		this.id = id;
		this.pass = pass;
		this.name = name;
	}

	//ID、パスワード、名前のgetterメソッド
	public String getId() {return this.id;}
	public String getPass() {return this.pass;}
	public String getName() {return this.name;}
}
