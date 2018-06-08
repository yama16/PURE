
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
import model.Login;

/**
 * Accountsテーブルを操作するDAO。
 *
 * @author furukawa
 */
public class AccountsDAO {

    private final String DRIVER_NAME = "org.h2.Driver";
    private final String JDBC_URL = "jdbc:h2:C:/data/pure";
    private final String DB_USER = "sa";
    private final String DB_PASS = "";

    static{
        try {
            Class.forName("org.h2.Driver");
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
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            String sql = "SELECT id FROM accounts WHERE id = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, id);
            ResultSet resultSet = pStmt.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * Accountsテーブルに登録するメソッド。
     * 登録したいアカウント情報を持ったAccountインスタンスを引数で渡す。
     * 登録できればtrueを返す。できなければfalseを返す。
     * @param account 本登録するアカウント
     * @return 本登録できればtrue。できなければfalse。
     */
    public boolean create(Account account){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            String sql = "INSERT INTO accounts (id, nickname, password, created_at, updated_at) VALUES (?,?,?,?,?)";
            PreparedStatement pStmt = connection.prepareStatement(sql);
            pStmt.setString(1, account.getId());
            pStmt.setString(2, account.getNickname());
            pStmt.setString(3, account.getPassword());
            pStmt.setTimestamp(4, account.getCreatedAt());
            pStmt.setTimestamp(5, account.getUpdatedAt());
            int result = pStmt.executeUpdate();
            if(result != 1){
                return false;
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            return false;
        }finally{
            if(connection != null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
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
        Connection conn = null;
        Account account = null;
        try{
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            String sql = "SELECT id, nickname, password, created_at, updated_at, is_deleted FROM accounts WHERE id = ? AND password = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, login.getId());
            pStmt.setString(2, login.getPassword());

            ResultSet resultSet = pStmt.executeQuery();

            if(resultSet.next() && !resultSet.getBoolean("is_deleted")){
                String id = resultSet.getString("id");
                String nickname = resultSet.getString("nickname");
                String password = resultSet.getString("password");
                Timestamp created_at = resultSet.getTimestamp("created_at");
                Timestamp updated_at = resultSet.getTimestamp("updated_at");

                account = new Account(id, nickname, password, created_at, updated_at);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                    return null;
                }
            }
        }
        return account;
    }

    /**
     * Accountsテーブルからアカウントを削除するメソッド。
     * 削除したいアカウントのAccountインスタンスを引数で渡す。
     * 削除できればtrueを返す。できなければfalseを返す。
     * @param account 削除するアカウント
     * @return 削除できればtrue。できなければfalse。
     */
    public boolean delete(Account account){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            String sql = "UPDATE Accounts SET is_deleted = TRUE WHERE id = ?;";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, account.getId());
            int result = pStmt.executeUpdate();
            if(result != 1){
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AccountsDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            String sql = "UPDATE accounts SET id = ?, nickname = ?, password = ?, updated_at = ? WHERE id = ?;";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, account.getId());
            pStmt.setString(2, account.getNickname());
            pStmt.setString(3, account.getPassword());
            pStmt.setTimestamp(4, account.getUpdatedAt());
            pStmt.setString(5, id);
            int result = pStmt.executeUpdate();
            if(result != 1){
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AccountsDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return true;
    }

}
