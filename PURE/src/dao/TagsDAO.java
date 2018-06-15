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
public class TagsDAO {

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
     * 掲示板のIDからタグを検索するメソッド。
     * @param bulletinBoardId 検索する掲示板のID
     * @return 検索したタグのリストを返す。SQLExceptionをcatchするとnullを返す。
     */
    public List<String> fingByBulletinBoardId(int bulletinBoardId){
    	List<String> tagList = new ArrayList<>();
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
    		String sql = "SELECT tag FROM tags WHERE bulletin_board_id=?;";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, bulletinBoardId);
    		ResultSet resultSet = pStmt.executeQuery();
    		while(resultSet.next()){
    			tagList.add(resultSet.getString("tag"));
    		}
    	} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    	return tagList;
    }

}
