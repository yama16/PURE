package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.BulletinBoard;

/**
 *
 * @author furukawa
 *
 */
public class FavoritesDAO {

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
     * お気に入りを追加するメソッド。
     * @param accountId お気に入りをしたアカウントのID
     * @param bulletinBoardId お気に入りした掲示板のID
     * @return お気に入りに追加できればtrue。できなければfalse。
     */
    public boolean create(String accountId, int bulletinBoardId){
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "INSERT INTO favorites(account_id, bulletin_board_id) VALUES(?, ?);";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, accountId);
    		pStmt.setInt(2, bulletinBoardId);

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
     * お気に入りを削除するメソッド。
     * @param accountId お気に入りを削除するアカウントのID
     * @param bulletinBoardId 削除するお気に入りの掲示板のID
     * @return 削除できればtrue。できなければfalse。
     */
    public boolean delete(String accountId, int bulletinBoardId){
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "DELETE FROM favorites WHERE account_id=? AND bulletin_board_id=?;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, accountId);
    		pStmt.setInt(2, bulletinBoardId);

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
     * アカウントIDからお気に入りの掲示板IDを検索するメソッド。
     * @param accountId 検索するアカウントのID。
     * @return 検索が成功すればお気に入りのリストを返す。失敗すればnullを返す。
     */
    public List<BulletinBoard> findByAccountId(String accountId){
    	List<BulletinBoard> favoriteList = new ArrayList<>();
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql ="SELECT f.bulletin_board_id, b.title, f.account_id, b.created_at, b.view_quantity FROM favorites AS f LEFT OUTER JOIN bulletin_boards AS b ON f.bulletin_board_id = b.id WHERE f.account_id=?;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, accountId);

    		ResultSet resultSet = pStmt.executeQuery();
    		while(resultSet.next()){
    			BulletinBoard bulletinBoard = new BulletinBoard();
    			bulletinBoard.setId(resultSet.getInt("bulletin_board_id"));
    			bulletinBoard.setAccountId(resultSet.getString("account_id"));
    			bulletinBoard.setTitle(resultSet.getString("title"));
    			favoriteList.add(bulletinBoard);
    		}

    	} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

    	return favoriteList;
    }

}
