package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.TagList;

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
     * 引数で指定した掲示板IDで、引数で指定したリストの全てのタグを追加する。
     * @param bulletinBoardId 追加する掲示板のID
     * @param list 追加するタグのリスト
     * @return 成功すればtrue、失敗すればfalse。
     */
    public boolean createAll(int bulletinBoardId, TagList list){
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		return createAll(bulletinBoardId, list, conn);

    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;
		}
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

    		pStmt.executeUpdate();

    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;
		}

    	return true;
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
     * 掲示板のIDからタグを検索するメソッド。
     * @param bulletinBoardId 検索する掲示板のID
     * @return 検索したタグのリストを返す。SQLExceptionをcatchするとnullを返す。
     */
    public TagList findByBulletinBoardId(int bulletinBoardId){
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		return findByBulletinBoardId(bulletinBoardId, conn);

    	} catch (SQLException e) {
    		e.printStackTrace();
			return null;
		}
    }

}
