package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.TagList;

/**
 * tagsテーブルを操作するDAO
 * @author furukawa
 */
public class TagsDAO {

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
     * daoパッケージのクラスからしかアクセスできないタグを追加するメソッド。
     * 呼び出されるときConnectionを渡す
     * @param bulletinBoardId 追加するタグがついている掲示板のID
     * @param tagList 追加するタグのリスト
     * @param conn データベースとのコネクション
     * @return 追加できればtrue。できなければfalse。
     * @throws SQLException
     */
    protected boolean createAll(int bulletinBoardId, TagList tagList, Connection conn) throws SQLException{
    	String sql = "INSERT INTO tags(bulletin_board_id, tag) VALUES(?, ?);";

    	PreparedStatement pStmt = conn.prepareStatement(sql);
    	pStmt.setInt(1, bulletinBoardId);
    	for(int i = 0; i < tagList.size(); i++){
	    	pStmt.setString(2, tagList.get(i));
	    	int result = pStmt.executeUpdate();
	    	if(result != 1){
	    		return false;
	    	}
    	}

    	return true;
    }

    /**
     * 引数で指定した掲示板IDの掲示板のタグを全て削除するメソッド。
     * @param bulletinBoardId 削除するタグの掲示板ID
     * @return 成功すればtrue、失敗すればfalseを返す。
     * @throws SQLException
     */
    protected void deleteByBulletinBoardId(int bulletinBoardId, Connection conn) throws SQLException{
    	String sql = "DELETE FROM tags WHERE bulletin_board_id=?;";

    	PreparedStatement pStmt = conn.prepareStatement(sql);
    	pStmt.setInt(1, bulletinBoardId);

    	pStmt.executeUpdate();
    }

    /**
     * 引数で指定した掲示板のIDのタグをリストで返す。
     * @param bulletinBoardId
     * @param conn
     * @return
     * @throws SQLException
     */
    protected TagList findByBulletinBoardId(int bulletinBoardId, Connection conn) throws SQLException{
    	TagList tagList = new TagList();

    	String sql = "SELECT tag FROM tags WHERE bulletin_board_id=?;";

    	PreparedStatement pStmt = conn.prepareStatement(sql);
    	pStmt.setInt(1, bulletinBoardId);

    	ResultSet resultSet = pStmt.executeQuery();
    	while(resultSet.next()){
    		tagList.add(resultSet.getString("tag"));
    	}

    	return tagList;
    }

    /**
     *
     * @param bulletinBoardId
     * @param tagList
     * @return
     */
    public boolean update(int bulletinBoardId, TagList tagList){
    	Connection conn = null;
    	try{
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    		conn.setAutoCommit(false);

    		deleteByBulletinBoardId(bulletinBoardId, conn);

    		if(!createAll(bulletinBoardId, tagList, conn)){
    			conn.rollback();
    			return false;
    		}
    		conn.commit();

    	}catch(SQLException e){
    		e.printStackTrace();
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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

}
