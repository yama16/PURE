package dao;


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

}
