package model;

import dao.AccountsDAO;

/**
 * ログイン処理をするクラス。
 *
 * @author furukawa
 */
public class LoginLogic {

    /**
     * ログイン処理をするメソッド。
     * @param login ログイン情報
     * @return アカウントが見つかればそのインスタンスを返す。見つからなければnullを返す。
     */
    public Account execute(Login login) {
        AccountsDAO dao = new AccountsDAO();
        return dao.findByLogin(login);
    }

}
