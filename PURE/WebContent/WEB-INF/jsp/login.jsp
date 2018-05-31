<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "model.Login" %>
<%
Login login = (Login) request.getAttribute("login");

//ID、パスワードを取得
String id = "";
String pass = "";
if(login != null) {
	id = login.getId();
	pass = login.getPassword();
}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8">
	<title>ログイン</title>
</head>
<body>
	<% if(login != null) { %>
		<p>ID又はパスワードが間違っています</p>
	<% } %>
	<p><output id="empty"></output></p>
	<form action="/PURE/LoginServlet" method="post" onsubmit="return check()">
		ユーザーID:<input type="text" name="id" id="id" value=<%= id %>><br>
		パスワード:<input type="password" name="pass" id="pass"  value=<%= pass %>><br>
		<input type="submit" value="ログイン" id="submit">
	</form>
	<script type="text/javascript">
			var id = document.getElementById("id");
			var pass = document.getElementById("pass");
			function check(){
				if(id.value.length <= 0 || pass.value.length <= 0) {
					var empty = document.getElementById("empty");
					empty.innerHTML = "未入力の項目があります";
					return false;
				}else{
					return true;
				}
			}
	</script>
</body>
</html>