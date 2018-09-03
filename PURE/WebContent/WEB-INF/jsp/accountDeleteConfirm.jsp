<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Account" import="model.Sanitize" %>
<%
Sanitize sanitize = new Sanitize();
Account account = (Account) session.getAttribute("account");
String id = "";
String nickname = "";
id = account.getId();
nickname = account.getNickname();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8">
<title>アカウント削除確認画面</title>
</head>
<body>
	<div id="page">
		<header>
			<h1><a href="/PURE/HomeServlet"><img alt="" src="pure_logo.png" height = "220" width = "500"></a></h1>
		</header>
		<p>ID: <%= sanitize.execute(id) %></p>
		<p>ニックネーム: <%= sanitize.execute(nickname) %></p>
		<p>※ アカウントを削除した場合このアカウントは使用不可になります</p>
		<p>※ アカウントが削除されても作った掲示板や投稿したコメントは残ります</p>
		<h2>本当に削除しますか？</h2>
		<form action="/PURE/AccountDeleteServlet" method="get">
			<input type="submit" value="アカウントを削除する">
		</form>
		<form action="/PURE/AccountHomeServlet" method="get">
			<input type="submit" value="やっぱや～めた">
		</form>
	</div>
</body>
</html>