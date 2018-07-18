<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
boolean deleteAccountCheck = (boolean)request.getAttribute("deleteCheck");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8">
<title>アカウント削除完了画面</title>
</head>
<body>
	<% if(deleteAccountCheck) { %>
		<h2>アカウント削除が完了しました</h2>
	<% }else{ %>
		<h2>アカウント削除に失敗しました</h2>
	<% } %>
	<a href="/PURE/HomeServlet">ホーム</a>
</body>
<script>
	console.log("<%= deleteAccountCheck %>");
</script>
</html>