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
	<!-- ログイン失敗時にメッセージ表示 -->
	<% if( login != null) { %>
		<p>ID又はパスワードが間違っています</p>
	<% } %>

	<!-- 未入力の項目がある場合エラーメッセージを表示 -->
	<p><output id="empty"></output></p>

	<!-- ログインページ -->
	<form action="/PURE/LoginServlet" method="post" id="form">
		ユーザーID:<input type="text" name="id" id="id" value=<%= id %>><br>
		パスワード:<input type="password" name="pass" id="pass"  value=<%= pass %>><br>
		<input type="submit" value="ログイン" id="submit">
	</form>

	<!-- ID、パスワードが未入力でないかの確認 -->
	<script>
		let id = document.getElementById("id");			//入力されたIDを取得
		let pass = document.getElementById("pass");		//入力されたパスワードを取得
		let form = document.getElementById("form");
		form.onsubmit = function(){
			if(!(id.value) || !(pass.value)) {
				let empty = document.getElementById("empty");
				empty.innerHTML = "未入力の項目があります";
				return false;
			}else{
				return true;
			}
		};
	</script>
</body>
</html>