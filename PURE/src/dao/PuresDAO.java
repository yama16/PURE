package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * puresテーブルにアクセスするDAO。
 * @author furukawa
 *
 */
public class PuresDAO {

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
     * PUREを追加するメソッド。
     * @param accountId PUREしたアカウントのID。
     * @param commentId PUREしたコメントのID。
     * @param bulletinBoardId PUREしたコメントがある掲示板のID。
     * @param conn コネクション
     * @return 追加できれば1を返す。できなければ0を返す。
     * @throws SQLException
     */
    private int create(String accountId, int commentId, int bulletinBoardId, Connection conn) throws SQLException{
    	String sql = "INSERT INTO pures(account_id, comment_id, bulletin_board_id) VALUES(?, ?, ?);";

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
     * 追加または削除できればCommentsDAOのupdatePureでPURE数を更新する。
     * 追加できれば1を返し、削除できれば-1を返し、エラー等で何もできなければ0を返す。
     * @param accountId PUREボタンを押したアカウントのID
     * @param commentId PUREボタンを押されたコメントのID
     * @param bulletinBoardId PUREボタンを押したコメントのある掲示板のID
     * @return 追加できれば1を返し、削除できれば-1を返し、エラー等で何もできなければ0を返す。
     */
    public int toggle(String accountId, int commentId, int bulletinBoardId){
    	Connection conn = null;
    	int result;
    	try{
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    		conn.setAutoCommit(false);

    		if(find(accountId, commentId, bulletinBoardId, conn)){
    			result = delete(accountId, commentId, bulletinBoardId, conn);
    		}else{
    			result = create(accountId, commentId, bulletinBoardId, conn);
    		}
    		if(result == 0){
    			conn.rollback();
    			return 0;
    		}else{
    			CommentsDAO dao = new CommentsDAO();
    			result = dao.updatePure(commentId, bulletinBoardId, result, conn);
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
		} finally {
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
    	return result;
    }

    /**
     * 引数のアカウントIDと掲示板IDから、そのアカウントがその掲示板でPUREしているコメントのIDのリストを返すメソッド。
     * @param accountId 調べるアカウントのID
     * @param bulletinBoardId 調べる掲示板のID
     * @return 引数の掲示板でPUREしているコメントのIDのリスト。エラーが起きればnullを返す。
     */
    public List<Integer> getPureCommentList(String accountId, int bulletinBoardId){
    	Connection conn = null;
    	List<Integer> pureCommentList = new ArrayList<>();
    	try{
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

    		String sql = "SELECT comment_id FROM pures WHERE account_id = ? AND bulletin_board_id = ?;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, accountId);
    		pStmt.setInt(2, bulletinBoardId);

    		ResultSet resultSet = pStmt.executeQuery();
    		while(resultSet.next()){
    			pureCommentList.add(resultSet.getInt("comment_id"));
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

    	return pureCommentList;
    }

}
