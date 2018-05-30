<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8">
	<title>ログイン</title>
</head>
<body>
	<form action="/Test/LoginServlet" method="post">
		ユーザーID:<input type="text" name="id"><br>
		パスワード:<input type="password" name="pass"><br>
		<input type="submit" value="ログイン">
	</form>
</body>
</html>