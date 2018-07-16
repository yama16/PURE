package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bo.EditTagLogic;
import model.TagList;

/**
 * Servlet implementation class TagUpdateServlet
 */
@WebServlet("/TagsUpdateServlet")
public class TagsUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		System.out.println(boardId);

		TagList tags = new TagList();
		for(int i = 1; i <= 6; i++) {
			String tag = request.getParameter("tag"+i);
			if(!(tag == null)) {
				tags.add(tag);
			}else{
				break;
			}
		}

		EditTagLogic editTagLogic = new EditTagLogic();
		boolean tagUpdate = editTagLogic.execute(boardId, tags);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/accountHome.jsp");
		dispatcher.forward(request, response);
	}

}
