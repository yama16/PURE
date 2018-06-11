<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Account" %>
<%@ page import="java.sql.Timestamp"%>
<%
Account account = (Account) session.getAttribute("account");
String id = account.getId();
String nickname = account.getNickname();
Timestamp createTime = account.getCreatedAt();
Timestamp updateTime = account.getUpdatedAt();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8">
<title>個人設定</title>
<link rel="stylesheet" href="dzz.css">
</head>
<body>
	<div id="page">
		<header>
			<h1><%= nickname %></h1>
		</header>
		<main>
			<form id="confirm">
				<fieldset>
					個人設定:<input type="button" value="TEST" id="test"><br>
					パスワード変更:<input type="button" value="testRemove" id="te"><br>
					掲示板:<br>
				</fieldset>
			</form>
		</main>
		<div id="menu">

		</div>
	</div>
	<script>
		let menu = document.getElementById("menu");

		//選択されたメニュー画面を表示
		document.getElementById("test").onclick = function() {
			menu.textContent = null;

			//現在のニックネームを表示
			let nicknameDisplay = document.createElement("p");
			let nickname = document.createTextNode("<%= nickname %>");
			let nicknameChange = document.createElement("input");
			nicknameChange.setAttribute("type","button");
			nicknameChange.setAttribute("value","変更");
			menu.appendChild(nicknameDisplay);
			nicknameDisplay.appendChild(nickname);

			//現在のIDを表示
			let idDisplay = document.createElement("p");
			let id = document.createTextNode("<%= id %>");
			menu.appendChild(idDisplay);
			idDisplay.appendChild(id);

			//パスワード変更
			let passChangeDisplay = document.createElement("p");
			let passChangeMsg = document.createTextNode("パスワード変更");
			let passChange = document.createElement("input");
			passChange.setAttribute("type","button");
			passChange.setAttribute("value","変更");
			menu.appendChild(passChangeDisplay);
			passChangeDisplay.appendChild(passChangeMsg);

			//アカウント作成日時を表示
			let createTimeDisplay = document.createElement("p");
			let createTime = document.createTextNode("作成日時："+"<%= createTime %>");
			menu.appendChild(createTimeDisplay);
			createTimeDisplay.appendChild(createTime);

			//アカウント更新日時を表示
			let updateTimeDisplay = document.createElement("p");
			let updateTime = document.createTextNode("最終更新日時："+"<%= updateTime %>");
			menu.appendChild(updateTimeDisplay);
			updateTimeDisplay.appendChild(updateTime);
		}

		document.getElementById("te").onclick = function() {
			menu.textContent = null;
		}
	</script>
</body>
</html>