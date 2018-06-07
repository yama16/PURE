package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.BulletinBoard;

/**
 * bulletin_boards（掲示板）テーブルを操作するDAO。
 * @author furukawa
 *
 */
public class BulletinBoardsDAO {

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
     * 掲示板をbulletin_boardsテーブルに登録するメソッド。
     * @param bulletinBoard 作成する掲示板
     * @return 作成できればtrueを返す。できなければfalseを返す。
     */
    public boolean create(BulletinBoard bulletinBoard){
    	Connection conn = null;
    	try{
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

    		String sql = "INSERT INTO bulletin_boards(id,title,account_id,created_at,updated_at,view_quantity,pure_quantity) VALUES(?,?,?,?,?,?,?);";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, bulletinBoard.getId());
    		pStmt.setString(2, bulletinBoard.getTitle());
    		pStmt.setString(3, bulletinBoard.getAccountId());
    		pStmt.setTimestamp(4, bulletinBoard.getCreatedAt());
    		pStmt.setTimestamp(5, bulletinBoard.getUpdatedAt());
    		pStmt.setInt(6, bulletinBoard.getViewQuantity());
    		pStmt.setInt(7, bulletinBoard.getPureQuantity());
    		int result = pStmt.executeUpdate();
    		if(result != 1){
    			return false;
    		}
    	} catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
    	return true;
    }

}
