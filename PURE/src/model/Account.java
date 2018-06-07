
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
    private Timestamp createdAt;
    /** アカウント情報を更新した日時 */
    private Timestamp updatedAt;

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
    public Account(String id, String nickname, String password, Timestamp createdAt, Timestamp updatedAt){
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    //全フィールドのgetterとsetter
    public String getId(){ return id; }
    public void setId(String id){ this.id = id; }
    public String getNickname(){ return nickname; }
    public void setNickname(String nickname){ this.nickname = nickname; }
    public String getPassword(){ return password; }
    public void setPassword(String password){ this.password = password; }
    public Timestamp getCreatedAt(){ return createdAt; }
    public void setCreatedAt(Timestamp createdAt){ this.createdAt = createdAt; }
    public Timestamp getUpdatedAt(){ return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt){ this.updatedAt = updatedAt; }

}
