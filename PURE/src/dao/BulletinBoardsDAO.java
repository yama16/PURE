package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.BulletinBoard;
import model.BulletinBoardList;
import model.TagList;

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

    		if(bulletinBoard.getTagList() != null){
				TagsDAO dao = new TagsDAO();
	    		if(!dao.createAll(bulletinBoard.getId(), bulletinBoard.getTagList(), conn)){
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
     * @param findId 検索する掲示板のID
     * @return 見つかったらその掲示板の情報を持ったインスタンスを返す。見つからなければnullを返す。
     */
    public BulletinBoard findById(int bulletinBoardId){
    	BulletinBoard bulletinBoard = null;
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
    		String sql = "SELECT * FROM bulletin_boards WHERE id=?";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, bulletinBoardId);
    		ResultSet resultSet = pStmt.executeQuery();
			CommentsDAO commentsDAO = new CommentsDAO();
			TagsDAO tagsDAO = new TagsDAO();
    		if(resultSet.next()){
    			bulletinBoard = new BulletinBoard();
    			bulletinBoard.setId(resultSet.getInt("id"));
    			bulletinBoard.setTitle(resultSet.getString("title"));
    			bulletinBoard.setAccountId(resultSet.getString("account_id"));
    			bulletinBoard.setCreatedAt(resultSet.getTimestamp("created_at"));
    			bulletinBoard.setViewQuantity(resultSet.getInt("view_quantity"));
    			bulletinBoard.setFavoriteQuantity(resultSet.getInt("favorite_quantity"));
    			bulletinBoard.setCommentList(commentsDAO.findByBulletinBoardId(bulletinBoardId, conn));
    			bulletinBoard.setTagList(tagsDAO.findByBulletinBoardId(bulletinBoardId, conn));
    		}
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

    		String sql = "SELECT id, title, account_id, created_at, view_quantity, favorite_quantity, tag FROM bulletin_boards LEFT OUTER JOIN tags ON id = bulletin_board_id WHERE account_id = ? ORDER BY id;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, accountId);

    		ResultSet resultSet = pStmt.executeQuery();
			BulletinBoard bulletinBoard = null;
			TagList tagList = null;
    		while(resultSet.next()){
    			int bulletinBoardId = resultSet.getInt("id");
    			if(bulletinBoard == null || bulletinBoard.getId() != bulletinBoardId){
    				bulletinBoard = new BulletinBoard();
    				tagList = new TagList();
    				list.add(bulletinBoard);
        			bulletinBoard.setId(bulletinBoardId);
        			bulletinBoard.setTitle(resultSet.getString("title"));
        			bulletinBoard.setAccountId(resultSet.getString("accont_id"));
        			bulletinBoard.setCreatedAt(resultSet.getTimestamp("created_at"));
        			bulletinBoard.setViewQuantity(resultSet.getInt("view_quantity"));
        			bulletinBoard.setFavoriteQuantity(resultSet.getInt("favorite_quantity"));
        			bulletinBoard.setTagList(tagList);
    			}
    			String tag = resultSet.getString("tag");
    			if(tag != null){
    				tagList.add(tag);
    			}
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
    	String orderStr = "";
    	if(order == 1){
    		orderStr = "favorite_quantity";
    	}else if(order == 2){
    		orderStr = "view_quantity";
    	}else{
    		return null;
    	}
    	BulletinBoardList list = new BulletinBoardList();

    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "SELECT id, title, account_id, created_at, view_quantity, favorite_quantity, tag FROM bulletin_boards LEFT OUTER JOIN tags ON id = bulletin_board_id ORDER BY " + orderStr + " DESC, id LIMIT 600;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);

    		ResultSet resultSet = pStmt.executeQuery();
    		BulletinBoard bulletinBoard = null;
			TagList tagList = null;
    		while(resultSet.next()){
    			int bulletinBoardId = resultSet.getInt("id");
    			if(bulletinBoard == null || bulletinBoard.getId() != bulletinBoardId){
    				if(list.size() >= 100){
    					break;
    				}
    				bulletinBoard = new BulletinBoard();
    				tagList = new TagList();
    				list.add(bulletinBoard);
        			bulletinBoard.setId(bulletinBoardId);
        			bulletinBoard.setTitle(resultSet.getString("title"));
        			bulletinBoard.setAccountId(resultSet.getString("accont_id"));
        			bulletinBoard.setCreatedAt(resultSet.getTimestamp("created_at"));
        			bulletinBoard.setViewQuantity(resultSet.getInt("view_quantity"));
        			bulletinBoard.setFavoriteQuantity(resultSet.getInt("favorite_quantity"));
        			bulletinBoard.setTagList(tagList);
    			}
    			String tag = resultSet.getString("tag");
    			if(tag != null){
    				tagList.add(tag);
    			}
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
    	keyword = "%" + keyword + "%";

    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "SELECT id, title, account_id, created_at, view_quantity, favorite_quantity FROM bulletin_boards LEFT OUTER JOIN tags ON id = bulletin_board_id WHERE title LIKE ? ORDER BY id;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, keyword);

    		ResultSet resultSet = pStmt.executeQuery();
    		BulletinBoard bulletinBoard = null;
			TagList tagList = null;
    		while(resultSet.next()){
    			int bulletinBoardId = resultSet.getInt("id");
    			if(bulletinBoard == null || bulletinBoard.getId() != bulletinBoardId){
    				bulletinBoard = new BulletinBoard();
    				tagList = new TagList();
    				list.add(bulletinBoard);
        			bulletinBoard.setId(bulletinBoardId);
        			bulletinBoard.setTitle(resultSet.getString("title"));
        			bulletinBoard.setAccountId(resultSet.getString("accont_id"));
        			bulletinBoard.setCreatedAt(resultSet.getTimestamp("created_at"));
        			bulletinBoard.setViewQuantity(resultSet.getInt("view_quantity"));
        			bulletinBoard.setFavoriteQuantity(resultSet.getInt("favorite_quantity"));
        			bulletinBoard.setTagList(tagList);
    			}
    			String tag = resultSet.getString("tag");
    			if(tag != null){
    				tagList.add(tag);
    			}
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
    	if(partial){
    		keyword = "%" + keyword + "%";
    	}
    	BulletinBoardList list = new BulletinBoardList();

    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "SELECT id, title, account_id, created_at, view_quantity, favorite_quantity FROM bulletin_boards LEFT OUTER JOIN tags ON id = bulletin_board_id WHERE id IN (SELECT DISTINCT bulletin_board_id FROM tags WHERE tag LIKE ?) ORDER BY id;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, keyword);

    		ResultSet resultSet = pStmt.executeQuery();
    		BulletinBoard bulletinBoard = null;
			TagList tagList = null;
    		while(resultSet.next()){
    			int bulletinBoardId = resultSet.getInt("id");
    			if(bulletinBoard == null || bulletinBoard.getId() != bulletinBoardId){
    				bulletinBoard = new BulletinBoard();
    				tagList = new TagList();
    				list.add(bulletinBoard);
        			bulletinBoard.setId(bulletinBoardId);
        			bulletinBoard.setTitle(resultSet.getString("title"));
        			bulletinBoard.setAccountId(resultSet.getString("accont_id"));
        			bulletinBoard.setCreatedAt(resultSet.getTimestamp("created_at"));
        			bulletinBoard.setViewQuantity(resultSet.getInt("view_quantity"));
        			bulletinBoard.setFavoriteQuantity(resultSet.getInt("favorite_quantity"));
        			bulletinBoard.setTagList(tagList);
    			}
    			String tag = resultSet.getString("tag");
    			if(tag != null){
    				tagList.add(tag);
    			}
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

    		String sql ="SELECT f.bulletin_board_id, b.title, f.account_id, b.created_at, b.view_quantity, b.favorite_quantity FROM favorites AS f LEFT OUTER JOIN bulletin_boards AS b ON f.bulletin_board_id = b.id LEFT OUTER JOIN tags AS t ON b.id = t.bulletin_board_id WHERE f.account_id = ? ORDER BY f.bulletin_board_id;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, accountId);

    		ResultSet resultSet = pStmt.executeQuery();
    		BulletinBoard bulletinBoard = null;
			TagList tagList = null;
    		while(resultSet.next()){
    			int bulletinBoardId = resultSet.getInt("id");
    			if(bulletinBoard == null || bulletinBoard.getId() != bulletinBoardId){
    				bulletinBoard = new BulletinBoard();
    				tagList = new TagList();
    				favoriteList.add(bulletinBoard);
        			bulletinBoard.setId(bulletinBoardId);
        			bulletinBoard.setTitle(resultSet.getString("title"));
        			bulletinBoard.setAccountId(resultSet.getString("accont_id"));
        			bulletinBoard.setCreatedAt(resultSet.getTimestamp("created_at"));
        			bulletinBoard.setViewQuantity(resultSet.getInt("view_quantity"));
        			bulletinBoard.setFavoriteQuantity(resultSet.getInt("favorite_quantity"));
        			bulletinBoard.setTagList(tagList);
    			}
    			String tag = resultSet.getString("tag");
    			if(tag != null){
    				tagList.add(tag);
    			}
    		}

    	} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

    	return favoriteList;
    }

    /**
     *
     * @param title
     * @return
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

}
