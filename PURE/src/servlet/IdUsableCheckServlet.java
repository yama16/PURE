package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bo.IdUsableCheckLogic;

/**
 * Servlet implementation class IdCheckServlet
 */
@WebServlet("/IdUsableCheckServlet")
public class IdUsableCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//入力されたIDを取得
		String id = request.getParameter("id");

		//IDがすでに登録されているかの確認
		IdUsableCheckLogic idCheck = new IdUsableCheckLogic();
		boolean check = idCheck.execute(id);

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(check);
		pw.close();


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
