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
		document.getElementById("test").onclick = personalSetting;
		function personalSetting() {
			menu.textContent = null;

			//現在のニックネームを表示
			let nicknameDisplay = document.createElement("p");
			let nickname = document.createTextNode("<%= nickname %>");
			let nicknameChangeButton = document.createElement("input");
			nicknameChangeButton.setAttribute("id","nicknameChange");
			nicknameChangeButton.setAttribute("type","button");
			nicknameChangeButton.setAttribute("value","変更");
			menu.appendChild(nicknameDisplay);
			nicknameDisplay.appendChild(nickname);
			menu.appendChild(nicknameChangeButton);
			console.log("ok");

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
			menu.appendChild(passChange);

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

			document.getElementById("nicknameChange").onclick = nicknameChange;
		}

		//ニックネーム変更ボタンが押されたら入力画面を表示
		function nicknameChange() {
			console.log("ohu")
			menu.textContent = null;

			//ニックネーム入力フォームj
			let inputNicknameForm = document.createElement("form");
			inputNicknameForm.setAttribute("action","/PURE/nicknameChangeServlet");
			inputNicknameForm.setAttribute("method","post");
			menu.appendChild(inputNicknameForm);

			//現在のニックネームを表示
			let nicknameDisplay = document.createElement("p");
			let nickname = document.createTextNode("現在："+"<%= nickname %>");
			inputNicknameForm.appendChild(nicknameDisplay);
			nicknameDisplay.appendChild(nickname);

			//新しいニックネームの入力設定
			let guideDisplayMsg = document.createElement("p");
			let guideMsg = document.createTextNode("新しいニックネームを入力してください");
			let inputNewNickname = document.createElement("input");
			inputNewNickname.setAttribute("type","text");
			inputNewNickname.setAttribute("name","newNickname");
			inputNicknameForm.appendChild(guideDisplayMsg);
			guideDisplayMsg.appendChild(guideMsg);
			inputNicknameForm.appendChild(inputNewNickname);

			//送信ボタンの設定
			let submit = document.createElement("input");
			submit.setAttribute("type","submit");
			submit.setAttribute("value","変更");
			inputNicknameForm.appendChild(submit);

			//戻るボタン設定
			let back = document.createElement("input");
			back.setAttribute("id","back")
			back.setAttribute("type","button");
			back.setAttribute("value","戻る")
			inputNicknameForm.appendChild(back);

			document.getElementById("back").onclick = personalSetting;

		}

		function passChange() {
			menu.textContent = null;

			//パスワード入力フォーム
			let inputPassForm = document.createElement("form");
			inputPassForm.setAttribute("action","/PURE/PassChangeServlet");
			inputPassForm.setAttribute("method","post");
			menu.appendChild(inputPassForm);

			//現在のパスワードを入力
			let pass = document
		}

		document.getElementById("te").onclick = function() {
			menu.textContent = null;
		}
	</script>
</body>
</html>