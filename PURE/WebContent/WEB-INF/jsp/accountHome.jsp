<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Account" %>
<%@ page import="java.sql.Timestamp"%>
<%
Account account = (Account) session.getAttribute("account");
String id = account.getId();
String nickname = account.getNickname().replace("\\", "\\\\").replace("\"", "\\\"");
Timestamp createTime = account.getCreatedAt();
Timestamp updateTime = account.getUpdatedAt();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>個人設定</title>
<link rel="stylesheet" href="accountHome.css">
</head>
<body>
	<div id="page">
		<header>
			<h1><a href="/PURE/HomeServlet">PURE</a></h1>
		</header>
		<main>
			<form id="confirm">
				<input type="button" value="個人設定" id="personal" style="width:200px; height:30px;"><br>
				<input type="button" value="掲示板" id="bulletinBoard" style="width:200px; height:30px;"><br>
				<input type="button" value="お気に入り" id="favorite" style="width:200px; height:30px;"><br>
				<input type="button" value="コメント履歴" id="commentHistory" style="width:200px; height:30px;">
			</form>
		</main>
		<div id="menu"></div>
	</div>
	<script>
		let menu = document.getElementById("menu");
		let personal = document.getElementById("personal");
		let bulletinBoard = document.getElementById("bulletinBoard");
		let favorite = document.getElementById("favorite");
		let commentHistory = document.getElementById("commentHistory");
		let favoriteBulletinBoard;
		let passUseble = false;
		let titleUseble = false;

		///ページを読み込んだ時個人設定画面を表示
		window.onload = personalSetting;

		//個人設定画面を表示
		personal.addEventListener("click",personalSetting,false);

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
			let nicknameForm = document.getElementById("nicknameForm");
			nicknameForm.addEventListener("submit",function(e) {
				let newNickname = document.getElementById("newNickname");
				let inputNicknameCheck = true;
				if(newNickname.value.length <= 0) {
					inputNicknameCheck = false;
				}else if(newNickname.value.length > 10) {
					inputNicknameCheck = false;
				}
				if(!(inputNicknameCheck)) {
					inputNicknameForm.appendChild(elt("p", null, "※ニックネームの入力が正しくありません"));
					e.preventDefault();
				}
			},false);

			//戻るボタン設定
			let back = elt("input", {id: "back", type: "button", value: "戻る"});
			inputNicknameForm.appendChild(back);
			document.getElementById("back").onclick = personalSetting;

		}

		function passChange() {
			console.log("ohaa");
			menu.textContent = null;

			//パスワード入力フォーム
			let inputPassForm = elt("form", {action: "/PURE/PassChangeServlet", method: "post", id: "passForm"});
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

			//パスワードが一致しているかのチェック
			document.getElementById("nowPass").addEventListener("blur",passCheck,false);

			//パスワード変更のために入力した値のチェック
			document.getElementById("passForm").onsubmit = function() {
				console.log("moge");
				let nowPass = document.getElementById("nowPass").value;
				let newPass = document.getElementById("newPass").value;
				let newPassConfirm = document.getElementById("confirmPass").value;
				let isAppropriate = true;
				console.log(passUseble);

				if(passUseble) {
					inputPassForm.appendChild(elt("p", null, "⚠パスワードの入力が正しくありません"));
					isAppropriate = false;
				}

				if(nowPass.length <= 0) {
					console.log("2");
					isAppropriate = false;
				}

				if(newPass.length < 8 || newPass.length > 16) {
					inputPassForm.appendChild(elt("p", null, "パスワードの文字数が足りません"));
					console.log("3");
					isAppropriate = false;
				}

				if(newPassConfirm.length < 8 || newPassConfirm.length > 16) {
					console.log("4");
					isAppropriate = false;
				}

				if(newPass !== newPassConfirm) {
					console.log("5");
					isAppropriate = false;
				}

				return isAppropriate;
			}

		}

		//入力されたパスワードが自分のパスワードと一致しているかのチェック
		function passCheck(){
			let req = new XMLHttpRequest();
			let nowPass = document.getElementById("nowPass");

			req.onreadystatechange = function() {
				if (req.readyState == 4 && req.status == 200) {
					if (!(JSON.parse(req.response))) {
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

		//掲示板画面の表示
		bulletinBoard.addEventListener("click",bulletinBoardCreate,false);
		function bulletinBoardCreate() {
			menu.textContent = null;
			let req = new XMLHttpRequest();

			req.onreadystatechange = function() {
				if (req.readyState == 4 && req.status == 200) {
					let myBulletinBoard = JSON.parse(req.response);
					for(let boards of myBulletinBoard) {
						let inputTagP = elt("p");
						menu.appendChild(inputTagP);
						inputTagP.appendChild(elt("a", {href: "/PURE/BulletinBoardServlet?id="+boards.id}, boards.title));
					}

					//作成画面への遷移ボタンの設定
					let createBoardButton = elt("input",{type: "button", id: "createBoard", value: "作成"});
					menu.appendChild(createBoardButton);

					//掲示板作成画面の表示
					document.getElementById("createBoard").addEventListener("click",function() {
						menu.textContent = null;

						//掲示板作成フォーム設定
						let createBoardForm = elt("form",{action: "/PURE/CreateBulletinBoardServlet", method: "post", id: "boardForm"});
						menu.appendChild(createBoardForm);

						//タイトル入力設定
						let counter = 0;
						let inputTags = elt("div",{id: "tags"});
						let inputTitleDisplay = elt("label", null, "タイトル入力:");
						let inputTitle = elt("input",{type: "text", id: "title", name: "title"});
						createBoardForm.appendChild(inputTitleDisplay);
						inputTitleDisplay.appendChild(inputTitle);
						createBoardForm.appendChild(elt("br"));
						createBoardForm.appendChild(inputTags);
						createBoardForm.appendChild(elt("br"));

						//タグ追加ボタンの設定
						let addTag = elt("input", {type: "button", id: "addTag", value: "タグ追加"});
						createBoardForm.appendChild(addTag);

						document.getElementById("addTag").addEventListener("click", function(){

							if(counter < 6) {
								counter++;
								let inputTagP = elt("p");
								let inputTagDisplay = elt("label", null, "タグ" + counter);
								let inputTag = elt("input", {type: "text", id: "tag"+counter, name: "tag"+counter});
								inputTags.appendChild(inputTagP);
								inputTagP.appendChild(inputTagDisplay);
								inputTagDisplay.appendChild(inputTag);
							}
						}, false);

						//タグ削除ボタンの設定
						let tags = document.getElementById("tags");
						let removeTag = elt("input", {type: "button", id: "removeTag", value: "削除"});
						createBoardForm.appendChild(removeTag);

						document.getElementById("removeTag").addEventListener("click",function(){
							if(counter >= 1) {
								counter--;
								tags.removeChild(tags.lastElementChild);
							}
						},false);

						//戻るボタン設定
						let back = elt("input", {type: "button",id: "back", value: "戻る"});
						createBoardForm.appendChild(back);
						document.getElementById("back").addEventListener("click",bulletinBoardCreate,false);

						//掲示板作成の確定ボタン設定
						let createConfirm = elt("input", {type: "submit", value: "確定"});
						createBoardForm.appendChild(createConfirm);

						//掲示板のタイトルが他のと重複していないかのチェック
						document.getElementById("title").addEventListener("blur",titleCheck,false);

						//掲示板作成時の入力値チェック
						let boardForm = document.getElementById("boardForm");
						boardForm.addEventListener("submit",function(e) {
							let title = document.getElementById("title").value;
							console.log(titleUseble);

							if(counter === 1) {
								let tag = document.getElementById("tag1").value;
								if(tag.length <= 0) {
									console.log("dodo");
									e.preventDefault();
								}
							}else{
								for(let i = 1; i < counter; i++) {
									let tag = document.getElementById("tag"+ i).value;
									let nextTag = document.getElementById("tag" + (i+1)).value;

									if(tag === nextTag) {
										console.log("bobo");
										e.preventDefault();
									}

									if(tag.length <= 0 || nextTag.length <= 0) {
										console.log("vovo");
										e.preventDefault();
									}
								}
							}
							console.log("1");

							if(titleUseble) {
								e.preventDefault();
							}
							console.log("2");

							if(title.length <= 0) {
								e.preventDefault();
							}
							console.log("3");
						},false);
					},false);
				}
			};

			//自身の作成した掲示板の情報を取得
			req.open("GET","/PURE/GetMyBulletinBoardServlet");
			req.send(null);
		}

		//掲示板のタイトル検索
		function titleCheck(){
			let req = new XMLHttpRequest();
			let getTitle = document.getElementById("title").value;

			req.onreadystatechange = function() {
				if (req.readyState == 4 && req.status == 200) {
					if (!(JSON.parse(req.response))) {
						titleUseble = true;
					} else {
						titleUseble = false;
					}
				}
			};

			//PassCheckServletに入力されたPassを送信
			req.open("GET","/PURE/TitleCheckServlet?title=" + getTitle);
			req.send(null);
		}

		//お気に入り画面の表示
		favorite.addEventListener("click",function(){
			menu.textContent = null;

			//お気に入りの掲示板があるかのチェック
			let req = new XMLHttpRequest();

			req.onreadystatechange = function() {
				if (req.readyState == 4 && req.status == 200) {
					favoriteBulletinBoard = JSON.parse(req.response);

					for(let favoriteBoard of favoriteBulletinBoard) {
						let inputTagP = elt("p");
						menu.appendChild(inputTagP);
						inputTagP.appendChild(elt("a", {href: "/PURE/BulletinBoardServlet?id="+favoriteBoard.id}, favoriteBoard.title));
					}
				}
			};

			req.open("GET","/PURE/FavoriteServlet");
			req.send(null);
		},false);

		//コメントの履歴を表示
		commentHistory.addEventListener("click",function(){
			menu.textContent = null;
			let req = new XMLHttpRequest();

			req.onreadystatechange = function() {
				if (req.readyState == 4 && req.status == 200) {
					let myCommentBulletinBoardList = JSON.parse(req.response);

					for(let myCommentBulletinBoard of myCommentBulletinBoardList) {
						for(let comment of myCommentBulletinBoard.commentList) {
								let inputTagP = elt("p");
								menu.appendChild(inputTagP);
								inputTagP.appendChild(elt("a", {href: "/PURE/BulletinBoardServlet?id="+myCommentBulletinBoard.id}, comment.comment));
						}
					}
				}
			};

			//PassCheckServletに入力されたPassを送信
			req.open("GET","/PURE/GetMyCommentServlet");
			req.send(null);
		},false);

		//要素の作成
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

	</script>
</body>
</html>