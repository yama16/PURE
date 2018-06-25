package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.BulletinBoard;
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

    	List<Comment> commentList = new ArrayList<>();

    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "SELECT c.id, c.bulletin_board_id, c.account_id, c.comment, c.created_at, c.pure_quantity, a.nickname FROM comments AS c LEFT OUTER JOIN accounts AS a ON c.account_id = a.id WHERE c.bulletin_board_id = ?";

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

    	} catch (SQLException e) {
    		e.printStackTrace();
            return null;
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

    	List<Comment> newCommentList = new ArrayList<>();

    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "SELECT c.id, c.bulletin_board_id, c.account_id, c.comment, c.created_at, c.pure_quantity, a.nickname FROM comments AS c LEFT OUTER JOIN accounts AS a WHERE c.bulletin_board_id = ? AND c.comment_id > ?";

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
    public boolean create(Comment comment){

    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "INSERT INTO comments(id, bulletin_board_id, account_id, comment, created_at) VALUES((SELECT COALESCE(MAX(id),0)+1 FROM comments WHERE bulletin_board_id = ?), ?, ?, ?, COALESCE(?, DEFAULT));";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, comment.getBulletinBoardId());
    		pStmt.setInt(2, comment.getBulletinBoardId());
    		pStmt.setString(3, comment.getAccountId());
    		pStmt.setString(4, comment.getComment());
    		pStmt.setTimestamp(5, comment.getCreatedAt());

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
    public List<BulletinBoard> history(String accountId){
    	List<BulletinBoard> list = new ArrayList<>();
    	try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

    		String sql = "SELECT c.id, c.bulletin_board_id, c.comment, c.created_at, c.pure_quantity, b.title, b.account_id, b.created_at AS bulletin_board_created_at, b.view_quantity, b.favorite_quantity FROM comments AS c LEFT OUTER JOIN bulletin_boards AS b ON c.bulletin_board_id = b.id WHERE c.account_id=? ORDER BY c.bulletin_board_id, c.id;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, accountId);

    		ResultSet resultSet = pStmt.executeQuery();
    		while(resultSet.next()){
    			int bulletinBoardId = resultSet.getInt("bulletin_board_id");
    			if(list.size() == 0 || list.get(list.size() - 1).getId() != bulletinBoardId){
    				BulletinBoard bulletinBoard = new BulletinBoard();
    				bulletinBoard.setId(bulletinBoardId);
    				bulletinBoard.setTitle(resultSet.getString("title"));
    				bulletinBoard.setAccountId(resultSet.getString("account_id"));
    				bulletinBoard.setCreatedAt(resultSet.getTimestamp("bulletin_board_created_at"));
    				bulletinBoard.setViewQuantity(resultSet.getInt("view_quantity"));
    				bulletinBoard.setId(resultSet.getInt("favorite_quantity"));
    				List<Comment> commentList = new ArrayList<>();
    				bulletinBoard.setCommentList(commentList);
    				list.add(bulletinBoard);
    			}
    			Comment comment = new Comment();
    			comment.setId(resultSet.getInt("id"));
    			comment.setAccountId(accountId);
    			comment.setBulletinBoardId(bulletinBoardId);
    			comment.setComment(resultSet.getString("comment"));
    			comment.setCreatedAt(resultSet.getTimestamp("created_at"));
    			comment.setPureQuantity(resultSet.getInt("pure_quantity"));
    			list.get(list.size() - 1).getCommentList().add(comment);
    		}

    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
		}

    	return list;
    }

}
