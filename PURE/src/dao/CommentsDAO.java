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
     * @return 検索した掲示板のコメントのリストを返す。コメントが無ければnullを返す。
     */
    public List<Comment> findByBulletin_board_id(int bulletin_board_id){
    	Connection conn = null;
    	List<Comment> commentList = new ArrayList<>();
    	try{
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

    		String sql = "SELECT * FROM comments WHERE bulletin_board_id = ?";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, bulletin_board_id);
    		ResultSet resultSet = pStmt.executeQuery();
    		while(resultSet.next()){
    			Comment comment = new Comment();
    			comment.setId(resultSet.getInt("id"));
    			comment.setBulletin_board_id(resultSet.getInt("bulletin_board_id"));
    			comment.setAccount_id(resultSet.getString("account_id"));
    			comment.setComment(resultSet.getString("comment"));
    			comment.setCreated_at(resultSet.getTimestamp("created_at"));
    			comment.setPure_quantity(resultSet.getInt("pure_quantity"));
    			commentList.add(comment);
    		}
    	} catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
    	return commentList.size() == 0 ? null : commentList;
    }

}
