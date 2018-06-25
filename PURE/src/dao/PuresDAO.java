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
     * @param conn コネクション
     * @return 追加できれば1を返す。できなければ0を返す。
     * @throws SQLException
     */
    private int create(String accountId, int commentId, int bulletinBoardId, Connection conn) throws SQLException{
    	String sql = "INSERT INTO pures VALUES(?, ?, ?)";

    	PreparedStatement pStmt = conn.prepareStatement(sql);
    	pStmt.setString(1, accountId);
    	pStmt.setInt(2, commentId);
    	pStmt.setInt(3, bulletinBoardId);

    	int result = pStmt.executeUpdate();
    	if(result != 1){
    		return 0;
    	}
    	return 1;
    }

    /**
     * PUREを削除するメソッド。
     * @param accountId 取消したアカウントのID。
     * @param commentId 取消したコメントのID。
     * @param bulletinBoardId 取消したコメントがある掲示板のID。
     * @param conn コネクション
     * @return 削除できれば-1を返す。できなければ0を返す。
     * @throws SQLException
     */
    private int delete(String accountId, int commentId, int bulletinBoardId, Connection conn) throws SQLException{
    	String sql = "DELETE FROM pures WHERE account_id=? AND comment_id=? AND bulletin_board_id=?;";

    	PreparedStatement pStmt = conn.prepareStatement(sql);
    	pStmt.setString(1, accountId);
    	pStmt.setInt(2, commentId);
    	pStmt.setInt(3, bulletinBoardId);

    	int result = pStmt.executeUpdate();
    	if(result != 1){
    		return 0;
    	}
    	return -1;
    }

    /**
     * PUREを検索するメソッド。
     * @param accountId 探すPUREのアカウントのID
     * @param commentId 探すPUREのコメントのID
     * @param bulletinBoardId 探すPUREの掲示板のID
     * @param conn コネクション
     * @return 見つかったらtrueを返す。見つからなかったらfalseを返す。
     * @throws SQLException
     */
    private boolean find(String accountId, int commentId, int bulletinBoardId, Connection conn) throws SQLException{
    	String sql = "SELECT * FROM pures WHERE account_id=? AND comment_id=? AND bulletin_board_id=?;";

    	PreparedStatement pStmt = conn.prepareStatement(sql);
    	pStmt.setString(1, accountId);
    	pStmt.setInt(2, commentId);
    	pStmt.setInt(3, bulletinBoardId);

    	ResultSet resultSet = pStmt.executeQuery();
    	return resultSet.next();
    }

    /**
     * PUREがあるかをprivateメソッドのfindで検索して、あればprivateメソッドのdeleteで削除、なければprivateメソッドのcreateで追加する。
     * 追加できれば1を返し、削除できれば-1を返し、エラー等で何もできなければ0を返す。
     * @param accountId PUREボタンを押したアカウントのID
     * @param commentId PUREボタンを押されたコメントのID
     * @param bulletinBoardId PUREボタンを押したコメントのある掲示板のID
     * @return 追加できれば1を返し、削除できれば-1を返し、エラー等で何もできなければ0を返す。
     */
    public int toggle(String accountId, int commentId, int bulletinBoardId){
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		if(find(accountId, commentId, bulletinBoardId, conn)){

    			return delete(accountId, commentId, bulletinBoardId, conn);

    		}else{

    			return create(accountId, commentId, bulletinBoardId, conn);

    		}

    	} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
    }

}
