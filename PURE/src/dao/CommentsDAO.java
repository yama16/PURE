package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.BulletinBoard;
import model.BulletinBoardList;
import model.Comment;
import model.CommentList;

/**
 * Comments（コメント）テーブルを操作するDAO。
 * @author furukawa
 */
public class CommentsDAO {

    private final String JDBC_URL = "jdbc:mysql://localhost:3306/pure?useUnicode=true&characterEncoding=utf8";
    private final String DB_USER = "root";
    private final String DB_PASS = "root";

    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        }
    }

    /**
     * 掲示板IDからその掲示板のコメントのリストを返すメソッド。
     * @param bulletin_board_id 検索する掲示板のID
     * @return 検索した掲示板のコメントのリストを返す。
     * @throws SQLException
     */
    protected CommentList findByBulletinBoardId(int bulletinBoardId, Connection conn) throws SQLException{

    	CommentList commentList = new CommentList();

    	String sql = "SELECT c.id, c.bulletin_board_id, c.account_id, c.comment, c.created_at, c.pure_quantity, a.nickname FROM comments AS c LEFT OUTER JOIN accounts AS a ON c.account_id = a.id WHERE c.bulletin_board_id = ? ORDER BY c.id";

    	PreparedStatement pStmt = conn.prepareStatement(sql);
    	pStmt.setInt(1, bulletinBoardId);

    	ResultSet resultSet = pStmt.executeQuery();
    	while(resultSet.next()){
    		Comment comment = new Comment();
    		comment.setId(resultSet.getInt("id"));
    		comment.setBulletinBoardId(resultSet.getInt("bulletin_board_id"));
    		comment.setAccountId(resultSet.getString("account_id"));
    		comment.setComment(resultSet.getString("comment"));
    		comment.setCreatedAt(resultSet.getTimestamp("created_at"));
    		comment.setPureQuantity(resultSet.getInt("pure_quantity"));
    		comment.setNickname(resultSet.getString("nickname"));
    		commentList.add(comment);
    	}

    	return commentList;
    }

    /**
     * 引数で指定した掲示板のコメントの中から、引数で指定したコメントより新しいコメントのリストを返すメソッド。
     *
     * @param bulletin_board_id 検索する掲示板のID
     * @param comment_id 最新のコメントのID
     * @return 引数のcomment_idよりも最新のコメントのリストを返す。
     */
    public CommentList findNewComment(int bulletinBoardId, int commentId){

    	CommentList newCommentList = new CommentList();

    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "SELECT c.id, c.bulletin_board_id, c.account_id, c.comment, c.created_at, c.pure_quantity, a.nickname FROM comments AS c LEFT OUTER JOIN accounts AS a ON c.account_id = a.id WHERE c.bulletin_board_id = ? AND c.id > ?";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, bulletinBoardId);
    		pStmt.setInt(2, commentId);

    		ResultSet resultSet = pStmt.executeQuery();
    		while(resultSet.next()){
    			Comment comment = new Comment();
    			comment.setId(resultSet.getInt("id"));
    			comment.setBulletinBoardId(resultSet.getInt("bulletin_board_id"));
    			comment.setAccountId(resultSet.getString("account_id"));
    			comment.setComment(resultSet.getString("comment"));
    			comment.setCreatedAt(resultSet.getTimestamp("created_at"));
    			comment.setPureQuantity(resultSet.getInt("pure_quantity"));
    			comment.setNickname(resultSet.getString("nickname"));
    			newCommentList.add(comment);
    		}

    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
        }

    	return newCommentList;
    }

    /**
     * commentsテーブルに引数で渡されたコメントを追加するメソッド。
     * @param comment 追加するコメント。
     * @return 追加できたときtrueを返す。追加できなかったときfalseを返す。
     */
    public boolean create(int bulletinBoardId, String accountId, String comment, Timestamp createdAt){

    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "SELECT COALESCE(MAX(id),0)+1 AS commentId FROM comments WHERE bulletin_board_id = ?;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, bulletinBoardId);

    		ResultSet resultSet = pStmt.executeQuery();
    		int commentId = 0;
    		if(resultSet.next()){
    			commentId = resultSet.getInt("commentId");
    		}else{
    			return false;
    		}

    		sql = "INSERT INTO comments(id, bulletin_board_id, account_id, comment, created_at) VALUES(?, ?, ?, ?, COALESCE(?, CURRENT_TIMESTAMP()));";

    		pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, commentId);
    		pStmt.setInt(2, bulletinBoardId);
    		pStmt.setString(3, accountId);
    		pStmt.setString(4, comment);
    		pStmt.setTimestamp(5, createdAt);

    		System.out.println(pStmt);

    		int result = pStmt.executeUpdate();
    		if(result != 1){
    			return false;
    		}

    	} catch (SQLException e) {
    		e.printStackTrace();
            return false;
        }

    	return true;
    }

    /**
     * コメントの履歴を取得するメソッド。
     * 掲示板の情報と、引数のアカウントのコメントが入った掲示板のリストを返す。
     * タグの情報は入っていない。
     * @param accountId 履歴を取得するアカウントのID
     * @return アカウントのコメントの履歴の入った掲示板のリスト。
     */
    public BulletinBoardList history(String accountId){
    	BulletinBoardList bulletinBoardList = new BulletinBoardList();
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "SELECT c.id, c.bulletin_board_id, c.account_id, c.comment, c.created_at, c.pure_quantity, a.nickname FROM comments AS c LEFT OUTER JOIN accounts AS a ON c.account_id = a.id WHERE c.account_id = ?;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, accountId);

    		ResultSet resultSet = pStmt.executeQuery();
    		BulletinBoardsDAO bulletinBoardsDAO = new BulletinBoardsDAO();
    		TagsDAO tagsDAO = new TagsDAO();
    		while(resultSet.next()){
    			int bulletinBoardId = resultSet.getInt("bulletin_board_id");
    			Comment comment = new Comment();
    			comment.setId(resultSet.getInt("id"));
    			comment.setBulletinBoardId(bulletinBoardId);
    			comment.setAccountId(resultSet.getString("account_id"));
    			comment.setComment(resultSet.getString("comment"));
    			comment.setCreatedAt(resultSet.getTimestamp("created_at"));
    			comment.setPureQuantity(resultSet.getInt("pure_quantity"));
    			comment.setNickname(resultSet.getString("nickname"));
    			BulletinBoard bulletinBoard = bulletinBoardsDAO.findById(bulletinBoardId, conn);
        		if(bulletinBoardList.add(bulletinBoard)){
            		bulletinBoard.setTagList(tagsDAO.findByBulletinBoardId(bulletinBoardId, conn));
        		}
        		bulletinBoardList.addComment(comment);
    		}

    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
		}

    	return bulletinBoardList;
    }

    /**
     * 引数で指定したコメントのPURE数をupdateの数だけ変更する。
     * @param commentId PURE数を変更するコメントのID
     * @param bulletinBoardId PURE数を変更するコメントのある掲示板のID
     * @param update 増やすなら1、減らすなら-1。
     * @param conn コネクション
     * @return 増やせれば1、減らせれば-1、できなければ0を返す。
     * @throws SQLException
     */
    protected int updatePure(int commentId, int bulletinBoardId, int update, Connection conn) throws SQLException{
    	String sql = "UPDATE comments SET pure_quantity = pure_quantity + ? WHERE id=? AND bulletin_board_id=?;";

    	PreparedStatement pStmt = conn.prepareStatement(sql);
    	pStmt.setInt(1, update);
    	pStmt.setInt(2, commentId);
    	pStmt.setInt(3, bulletinBoardId);

    	int result = pStmt.executeUpdate();
    	if(result != 1){
    		return 0;
    	}
    	return update;
    }

    /**
     *
     * @param timestamp
     * @return
     */
    public BulletinBoardList getRealTimeComment(Timestamp timestamp){
    	BulletinBoardList bulletinBoardList = new BulletinBoardList();
    	Connection conn = null;
    	try{
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

    		String sql = "SELECT c.id, c.bulletin_board_id, c.account_id, c.comment, c.created_at, c.pure_quantity, a.nickname FROM comments AS c LEFT OUTER JOIN accounts AS a ON c.account_id = a.id WHERE c.created_at > ?";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setTimestamp(1, timestamp);

    		ResultSet resultSet = pStmt.executeQuery();
    		BulletinBoardsDAO bulletinBoardsDAO = new BulletinBoardsDAO();
    		TagsDAO tagsDAO = new TagsDAO();
    		while(resultSet.next()){
    			int bulletinBoardId = resultSet.getInt("bulletin_board_id");
    			Comment comment = new Comment();
    			comment.setId(resultSet.getInt("id"));
    			comment.setBulletinBoardId(bulletinBoardId);
    			comment.setAccountId(resultSet.getString("account_id"));
    			comment.setComment(resultSet.getString("comment"));
    			comment.setCreatedAt(resultSet.getTimestamp("created_at"));
    			comment.setPureQuantity(resultSet.getInt("pure_quantity"));
    			comment.setNickname(resultSet.getString("nickname"));
    			BulletinBoard bulletinBoard = bulletinBoardsDAO.findById(bulletinBoardId, conn);
        		if(bulletinBoardList.add(bulletinBoard)){
            		bulletinBoard.setTagList(tagsDAO.findByBulletinBoardId(bulletinBoardId, conn));
        		}
        		bulletinBoardList.addComment(comment);
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

    	return bulletinBoardList;
    }

    /**
     *
     * @param bulletinBoardId
     * @return
     */
    public List<Integer> pureComments(int bulletinBoardId){
    	Connection conn = null;
    	List<Integer> list = new ArrayList<>();
    	try{
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

    		String sql = "SELECT id FROM comments WHERE bulletin_board_id = ? AND pure_quantity >= 1";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, bulletinBoardId);

    		ResultSet resultSet = pStmt.executeQuery();
    		while(resultSet.next()){
    			list.add(resultSet.getInt("id"));
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

}
