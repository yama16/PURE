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
	<div id="errorDisplay"></div>

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
		let empty = document.getElementById("errorDisplay");

		form.addEventListener("submit",function(e){
			empty.textContent = null;
			let inputCheck = false;

			if(id.value.length < 6 || id.value.length > 12) {
				console.log("11");
				errorMsgDisplay("IDの入力文字数に誤りがあります");
				inputCheck = true;
			}

			if(pass.value.length < 8 || pass.value.length > 16) {
				console.log("22");
				errorMsgDisplay("パスワードの入力文字数に誤りがあります");
				inputCheck = true;
			}

			if(inputCheck) {
				e.preventDefault();
			}

		},false);

		function errorMsgDisplay(error) {
			let inputTagP = document.createElement("p");
			let errorMsg = document.createTextNode(error);
			empty.appendChild(inputTagP);
			inputTagP.appendChild(errorMsg);
		}
	</script>
</body>
</html>