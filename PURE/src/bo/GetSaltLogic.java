package bo;

import dao.AccountsDAO;

public class GetSaltLogic {

	public String execute(String id) {
		AccountsDAO dao = new AccountsDAO();
		String salt = dao.getSaltById(id);
		return salt;
	}

}
