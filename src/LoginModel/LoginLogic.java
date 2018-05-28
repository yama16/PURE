package LoginModel;

import dao.UserDAO;

public class LoginLogic {
	//アカウントの有無の判断
	public boolean execute(User user) {
		UserDAO userDAO = new UserDAO();
		userDAO.LoginDecision(user);
		return false;
	}

}
