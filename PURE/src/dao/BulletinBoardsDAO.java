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
        	Logger.getLogger(BulletinBoardsDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * 掲示板をbulletin_boardsテーブルに登録するメソッド。
     * タグの追加はTagsDAOのcreateに任せる。
     * @param bulletinBoard 作成する掲示板
     * @return 作成できればtrueを返す。できなければfalseを返す。
     */
    public boolean create(BulletinBoard bulletinBoard){
    	Connection conn = null;
    	try{
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    		conn.setAutoCommit(false);

    		String sql = "INSERT INTO bulletin_boards(id,title,account_id,created_at,view_quantity,favorite_quantity) VALUES(?,?,?,?,?,?);";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, bulletinBoard.getId());
    		pStmt.setString(2, bulletinBoard.getTitle());
    		pStmt.setString(3, bulletinBoard.getAccountId());
    		pStmt.setTimestamp(4, bulletinBoard.getCreatedAt());
    		pStmt.setInt(5, bulletinBoard.getViewQuantity());
    		pStmt.setInt(6, bulletinBoard.getFavoriteQuantity());
    		int result = pStmt.executeUpdate();
    		if(result != 1){
				conn.rollback();
    			return false;
    		}

			TagsDAO dao = new TagsDAO();
    		for(String tag : bulletinBoard.getTagList()){
    			if(!dao.create(bulletinBoard.getId(), tag, conn)){
    				conn.rollback();
    				return false;
    			}
    		}

    		conn.commit();
    	} catch (SQLException e1) {
    		Logger.getLogger(BulletinBoardsDAO.class.getName()).log(Level.SEVERE, null, e1);
            try {
				conn.rollback();
			} catch (SQLException e2) {
				Logger.getLogger(BulletinBoardsDAO.class.getName()).log(Level.SEVERE, null, e2);
			}
            return false;
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                	Logger.getLogger(BulletinBoardsDAO.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    	return true;
    }

    /**
     * 掲示板を探すメソッド。
     * @param findId 検索する掲示板のID
     * @return 見つかったらその掲示板の情報を持ったインスタンスを返す。見つからなければnullを返す。
     */
    public BulletinBoard findById(int bulletinBoardId){
    	BulletinBoard bulletinBoard = null;
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
    		String sql = "SELECT * FROM bulletin_boards WHERE id=?";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, bulletinBoardId);
    		ResultSet resultSet = pStmt.executeQuery(sql);
    		if(resultSet.next()){
    			bulletinBoard = new BulletinBoard();
    			bulletinBoard.setId(resultSet.getInt("id"));
    			bulletinBoard.setTitle(resultSet.getString("title"));
    			bulletinBoard.setAccountId(resultSet.getString("accont_id"));
    			bulletinBoard.setCreatedAt(resultSet.getTimestamp("created_at"));
    			bulletinBoard.setViewQuantity(resultSet.getInt("view_quantity"));
    			bulletinBoard.setFavoriteQuantity(resultSet.getInt("favorite_quantity"));
    		}
    	} catch(SQLException e) {
    		Logger.getLogger(BulletinBoardsDAO.class.getName()).log(Level.SEVERE, null, e);
    		return null;
    	}
    	return bulletinBoard;
    }

    /**
     * 引数のアカウントの作った掲示板のリストを返す。
     * それぞれの掲示板は掲示板情報とその掲示板のタグを持つ。コメントは持たない。
     * @param accountId 検索するアカウントのID
     * @return 検索した掲示板のリスト。取得に失敗したらnullを返す。
     */
    public List<BulletinBoard> findByAccountId(String accountId){
    	List<BulletinBoard> list = new ArrayList<>();
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "SELECT * FROM bulletin_boards WHERE account_id=?;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, accountId);

    		ResultSet resultSet = pStmt.executeQuery();
    		while(resultSet.next()){
    			BulletinBoard bulletinBoard = new BulletinBoard();
    			int bulletinBoardId = resultSet.getInt("id");
    			bulletinBoard.setId(bulletinBoardId);
    			bulletinBoard.setTitle(resultSet.getString("title"));
    			bulletinBoard.setAccountId(resultSet.getString("accont_id"));
    			bulletinBoard.setCreatedAt(resultSet.getTimestamp("created_at"));
    			bulletinBoard.setViewQuantity(resultSet.getInt("view_quantity"));
    			bulletinBoard.setFavoriteQuantity(resultSet.getInt("favorite_quantity"));
    			TagsDAO tagsDAO = new TagsDAO();
    			bulletinBoard.setTagList(tagsDAO.findByBulletinBoardId(bulletinBoardId, conn));
    			list.add(bulletinBoard);
    		}

    	} catch (SQLException e) {
    		Logger.getLogger(BulletinBoardsDAO.class.getName()).log(Level.SEVERE, null, e);
    		return null;
		}

    	return list;
    }

    /*public List<BulletinBoard> ranking(){

    }*/

}
