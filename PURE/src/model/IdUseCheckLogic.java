
package model;

import dao.AccountsDAO;

/**
 * IDが既に使われているかチェックする処理をするクラス。
 *
 * @author furukawa
 */
public class IdUseCheckLogic {

    /**
     * 指定したidが、既にAccountsテーブルに登録されているか調べるメソッド。
     * 既に登録されているか調べたいIDをString型で引数で渡す。
     * 既に登録されていればtrueを返す。されていなければfalseを返す。
     * @param id 登録されているか調べるid
     * @return idが既に登録されていればtrue。されていなければfalse。
     */
    public boolean execute(String id){
        AccountsDAO dao = new AccountsDAO();
        return dao.isUse(id);
    }

}
