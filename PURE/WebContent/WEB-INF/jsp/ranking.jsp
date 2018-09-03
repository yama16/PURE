<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.BulletinBoardList" %>
<%@ page import="model.BulletinBoard" %>
<%@ page import="model.Sanitize" %>
<%
Sanitize sanitize = new Sanitize();
BulletinBoardList bulletinBoardList = (BulletinBoardList) request.getAttribute("BulletinBoardList");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
    	<link rel="stylesheet" href="ranking.css">
    	<title>ランキング</title>
	</head>
	<body>
		<a href="/PURE/HomeServlet"><img alt="" src="pure_logo.png" height = "220" width = "500"></a>
		<h1>掲示板ランキング</h1>
		<div id="rankingField">
			<dl>
			<%
			for(int i = 0; i < bulletinBoardList.size(); i++) {
				BulletinBoard bulletinBoard = bulletinBoardList.get(i);
			%>
				<dt>
					<a href="/PURE/BulletinBoardServlet?id=<%= bulletinBoard.getId() %>">
						<%= sanitize.execute(bulletinBoard.getTitle()) %>
					</a>
				</dt>
			<% } %>
			</dl>
		</div>
	</body>
</html>