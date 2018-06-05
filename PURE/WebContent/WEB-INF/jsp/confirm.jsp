<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Account" %>
<%
Account account = (Account) session.getAttribute("createAccount");

String id = account.getId();
String nicname = account.getNickname();
String pass = account.getPassword();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8">
<title>アカウント作成</title>
</head>
<body>
	<p>ID：<%= id %></p><br>
	<p>ニックネーム：<%= nicname %></p><br>
	<p>よろしいですか？</p>

	<form action="AccountRegistrationServlet" method="post">
		<input type="submit" value="確定">
	</form>
	<form action="AccountRegistrationServlet" method="get">
		<input type="submit" value="取り消し">
	</form>
</body>
</html>