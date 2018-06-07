<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Account" %>
<%
Account account = (Account) session.getAttribute("createAccount");

String id = account.getId();
String nickname = account.getNickname();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8">
<title>アカウント作成確認</title>
</head>
<body>
	<h1>アカウント作成確認画面</h1>
	<p>ID：<%= id %></p>
	<p>ニックネーム：<%= nickname %></p>
	<p>よろしいですか？</p>

	<form action="CreateAccountServlet" method="post">
		<input type="submit" value="確定">
	</form>
	<form action="CreateAccountServlet" method="get">
		<input type="submit" value="訂正">
	</form>
</body>
</html>