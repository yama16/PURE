
package model;

import java.io.Serializable;

/**
 * ユーザーが入力したログイン情報を保持するクラス。
 *
 * @author furukawa
 */
public class Login implements Serializable {

    /** アカウントID */
    private String id;
    /** パスワード */
    private String password;

    /** 引数なしのコンストラクタ */
    public Login(){}

    /**
     * アカウントIDとパスワードを引数に持つコンストラクタ。
     * @param id アカウントID
     * @param password パスワード
     */
    public Login(String id, String password){
        this.id = id;
        this.password = password;
    }

    //getter
    public String getId(){ return id; }
    public String getPassword(){ return password; }

}
