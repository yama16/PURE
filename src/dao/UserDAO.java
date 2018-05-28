package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import LoginModel.User;

public class UserDAO {

	//JDBCドライバの読み込み
	private final String DRIVER_NAME = "org.h2.Driver";
	//データベースへ接続
	private final String JDBC_URL = "jdbc:h2:file:C:/data/pure";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";

	public List<User> findAll() {
		Connection conn = null;
		List<User> userList = new ArrayList<User>();

		try{

			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS);

			//SELECT文の準備
			String sql = "SELECT ID,NAME,TEXT FROM MUTTER ORDER BY ID DESC";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			//SELECTを実行
			ResultSet rs = pStmt.executeQuery();

			//SELECT文の結果をArrayListに格納
			while(rs.next()) {
				String id = rs.getString("ID");
				String pass = rs.getString("PASS");
				String name = rs.getString("NAME");
				User user = new User(id, pass,name);
				userList.add(user);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}finally{
			//データベース切断
			if(conn != null) {
				try{
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return userList;
	}

	public boolean LoginDecision(User user) {
		Connection conn = null;

		try{
			//データベースへ接続
			conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS);

			//SQLを実行し、IDを検索
			Statement stmt = conn.createStatement();
			String sql = "SELECT ID,PASS FROM USER WHERE USER.ID = " + user.getId();
			ResultSet rs = stmt.executeQuery(sql);

			

		}catch(SQLException e) {

		}
		return true;
	}
}
