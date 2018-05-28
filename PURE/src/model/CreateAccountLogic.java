
package model;

import dao.AccountsDAO;

/**
 * アカウント作成処理をするクラス。
 *
 * @author furukawa
 */
public class CreateAccountLogic {

    /**
     * Accountsテーブルに登録するメソッド。
     * 登録したいアカウント情報を持ったAccountインスタンスを引数で渡す。
     * 登録できればtrueを返す。できなければfalseを返す。
     * @param account 本登録するアカウント
     * @return 本登録できればtrue。できなければfalse。
     */
    public boolean execute(Account account){
        AccountsDAO dao = new AccountsDAO();
        return dao.create(account);
    }

}
