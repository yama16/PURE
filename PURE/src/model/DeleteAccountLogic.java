
package model;

import dao.AccountsDAO;

/**
 * アカウント削除処理をするクラス。
 *
 * @author furukawa
 */
public class DeleteAccountLogic {

    /**
     * Accountsテーブルからアカウントを削除するメソッド。
     * 削除したいアカウントのAccountインスタンスを引数で渡す。
     * 削除できればtrueを返す。できなければfalseを返す。
     * @param account 削除するアカウント
     * @return 削除できればtrue。できなければfalse。
     */
    public boolean execute(Account account){
        AccountsDAO dao = new AccountsDAO();
        return dao.delete(account.getId());
    }

}
