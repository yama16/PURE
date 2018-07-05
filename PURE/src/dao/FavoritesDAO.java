package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author furukawa
 *
 */
public class FavoritesDAO {

    private final String JDBC_URL = "jdbc:mysql://localhost:3306/pure?useUnicode=true&characterEncoding=utf8";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        }
    }

    /**
     * お気に入りを追加するprivateメソッド。
     * @param accountId お気に入りをしたアカウントのID
     * @param bulletinBoardId お気に入りした掲示板のID
     * @param conn コネクション
     * @return お気に入りに追加できれば1。できなければ0。
     * @throws SQLException
     */
    private int create(String accountId, int bulletinBoardId, Connection conn) throws SQLException{
   		String sql = "INSERT INTO favorites(account_id, bulletin_board_id) VALUES(?, ?);";

   		PreparedStatement pStmt = conn.prepareStatement(sql);
   		pStmt.setString(1, accountId);
   		pStmt.setInt(2, bulletinBoardId);

   		int result = pStmt.executeUpdate();
   		if(result != 1){
   			return 0;
   		}
   		return 1;
    }

    /**
     * お気に入りを削除するprivateメソッド。
     * @param accountId お気に入りを削除するアカウントのID
     * @param bulletinBoardId 削除するお気に入りの掲示板のID
     * @param conn コネクション
     * @return お気に入りから削除できれば-1。できなければ0。
     * @throws SQLException
     */
    private int delete(String accountId, int bulletinBoardId, Connection conn) throws SQLException{
    	String sql = "DELETE FROM favorites WHERE account_id=? AND bulletin_board_id=?;";

    	PreparedStatement pStmt = conn.prepareStatement(sql);
    	pStmt.setString(1, accountId);
    	pStmt.setInt(2, bulletinBoardId);

    	int result = pStmt.executeUpdate();
    	if(result != 1){
    		return 0;
    	}
    	return -1;
    }

    /**
     * 引数のアカウントIDと掲示板IDからお気に入りを探すメソッド。
     * @param accountId 探すお気に入りのアカウントID
     * @param bulletinBoardId 探すお気に入りの掲示板ID
     * @param conn コネクション
     * @return 見つかればtrue、見つからなければfalseを返す。
     * @throws SQLException
     */
    private boolean find(String accountId, int bulletinBoardId, Connection conn) throws SQLException{
    	String sql = "SELECT * FROM favorites WHERE account_id=? AND bulletin_board_id=?;";

    	PreparedStatement pStmt = conn.prepareStatement(sql);
    	pStmt.setString(1, accountId);
    	pStmt.setInt(2, bulletinBoardId);

    	ResultSet resultSet = pStmt.executeQuery();

    	return resultSet.next();
    }

    /**
     * findメソッドで引数のお気に入りがあるか調べ、あればdeleteメソッドで削除し、なければcreateメソッドで追加する。
     * @param accountId 調べるお気に入りのアカウントID
     * @param bulletinBoardId 調べるお気に入りの掲示板ID
     * @return 追加したら1、削除したら-1、エラー等で処理を失敗したら0を返す。
     */
    public int toggle(String accountId, int bulletinBoardId){
    	Connection conn = null;
    	int result;
    	try{
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    		conn.setAutoCommit(false);

    		if(find(accountId, bulletinBoardId, conn)){
    			result = delete(accountId, bulletinBoardId, conn);
    		}else{
    			result = create(accountId, bulletinBoardId, conn);
    		}
    		if(result == 0){
    			conn.rollback();
    			return 0;
    		}else{
    			BulletinBoardsDAO dao = new BulletinBoardsDAO();
    			result = dao.updateFavorite(bulletinBoardId, result, conn);
    		}
    		if(result == 0){
    			conn.rollback();
    			return 0;
    		}
    		conn.commit();

    	} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}finally{
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return 0;
				}
			}
		}
    	return result;
    }

    /**
     *
     * @param accountId
     * @param bulletinBoardId
     * @return
     */
    public boolean find(String accountId, int bulletinBoardId){
    	Connection conn = null;
    	try{
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

    		String sql = "SELECT * FROM favorites WHERE account_id = ? AND bulletin_board_id;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, accountId);
    		pStmt.setInt(2, bulletinBoardId);

    		ResultSet resultSet = pStmt.executeQuery();
    		if(!resultSet.next()){
    			return false;
    		}
    	}catch(SQLException e){
    		e.printStackTrace();
    		return false;
    	}finally{
    		if(conn != null){
    			try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
    		}
    	}

    	return true;
    }

    /**
     *
     * @param accountId
     * @return
     */
    public List<Integer> findByAccountId(String accountId){
    	List<Integer> favoriteList = new ArrayList<>();
    	Connection conn = null;
    	try{
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

    		String sql = "SELECT bulletin_board_id FROM favorites WHERE account_id = ?;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, accountId);

    		ResultSet resultSet = pStmt.executeQuery();
    		while(resultSet.next()){
    			favoriteList.add(resultSet.getInt("bulletin_board_id"));
    		}
    	}catch(SQLException e){
    		e.printStackTrace();
    		return null;
    	}finally{
    		if(conn != null){
    			try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
    		}
    	}
    	return favoriteList;
    }

}
