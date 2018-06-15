package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * puresテーブルにアクセスするDAO。
 * @author furukawa
 *
 */
public class PuresDAO {

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
     * PUREを追加するメソッド。
     * @param accountId PUREしたアカウントのID。
     * @param commentId PUREしたコメントのID。
     * @param bulletinBoardId PUREしたコメントがある掲示板のID。
     * @return PUREできればtrueを返す。できなければfalseを返す。
     */
    public boolean create(String accountId, int commentId, int bulletinBoardId){
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
    		String sql = "INSERT INTO pures(*) VALUES(?,?,?)";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, accountId);
    		pStmt.setInt(2, commentId);
    		pStmt.setInt(3, bulletinBoardId);

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
     *
     * @param accountId
     * @param commentId
     * @param bulletinBoardId
     * @return
     */
    public boolean delete(String accountId, int commentId, int bulletinBoardId){
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
    		String sql = "DELETE FROM pures WHERE account_id=? AND comment_id=? AND bulletin_board_id=?;";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, accountId);
    		pStmt.setInt(2, commentId);
    		pStmt.setInt(3, bulletinBoardId);

    		int result = pStmt.executeUpdate();
    		if(result != 1){
    			return false;
    		}
    	} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
    	return true;
    }

    /**
     * PUREしているか調べるメソッド。
     * @param accountId PUREを押したアカウントのID
     * @param commentId PUREを押されたコメントのID
     * @param bulletinBoardId PUREを押されたコメントのある掲示板
     * @return テーブルにあればtrueを返す。なければfalseを返す。
     */
    public boolean find(String accountId, int commentId, int bulletinBoardId){
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
    		String sql = "SELECT * FROM pures WHERE account_id=? AND comment_id=? AND bulletin_board_id=?;";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, accountId);
    		pStmt.setInt(2, commentId);
    		pStmt.setInt(3, bulletinBoardId);

    		ResultSet resultSet = pStmt.executeQuery();
    		if(!resultSet.next()){
    			return false;
    		}
    	} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    	return true;
    }

}
