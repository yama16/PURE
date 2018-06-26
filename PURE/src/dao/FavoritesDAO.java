package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		if(find(accountId, bulletinBoardId, conn)){

    			return delete(accountId, bulletinBoardId, conn);

    		}else{

    			return create(accountId, bulletinBoardId, conn);

    		}

    	} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
    }

}
