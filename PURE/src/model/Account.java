
package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * アカウント情報を保持するクラス。
 * JavaBeans。
 *
 * @author furukawa
 */
public class Account implements Serializable {

    /** アカウントID */
    private String id;
    /** ニックネーム */
    private String nickname;
    /** パスワード */
    private String password;
    /** アカウント作成をした日時 */
    private Timestamp created_at;
    /** アカウント情報を更新した日時 */
    private Timestamp updated_at;

    /** 引数なしのコンストラクタ */
    public Account(){}
    /**
     * フィールドにセットするものを引数にとるコンストラクタ。
     * @param id アカウントID
     * @param nickname ニックネーム
     * @param password パスワード
     * @param created_at アカウント作成をした日時
     * @param updated_at アカウント情報を更新した日時
     */
    public Account(String id, String nickname, String password, Timestamp created_at, Timestamp updated_at){
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    //全フィールドのgetterとsetter
    public String getId(){ return id; }
    public void setId(String id){ this.id = id; }
    public String getNickname(){ return nickname; }
    public void setNickname(String nickname){ this.nickname = nickname; }
    public String getPassword(){ return password; }
    public void setPassword(String password){ this.password = password; }
    public Timestamp getCreated_at(){ return created_at; }
    public void setCreated_at(Timestamp created_at){ this.created_at = created_at; }
    public Timestamp getUpdated_at(){ return updated_at; }
    public void setUpdated_at(Timestamp updated_at){ this.updated_at = updated_at; }

}
