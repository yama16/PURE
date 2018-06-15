package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Comment;

/**
 * Comments（コメント）テーブルを操作するDAO。
 * @author furukawa
 */
public class CommentsDAO {

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
     * 掲示板IDからその掲示板のコメントのリストを返すメソッド。
     * @param bulletin_board_id 検索する掲示板のID
     * @return 検索した掲示板のコメントのリストを返す。
     */
    public List<Comment> findByBulletinBoardId(int bulletinBoardId){
    	Connection conn = null;
    	List<Comment> commentList = new ArrayList<>();
    	try{
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

    		String sql = "SELECT * FROM comments WHERE bulletin_board_id = ?";
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
    			commentList.add(comment);
    		}
    	} catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
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
    public List<Comment> findNewComment(int bulletinBoardId, int commentId){
    	Connection conn = null;
    	List<Comment> newCommentList = new ArrayList<>();
    	try {
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

    		String sql = "SELECT * FROM comments WHERE bulletin_board_id = ? AND comment_id > ?";
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
    			newCommentList.add(comment);
    		}
    	} catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
    	return newCommentList;
    }

    /**
     * commentsテーブルに引数で渡されたコメントを追加するメソッド。
     * @param comment 追加するコメント。
     * @return 追加できたときtrueを返す。追加できなかったときfalseを返す。
     */
    public boolean create(Comment comment){
    	Connection conn = null;
    	try{
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

    		String sql = "INSERT INTO comments(*) VALUES("
    				+ "(SELECT COALESCE(MAX(id),0)+1 FROM comments WHERE bulletin_board_id=?)"
    				+ ",?,?,?,?,?)";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, comment.getBulletinBoardId());
    		pStmt.setInt(2, comment.getBulletinBoardId());
    		pStmt.setString(3, comment.getAccountId());
    		pStmt.setString(4, comment.getComment());
    		pStmt.setTimestamp(5, comment.getCreatedAt());
    		pStmt.setInt(6, comment.getPureQuantity());
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
