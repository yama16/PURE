
package model;

import dao.AccountsDAO;

/**
 * アカウント情報の更新を処理をするクラス。
 *
 * @author furukawa
 */
public class UpdateAccountLogic {

    /**
     * アカウント情報を更新するメソッド。
     * 更新前のアカウントIDと更新したいAccountインスタンスを渡す。
     * 更新できればtrueを返す。できなければfalseを返す。
     * @param id 更新したいアカウントのアカウントID
     * @param account 更新後のアカウント
     * @return 更新できればtrue。できなければfalse。
     */
    public boolean execute(String id, Account account){
        AccountsDAO dao = new AccountsDAO();
        return dao.update(id, account);
    }

}
