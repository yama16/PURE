package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IdUseCheckLogic;

/**
 * Servlet implementation class IdCheckServlet
 */
@WebServlet("/InputCheckServlet")
public class InputCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//入力されたIDを取得
		String id = request.getParameter("id");

		//IDがすでに登録されているかの確認
		IdUseCheckLogic idCheck = new IdUseCheckLogic();
		boolean check = idCheck.execute(id);

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(check);
		pw.close();


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
