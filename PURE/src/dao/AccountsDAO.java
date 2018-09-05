
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Account;
import model.Hash;
import model.Login;

/**
 * Accountsテーブルを操作するDAO。
 *
 * @author furukawa
 */
public class AccountsDAO {

    private final String JDBC_URL = "jdbc:mysql://localhost:3306/pure?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true";
    private final String DB_USER = "ojo";
    private final String DB_PASS = "mjmjm@aster007P";

    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        }
    }

    /**
     * 指定したidが、既にAccountsテーブルに登録されているか調べるメソッド。
     * 既に登録されているか調べたいIDをString型で引数で渡す。
     * 既に登録されていればtrueを返す。されていなければfalseを返す。
     * @param id 登録されているか調べるid
     * @return idが既に登録されていればtrue。されていなければfalse。
     */
    public boolean isUsable(String id){
        try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
            String sql = "SELECT id FROM accounts WHERE id = ?;";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, id);
            ResultSet resultSet = pStmt.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            return true;
        }
        return false;
    }

    /**
     * Accountsテーブルに登録するメソッド。
     * 登録したいアカウント情報を持ったAccountインスタンスを引数で渡す。
     * 登録できればtrueを返す。できなければfalseを返す。
     * @param account 登録するアカウント
     * @return 登録できればtrue。できなければfalse。
     */
    public boolean create(Account account){
    	Hash hash = new Hash();
    	byte[] salt = hash.createSalt();
    	if(salt == null){
    		return false;
    	}
    	String hashPass = hash.getHash(account.getPassword(), salt);
    	if(hashPass == null){
    		return false;
    	}
    	String hexSalt = hash.toHex(salt);
    	if(hexSalt == null){
    		return false;
    	}
    	account.setPassword(hashPass);
    	account.setSalt(hexSalt);
        try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
            String sql = "INSERT INTO accounts (id, nickname, password, salt, created_at, updated_at, is_deleted) VALUES (?, ?, ?, ?, COALESCE(?, CURRENT_TIMESTAMP()), COALESCE(?, CURRENT_TIMESTAMP()), ?);";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, account.getId());
            pStmt.setString(2, account.getNickname());
            pStmt.setString(3, account.getPassword());
            pStmt.setString(4, account.getSalt());
            pStmt.setTimestamp(5, account.getCreatedAt());
            pStmt.setTimestamp(6, account.getUpdatedAt());
            pStmt.setBoolean(7, false);
            int result = pStmt.executeUpdate();
            if(result != 1){
                return false;
            }
        } catch (SQLException e) {
        	Logger.getLogger(AccountsDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        return true;
    }

    /**
     * Accountsテーブルからログイン情報をもとにアカウントを探すメソッド。
     * ログイン情報を持ったLoginインスタンスを引数で渡す。
     * アカウントがテーブルから見つかればそのアカウントを返す。見つからなければnullを返す。
     * @param login ログイン情報
     * @return アカウントが見つかればそのアカウントを返す。見つからなければnullを返す。
     */
    public Account findByLogin(Login login){
        Account account = null;
        Hash hash = new Hash();

        try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

        	String findSalt = getSaltById(login.getId(), conn);
        	if(findSalt == null){
        		return null;
        	}
        	String hashPass = hash.getHash(login.getPassword(), findSalt);
        	if(hashPass == null){
        		return null;
        	}

            String sql = "SELECT id, nickname, password, salt, created_at, updated_at, is_deleted FROM accounts WHERE id = ? AND password = ?;";

            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, login.getId());
            pStmt.setString(2, hashPass);

            ResultSet resultSet = pStmt.executeQuery();
            if(resultSet.next() && !resultSet.getBoolean("is_deleted")){
                String id = resultSet.getString("id");
                String nickname = resultSet.getString("nickname");
                String password = resultSet.getString("password");
                String salt = resultSet.getString("salt");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                Timestamp updatedAt = resultSet.getTimestamp("updated_at");
                boolean isDeleted = resultSet.getBoolean("is_deleted");
                account = new Account(id, nickname, password, salt, createdAt, updatedAt, isDeleted);
            }

        } catch (SQLException e) {
        	e.printStackTrace();
            return null;
        }

        return account;
    }

    /**
     * Accountsテーブルからアカウントを削除するメソッド。
     * 削除したいアカウントのAccountのIDを引数で渡す。
     * 削除できればtrueを返す。できなければfalseを返す。
     * @param accountId 削除するアカウントのID
     * @return 削除できればtrue。できなければfalse。
     */
    public boolean delete(String accountId){

        try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

            String sql = "UPDATE Accounts SET is_deleted = TRUE WHERE id = ?;";

            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, accountId);

            int result = pStmt.executeUpdate();
            if(result != 1){
                return false;
            }

        } catch (SQLException e) {
        	e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * アカウント情報を更新するメソッド。
     * 更新前のアカウントIDと更新したいAccountインスタンスを渡す。
     * 更新できればtrueを返す。できなければfalseを返す。
     * @param id 更新したいアカウントのアカウントID
     * @param account 更新後のアカウント
     * @return 更新できればtrue。できなければfalse。
     */
    public boolean update(String id, Account account){
    	Hash hash = new Hash();

        try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

        	String findSalt = getSaltById(id, conn);
        	if(findSalt == null){
        		return false;
        	}
        	String hashPass = hash.getHash(account.getPassword(), findSalt);
        	if(hashPass == null){
        		return false;
        	}

            String sql = "UPDATE accounts SET nickname = ?, password = ?, updated_at = ? WHERE id = ?;";

            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, account.getNickname());
            pStmt.setString(2, hashPass);
            pStmt.setTimestamp(3, account.getUpdatedAt());
            pStmt.setString(4, id);

            int result = pStmt.executeUpdate();
            if(result != 1){
                return false;
            }

        } catch (SQLException e) {
        	e.printStackTrace();
            return false;
        }

        return true;
    }

    protected String getSaltById(String id, Connection conn) throws SQLException{
    	String sql = "SELECT salt FROM accounts WHERE id = ?;";

    	PreparedStatement pStmt = conn.prepareStatement(sql);
    	pStmt.setString(1, id);

    	ResultSet resultSet = pStmt.executeQuery();
    	if(!resultSet.next()){
    		return null;
    	}else{
    		return resultSet.getString("salt");
    	}
    }
    public String getSaltById(String id) {
    	String salt = null;
    	Connection conn = null;
    	try {
			conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

			String sql = "SELECT salt FROM accounts WHERE id = ?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, id);

			ResultSet rs = pStmt.executeQuery();
			if(rs.next()){
				salt = rs.getString("salt");
			}

		} catch (SQLException e) {
			return null;
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					return null;
				}
			}
		}
    	return salt;
    }

}
