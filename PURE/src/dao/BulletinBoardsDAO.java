package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.BulletinBoard;
import model.BulletinBoardList;

/**
 * bulletin_boards（掲示板）テーブルを操作するDAO。
 * @author furukawa
 *
 */
public class BulletinBoardsDAO {

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

    		String sql = "INSERT INTO bulletin_boards(title,account_id,created_at) VALUES(?,?,COALESCE(?,CURRENT_TIMESTAMP()));";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, bulletinBoard.getTitle());
    		pStmt.setString(2, bulletinBoard.getAccountId());
    		pStmt.setTimestamp(3, bulletinBoard.getCreatedAt());

    		int result = pStmt.executeUpdate();
    		if(result != 1){
				conn.rollback();
    			return false;
    		}

    		sql = "SELECT id FROM bulletin_boards WHERE title = ?;";

    		pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, bulletinBoard.getTitle());

    		ResultSet resultSet = pStmt.executeQuery();
    		int bulletinBoardId = 0;
    		if(resultSet.next()){
    			bulletinBoardId = resultSet.getInt("id");
    		}else{
    			conn.rollback();
    			return false;
    		}

    		if(bulletinBoard.getTagList() != null){
				TagsDAO dao = new TagsDAO();
	    		if(!dao.createAll(bulletinBoardId, bulletinBoard.getTagList(), conn)){
	    			conn.rollback();
	    			return false;
	    		}
    		}

    		conn.commit();
    	} catch (SQLException e1) {
    		e1.printStackTrace();
            try {
				conn.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
            return false;
        } finally {
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

    /**
     * 掲示板を探すメソッド。
     * @param bulletinBoardId 検索する掲示板のID
     * @return 見つかったらその掲示板の情報を持ったインスタンスを返す。見つからなければnullを返す。
     */
    public BulletinBoard findById(int bulletinBoardId){
    	BulletinBoard bulletinBoard = null;

    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

			CommentsDAO commentsDAO = new CommentsDAO();
			TagsDAO tagsDAO = new TagsDAO();
    		bulletinBoard = findById(bulletinBoardId, conn);
    		bulletinBoard.setCommentList(commentsDAO.findByBulletinBoardId(bulletinBoardId, conn));
    		bulletinBoard.setTagList(tagsDAO.findByBulletinBoardId(bulletinBoardId, conn));

    	} catch(SQLException e) {
    		e.printStackTrace();
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
    public BulletinBoardList findByAccountId(String accountId){
    	BulletinBoardList list = new BulletinBoardList();
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "SELECT id, title, account_id, created_at, view_quantity, favorite_quantity FROM bulletin_boards WHERE account_id = ? ORDER BY id;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, accountId);

    		ResultSet resultSet = pStmt.executeQuery();
    		TagsDAO dao = new TagsDAO();
    		while(resultSet.next()){
    			BulletinBoard bulletinBoard = new BulletinBoard();
    			int bulletinBoardId = resultSet.getInt("id");
        		bulletinBoard.setId(bulletinBoardId);
        		bulletinBoard.setTitle(resultSet.getString("title"));
        		bulletinBoard.setAccountId(resultSet.getString("account_id"));
        		bulletinBoard.setCreatedAt(resultSet.getTimestamp("created_at"));
        		bulletinBoard.setViewQuantity(resultSet.getInt("view_quantity"));
        		bulletinBoard.setFavoriteQuantity(resultSet.getInt("favorite_quantity"));
        		bulletinBoard.setTagList(dao.findByBulletinBoardId(bulletinBoardId, conn));
    			list.add(bulletinBoard);
    		}

    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
		}

    	return list;
    }

    /**
     *
     * @param order
     * @return
     */
    public BulletinBoardList ranking(int order){
    	String order1 = "";
    	String order2 = "";
    	if(order == 1){
    		order1 = "favorite_quantity";
    		order2 = "view_quantity";
    	}else if(order == 2){
    		order1 = "view_quantity";
    		order2 = "favorite_quantity";
    	}else{
    		return null;
    	}
    	BulletinBoardList list = new BulletinBoardList();

    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "SELECT id, title, account_id, created_at, view_quantity, favorite_quantity FROM bulletin_boards ORDER BY " + order1 + " DESC, " + order2 + " DESC LIMIT 100;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);

    		ResultSet resultSet = pStmt.executeQuery();
    		TagsDAO dao = new TagsDAO();
    		while(resultSet.next()){
	    		BulletinBoard bulletinBoard = new BulletinBoard();
    			int bulletinBoardId = resultSet.getInt("id");
	        	bulletinBoard.setId(bulletinBoardId);
	        	bulletinBoard.setTitle(resultSet.getString("title"));
	        	bulletinBoard.setAccountId(resultSet.getString("account_id"));
	        	bulletinBoard.setCreatedAt(resultSet.getTimestamp("created_at"));
	        	bulletinBoard.setViewQuantity(resultSet.getInt("view_quantity"));
	        	bulletinBoard.setFavoriteQuantity(resultSet.getInt("favorite_quantity"));
	        	bulletinBoard.setTagList(dao.findByBulletinBoardId(bulletinBoardId, conn));
	    		list.add(bulletinBoard);
    		}

    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
		}

    	return list;
    }

    /**
     * 引数のキーワードで掲示板のタイトルを部分検索する。
     * 並び替え、LIMITをやらねば
     * @param keyword 検索する部分文字列
     * @return 検索結果の掲示板のリスト
     */
    public BulletinBoardList findByKeyword(String keyword){
    	BulletinBoardList list = new BulletinBoardList();
    	keyword.replace("$", "$$").replace("%", "$%").replace("_", "$_");
    	keyword = "%" + keyword + "%";

    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "SELECT id, title, account_id, created_at, view_quantity, favorite_quantity FROM bulletin_boards WHERE title LIKE ? ESCAPE '$' ORDER BY id;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, keyword);

    		ResultSet resultSet = pStmt.executeQuery();
			TagsDAO dao = new TagsDAO();
    		while(resultSet.next()){
    			BulletinBoard bulletinBoard = new BulletinBoard();
    			int bulletinBoardId = resultSet.getInt("id");
        		bulletinBoard.setId(bulletinBoardId);
        		bulletinBoard.setTitle(resultSet.getString("title"));
        		bulletinBoard.setAccountId(resultSet.getString("account_id"));
        		bulletinBoard.setCreatedAt(resultSet.getTimestamp("created_at"));
        		bulletinBoard.setViewQuantity(resultSet.getInt("view_quantity"));
        		bulletinBoard.setFavoriteQuantity(resultSet.getInt("favorite_quantity"));
        		bulletinBoard.setTagList(dao.findByBulletinBoardId(bulletinBoardId, conn));
    			list.add(bulletinBoard);
    		}

    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
		}

    	return list;
    }

    /**
     * 引数で指定したタグから掲示板を検索するメソッド。
     * 第2引数にtrueを指定すれば部分一致検索、falseを指定すれば完全一致検索になる。
     * @param keyword 検索するタグ
     * @param partial 部分一致検索ならtrue、完全一致検索ならfalseを指定。
     * @return 検索した掲示板のリスト
     */
    public BulletinBoardList findByTag(String keyword, boolean partial){
    	keyword.replace("$", "$$").replace("%", "$%").replace("_", "$_");
    	if(partial){
    		keyword = "%" + keyword + "%";
    	}
    	BulletinBoardList list = new BulletinBoardList();

    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "SELECT id, title, account_id, created_at, view_quantity, favorite_quantity FROM bulletin_boards WHERE id IN (SELECT DISTINCT bulletin_board_id FROM tags WHERE tag LIKE ? ESCAPE '$') ORDER BY id;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, keyword);

    		ResultSet resultSet = pStmt.executeQuery();
    		TagsDAO dao = new TagsDAO();
    		while(resultSet.next()){
    			BulletinBoard bulletinBoard = new BulletinBoard();
    			int bulletinBoardId = resultSet.getInt("id");
        		bulletinBoard.setId(bulletinBoardId);
        		bulletinBoard.setTitle(resultSet.getString("title"));
        		bulletinBoard.setAccountId(resultSet.getString("account_id"));
        		bulletinBoard.setCreatedAt(resultSet.getTimestamp("created_at"));
        		bulletinBoard.setViewQuantity(resultSet.getInt("view_quantity"));
        		bulletinBoard.setFavoriteQuantity(resultSet.getInt("favorite_quantity"));
        		bulletinBoard.setTagList(dao.findByBulletinBoardId(bulletinBoardId, conn));
    			list.add(bulletinBoard);
    		}

    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
		}

    	return list;
    }

    /**
     * アカウントIDからお気に入りの掲示板IDを検索し、お気に入りの掲示板のリストを返すメソッド。
     * @param accountId 検索するアカウントのID。
     * @return 検索が成功すればお気に入りのリストを返す。失敗すればnullを返す。
     */
    public BulletinBoardList findFavorite(String accountId){
    	BulletinBoardList favoriteList = new BulletinBoardList();
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql ="SELECT id, title, account_id, created_at, view_quantity, favorite_quantity FROM bulletin_boards WHERE id IN (SELECT id FROM favorites WHERE account_id = ?) ORDER BY id;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, accountId);

    		ResultSet resultSet = pStmt.executeQuery();
    		TagsDAO dao = new TagsDAO();
    		while(resultSet.next()){
   				BulletinBoard bulletinBoard = new BulletinBoard();
    			int bulletinBoardId = resultSet.getInt("id");
       			bulletinBoard.setId(bulletinBoardId);
       			bulletinBoard.setTitle(resultSet.getString("title"));
       			bulletinBoard.setAccountId(resultSet.getString("account_id"));
       			bulletinBoard.setCreatedAt(resultSet.getTimestamp("created_at"));
        		bulletinBoard.setViewQuantity(resultSet.getInt("view_quantity"));
        		bulletinBoard.setFavoriteQuantity(resultSet.getInt("favorite_quantity"));
        		bulletinBoard.setTagList(dao.findByBulletinBoardId(bulletinBoardId, conn));
   				favoriteList.add(bulletinBoard);
    		}

    	} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

    	return favoriteList;
    }

    /**
     * 引数で指定した掲示板のタイトルが既に使われているか調べるメソッド。
     * @param title 調べる掲示板のタイトル
     * @return 使われていればfalse、使われていなければtrueを返す。
     */
    public boolean titleIsUsable(String title){
    	Connection conn = null;
    	try{
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

    		String sql = "SELECT * FROM bulletin_boards WHERE title = ?;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, title);

    		ResultSet resultSet = pStmt.executeQuery();
    		if(resultSet.next()){
    			return false;
    		}

    	}catch(SQLException e){
    		e.printStackTrace();
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

    /**
     * 新着の掲示板をリストで取得するメソッド。
     * @return 新着の掲示板のリスト
     */
    public BulletinBoardList getNewBulletinBoard(){
    	BulletinBoardList list = new BulletinBoardList();
    	Connection conn = null;
    	try{
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

    		String sql = "SELECT id, title, account_id, created_at, view_quantity, favorite_quantity FROM bulletin_boards ORDER BY id DESC LIMIT 10;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);

    		ResultSet resultSet = pStmt.executeQuery();
    		TagsDAO dao = new TagsDAO();
    		while(resultSet.next()){
   				BulletinBoard bulletinBoard = new BulletinBoard();
    			int bulletinBoardId = resultSet.getInt("id");
       			bulletinBoard.setId(bulletinBoardId);
       			bulletinBoard.setTitle(resultSet.getString("title"));
       			bulletinBoard.setAccountId(resultSet.getString("account_id"));
       			bulletinBoard.setCreatedAt(resultSet.getTimestamp("created_at"));
        		bulletinBoard.setViewQuantity(resultSet.getInt("view_quantity"));
        		bulletinBoard.setFavoriteQuantity(resultSet.getInt("favorite_quantity"));
        		bulletinBoard.setTagList(dao.findByBulletinBoardId(bulletinBoardId, conn));
   				list.add(bulletinBoard);
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
    	return list;
    }

    protected int updateFavorite(int bulletinBoardId, int update, Connection conn) throws SQLException{

    	String sql = "UPDATE bulletin_boards SET favorite_quantity = favorite_quantity + ? WHERE id = ?;";

    	PreparedStatement pStmt = conn.prepareStatement(sql);
    	pStmt.setInt(1, update);
    	pStmt.setInt(2, bulletinBoardId);

    	int result = pStmt.executeUpdate();

    	if(result != 1){
    		return 0;
    	}
    	return update;
    }

    /**
     *
     * @param id
     * @param conn
     * @return
     * @throws SQLException
     */
    protected BulletinBoard findById(int id, Connection conn) throws SQLException{
    	BulletinBoard bulletinBoard = null;

    	String sql = "SELECT * FROM bulletin_boards WHERE id = ?;";

    	PreparedStatement pStmt = conn.prepareStatement(sql);
    	pStmt.setInt(1, id);

    	ResultSet resultSet = pStmt.executeQuery();
    	if(resultSet.next()){
    		bulletinBoard = new BulletinBoard();
    		bulletinBoard.setId(resultSet.getInt("id"));
    		bulletinBoard.setTitle(resultSet.getString("title"));
    		bulletinBoard.setAccountId(resultSet.getString("account_id"));
    		bulletinBoard.setCreatedAt(resultSet.getTimestamp("created_at"));
    		bulletinBoard.setViewQuantity(resultSet.getInt("view_quantity"));
    		bulletinBoard.setFavoriteQuantity(resultSet.getInt("favorite_quantity"));
    	}

    	return bulletinBoard;
    }

}
