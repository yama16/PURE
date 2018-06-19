package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * daoパッケージのクラスからしかアクセスできないタグを追加するメソッド。
     * 呼び出されるときConnectionを渡す
     * @param bulletinBoardId 追加するタグがついている掲示板のID
     * @param tag 追加するタグ
     * @param conn データベースとのコネクション
     * @return 追加できればtrue。できなければfalse。
     * @throws SQLException
     */
    protected boolean create(int bulletinBoardId, String tag, Connection conn) throws SQLException{
    	String sql = "INSERT INTO tags(bulletin_board_id, tag) VALUES(?, ?);";

    	PreparedStatement pStmt = conn.prepareStatement(sql);
    	pStmt.setInt(1, bulletinBoardId);
    	pStmt.setString(2, tag);

    	int result = pStmt.executeUpdate();
    	if(result != 1){
    		return false;
    	}

    	return true;
    }

    /**
     * 引数で指定した掲示板IDで、引数で指定したリストの全てのタグを追加する。
     * @param bulletinBoardId 追加する掲示板のID
     * @param list 追加するタグのリスト
     * @return 成功すればtrue、失敗すればfalse。
     */
    public boolean createAll(int bulletinBoardId, List<String> list){
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "INSERT INTO tags(bulletin_board_id, tad) VALUES(?, ?);";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, bulletinBoardId);

    		for(String tag : list){
    			pStmt.setString(2, tag);
    			int result = pStmt.executeUpdate();
    			if(result != 1){
    				System.out.println("追加できてないよ");
    			}
    		}

    	} catch (SQLException e) {
    		Logger.getLogger(TagsDAO.class.getName()).log(Level.SEVERE, null, e);
    		return false;
		}
    	return true;
    }

    /**
     * 引数で指定した掲示板IDの掲示板のタグを全て削除するメソッド。
     * @param bulletinBoardId 削除するタグの掲示板ID
     * @return 成功すればtrue、失敗すればfalseを返す。
     */
    public boolean deleteByBulletinBoardId(int bulletinBoardId){

    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "DELETE FROM tags WHERE bulletin_board_id=?;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, bulletinBoardId);

    		int result = pStmt.executeUpdate();

    	} catch (SQLException e) {
    		Logger.getLogger(TagsDAO.class.getName()).log(Level.SEVERE, null, e);
    		return false;
		}

    	return true;
    }

    /**
     *
     * @param bulletinBoardId
     * @param conn
     * @return
     * @throws SQLException
     */
    protected List<String> findByBulletinBoardId(int bulletinBoardId, Connection conn) throws SQLException{
    	List<String> list = new ArrayList<>();

    	String sql = "SELECT tag FROM tags WHERE bulletin_board_id=?;";

    	PreparedStatement pStmt = conn.prepareStatement(sql);
    	pStmt.setInt(1, bulletinBoardId);

    	ResultSet resultSet = pStmt.executeQuery();
    	while(resultSet.next()){
    		list.add(resultSet.getString("tag"));
    	}

    	return list;
    }

    /**
     * 掲示板のIDからタグを検索するメソッド。
     * @param bulletinBoardId 検索する掲示板のID
     * @return 検索したタグのリストを返す。SQLExceptionをcatchするとnullを返す。
     */
    public List<String> findByBulletinBoardId(int bulletinBoardId){
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
    		Logger.getLogger(TagsDAO.class.getName()).log(Level.SEVERE, null, e);
			return null;
		}

    	return tagList;
    }

}
