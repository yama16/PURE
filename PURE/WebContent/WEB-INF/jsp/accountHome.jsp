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
					<input type="button" value="個人設定" id="personal"><br>
					<input type="button" value="掲示板" id="bulletinBoard"><br>
					<input type="button" value="お気に入り" id="favorite"><br>
					<input type="button" value="コメント履歴" id="commentHistory">
				</fieldset>
			</form>
		</main>
		<div id="menu">

		</div>
	</div>
	<script>
		let menu = document.getElementById("menu");
		let personal = document.getElementById("personal");
		let bulletinBoard = document.getElementById("bulletinBoard");
		let favorite = document.getElementById("favorite");
		let commentHistory = document.getElementById("commentHistory");

		//個人設定画面を表示
		personal.addEventListener("click",personalSetting,false);

		window.onload = personalSetting;

		//個人設定画面の作成
		function personalSetting() {
			menu.textContent = null;

			//現在のニックネームを表示
			let nicknameDisplay = elt("p", null, "<%= nickname %>");
			let nicknameChangeButton = elt("input", {type: "button", id: "nicknameChange", value: "変更"});
			menu.appendChild(nicknameDisplay);
			menu.appendChild(nicknameChangeButton);
			console.log("ok");

			//現在のIDを表示
			let idDisplay = elt("p", null, "<%= id %>");
			menu.appendChild(idDisplay);

			//パスワード変更
			let passChangeDisplay = elt("p", null, "パスワード変更");
			let passChangeButton = elt("input", {type: "button", id: "passChange", value: "変更"});
			menu.appendChild(passChangeDisplay);
			menu.appendChild(passChangeButton);

			//アカウント作成日時を表示
			let createTimeDisplay = elt("p", null, "作成日時："+"<%= createTime %>");
			menu.appendChild(createTimeDisplay);

			//アカウント更新日時を表示
			let updateTimeDisplay = elt("p", null, "最終更新日："+"<%= updateTime %>");
			menu.appendChild(updateTimeDisplay);

			//変更ボタンが押されたら入力ページを表示
			let nickname = document.getElementById("nicknameChange");
			let pass = document.getElementById("passChange");

			nickname.addEventListener("click",nicknameChange,false);
			pass.addEventListener("click",passChange,false);
		}

		//ニックネーム変更ボタンが押されたら入力画面を表示
		function nicknameChange() {
			console.log("ohu")
			menu.textContent = null;

			//ニックネーム入力フォームj
			let inputNicknameForm = elt("form",{action: "/PURE/NicknameChangeServlet", method: "post", id: "nicknameForm"});
			menu.appendChild(inputNicknameForm);

			//現在のニックネームを表示
			let nicknameDisplay = elt("p", null, "<%= nickname %>");
			inputNicknameForm.appendChild(nicknameDisplay);

			//新しいニックネームの入力設定
			let guideDisplayMsg = elt("p", null, "新しいニックネームを入力してください");
			let inputNewNickname = elt("input",{type: "text", id: "newNickname", name: "newNickname"});
			inputNicknameForm.appendChild(guideDisplayMsg);
			inputNicknameForm.appendChild(inputNewNickname);

			//送信ボタンの設定
			let changeSubmit = elt("input",{type: "submit", id: "changeSubmit", value: "変更"});
			inputNicknameForm.appendChild(changeSubmit);

			//ニックネームが未入力または入力制限を超えていた場合送信を中止
			document.getElementById("nicknameForm").onsubmit = function() {
				let newNickname = document.getElementById("newNickname");
				let inputNicknameCheck = true;
				if(newNickname.value.length <= 0) {
					inputNicknameCheck = false;
				}else if(newNickname.value.length > 10) {
					inputNicknameCheck = false;
				}
				return inputNicknameCheck;
			}

			//戻るボタン設定
			let back = elt("input", {id: "back", type: "button", value: "戻る"});
			inputNicknameForm.appendChild(back);
			document.getElementById("back").onclick = personalSetting;

		}

		function passChange() {
			console.log("ohaa");
			menu.textContent = null;
			let passUsable = false;

			//パスワード入力フォーム
			let inputPassForm = elt("form", {action: "/PURE/TestServlet", method: "post", id: "passForm"});
			menu.appendChild(inputPassForm);

			//現在のパスワードを入力
			let guideNowDisplay = elt("p", null, "現在のパスワードを入力してください");
			let inputNowPass = elt("input",{type: "password", id: "nowPass", name: "nowPass"});
			inputPassForm.appendChild(guideNowDisplay);
			inputPassForm.appendChild(inputNowPass);

			//新しいパスワードを入力
			let guideNewDisplay = elt("p", null, "新しいパスワードを入力してください");
			let inputNewPass = elt("input",{type: "password", id: "newPass", name: "pass"});
			inputPassForm.appendChild(guideNewDisplay);
			inputPassForm.appendChild(inputNewPass);

			//確認パスワードを入力
			let guideConfirmDisplay = elt("p", null, "もう一度入力してください");
			let inputConfirmPass = elt("input",{type: "password", id: "confirmPass"});
			inputPassForm.appendChild(guideConfirmDisplay);
			inputPassForm.appendChild(inputConfirmPass);

			//戻るボタン
			let back = elt("input", {type: "button",id: "back", value: "戻る"});
			inputPassForm.appendChild(back);
			document.getElementById("back").onclick = personalSetting;

			//送信ボタンの作成
			let passSubmit = elt("input", {type: "submit", value: "送信", id: "passSubmit"});
			inputPassForm.appendChild(passSubmit);

			//送信設定
			document.getElementById("passForm").onclick = function() {
				console.log("hoge");
			}
			document.getElementById("passForm").onsubmit = function() {
				console.log("moge");
				let nowPass = document.getElementById("nowPass").value;
				let newPass = document.getElementById("newPass").value;
				let newPassConfirm = document.getElementById("confirmPass").value;
				let isAppropriate = true;

				if(newPass.length < 8 || newPass.length > 16) {
					isAppropriate = false;
				}

				if(newPassConfirm.length < 8 || newPassConfirm.length > 16) {
					isAppropriate = false;
				}

				return isAppropriate;
			}

		}

		/*function passCheck() {
			if() {

			}
		}*/

		function passCheck(){
			let req = new XMLHttpRequest();
			let nowPass = document.getElementById("nowPass");
			req.onreadystatechange = function() {
				if (req.readyState == 4 && req.status == 200) {
					//Passwordがあっていなかったら注意メッセージを表示
					console.log(req.response);
					if (!(JSON.parse(req.response))) {
						console.log("ojoj");
						let errorMsg = elt("p", null, "パスワードの入力が正しくありません");
						passUseble = true;
					} else {
						passUseble = false;
					}
				}
			};

			//PassCheckServletに入力されたPassを送信
			req.open("POST","/PURE/PassCheckServlet");
			req.setRequestHeader("content-type", "application/x-www-form-urlencoded");
			req.send("pass=" + nowPass.value);
		}

		function elt(name, attributes) {
			let node = document.createElement(name);

			if(attributes) {
				for(let attr in attributes) {
					if(attributes.hasOwnProperty(attr)) {
						node.setAttribute(attr, attributes[attr])
					}
				}
			}

			for(let i=2; i < arguments.length; i++) {
				let child = arguments[i];
				if(typeof child == "string") {
					child = document.createTextNode(child);
				}
				node.appendChild(child);
			}
			return node;
		}

		//掲示板画面の表示
		bulletinBoard.addEventListener("click",bulletinBoardCreate,false);
		function bulletinBoardCreate() {
			menu.textContent = null;

			//作成画面への遷移ボタンの設定
			let createBoardButton = elt("input",{type: "button", id: "createBoard", value: "作成"});
			menu.appendChild(createBoardButton);

			//掲示板作成画面
			document.getElementById("createBoard").onclick = function() {
				menu.textContent = null;

				//掲示板作成フォーム設定
				let createBoardForm = elt("form",{action: "/PURE/CreateBulletinBoardServlet", method: "post", id: "boardForm"});
				menu.appendChild(createBoardForm);

				//タイトル入力設定
				let inputTitleDisplay = elt("label", null, "タイトル入力:");
				let inputTitle = elt("input",{type: "text", id: "title", name: "title"});
				createBoardForm.appendChild(inputTitleDisplay);
				inputTitleDisplay.appendChild(inputTitle);
				createBoardForm.appendChild(elt("br"));

				//タグ入力設定
				let inputTagDisplay = new Array();
				let inputTag = new Array();

				for(let i = 1; i <= 6; i++) {
					inputTagDisplay.push(elt("label", null, "タグ"+i+":"));
					inputTag.push(elt("input",{type: "text", name: "tag"+i}));

					createBoardForm.appendChild(inputTagDisplay[i-1]);
					inputTagDisplay[i-1].appendChild(inputTag[i-1]);

					createBoardForm.appendChild(elt("br"));
				}

				//戻るボタン設定
				let back = elt("input", {type: "button",id: "back", value: "戻る"});
				createBoardForm.appendChild(back);
				document.getElementById("back").onclick = bulletinBoardCreate;

				//掲示板作成の確定ボタン設定
				let createConfirm = elt("input", {type: "submit", value: "確定"});
				createBoardForm.appendChild(createConfirm);

				//掲示板作成時の入力値チェック
				let boardForm = document.getElementById("boardForm");
				boardForm.addEventListener("submit",function(e) {
					let title = document.getElementById("title").value;
					let isAppropriate = true;

					if(title.length <= 0) {
						e.preventDefault();
					}

				},false);
			}
		}
	</script>
</body>
</html>