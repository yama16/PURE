<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "model.Login" %>
<%
//リクエストスコープに保存されたAccountインスタンスを取得
Login login = (Login) request.getAttribute("againLogin");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8">
	<title>ログイン</title>
</head>
<body>
	<!--  -->
	<% if(login == null) {%>
		<form action="/PURE/LoginServlet" method="post">
			ユーザーID:<input type="text" name="id"><br>
			パスワード:<input type="password" name="pass"><br>
			<input type="submit" value="ログイン">
		</form>
	<% }else{ %>
		<form action="/PURE/LoginServlet" method="post">
			ユーザーID:<input type="text" name="id" value=<%= login.getId() %>><br>
			パスワード:<input type="password" name="pass" value=<%= login.getPassword() %>><br>
			<input type="submit" value="ログイン">
		</form>
		<p>ID又はパスワードが間違っています</p>
	<% } %>
</body>
</html>