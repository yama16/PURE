<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.BulletinBoardList" %>
<%@ page import="model.BulletinBoard" %>
<%
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
		<h1>掲示板ランキング</h1>
		<div id="rankingField">
			<dl>
			<%
			for(int i = 0; i < bulletinBoardList.size(); i++) {
				BulletinBoard bulletinBoard = bulletinBoardList.get(i);
			%>
				<dt>
					<a href="/PURE/BulletinBoardServlet?id=<%= bulletinBoard.getId() %>">
						<%= bulletinBoard.getTitle() %>
					</a>
				</dt>
			<% } %>
			</dl>
		</div>
	</body>
</html>