package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;

@WebServlet("/PassCheckServlet")
public class PassCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pass = request.getParameter("pass");

		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("account");
		boolean check = false;

		System.out.println(account.getPassword()+"と"+pass);
		if(pass.equals(account.getPassword())) {
			check = true;
		}

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(check);
		pw.close();
	}

}
