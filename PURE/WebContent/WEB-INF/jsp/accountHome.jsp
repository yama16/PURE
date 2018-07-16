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
			<p><a href="/PURE/LogoutServlet">ログアウト</a></p>
		</header>
			<input type="button" value="個人設定" id="personal" style="width:200px; height:30px;"><br>
			<input type="button" value="掲示板" id="bulletinBoard" style="width:200px; height:30px;"><br>
			<input type="button" value="お気に入り" id="favorite" style="width:200px; height:30px;"><br>
			<input type="button" value="コメント履歴" id="commentHistory" style="width:200px; height:30px;">
		<div id="accountMenu"></div>
	</div>
</body>
<script>
	let menu = document.getElementById("accountMenu");
	let personal = document.getElementById("personal");
	let bulletinBoard = document.getElementById("bulletinBoard");
	let favorite = document.getElementById("favorite");
	let commentHistory = document.getElementById("commentHistory");
	let favoriteBulletinBoard;
	let passUseble = false;

	// ページを読み込んだ時個人設定画面を表示
	window.onload = personalSetting;

	//個人設定画面を表示
	personal.addEventListener("click",personalSetting,false);

	//個人設定画面の作成
	function personalSetting() {
		menu.textContent = null;

		//個人設定メニューの表示
		menu.appendChild(elt("h2", null, "個人設定画面"));

		//現在のニックネームを表示
		let nicknameDisplay = elt("p", null, "現在のニックネーム: <%= nickname %>");
		let nicknameChangeButton = elt("input", {type: "button", id: "nicknameChange", value: "ニックネーム変更"});
		menu.appendChild(nicknameDisplay);
		menu.appendChild(nicknameChangeButton);
		console.log("ok");

		//現在のIDを表示
		let idDisplay = elt("p", null, "現在のID: <%= id %>");
		menu.appendChild(idDisplay);

		//パスワード変更
		let passChangeButton = elt("input", {type: "button", id: "passChange", value: "パスワード変更"});
		menu.appendChild(passChangeButton);

		//アカウント作成日時を表示
		let createTimeDisplay = elt("p", null, "作成日時："+"<%= createTime %>");
		menu.appendChild(createTimeDisplay);

		//アカウント更新日時を表示
		let updateTimeDisplay = elt("p", null, "最終更新日："+"<%= updateTime %>");
		menu.appendChild(updateTimeDisplay);

		//アカウント削除ボタンの設定
		let accountDeleteForm = elt("form", {action: "/PURE/AccountDeleteConfirmServlet", method: "get"});
		menu.appendChild(accountDeleteForm);
		accountDeleteForm.appendChild(elt("input", {type: "submit", value: "アカウント削除"}));

		//ニックネーム変更ボタンが押されたら入力ページを表示
		let nickname = document.getElementById("nicknameChange");
		nickname.addEventListener("click",nicknameChange,false);

		//パスワード変更ボタンが押されたら入力ページを表示
		let pass = document.getElementById("passChange");
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
		let guideDisplayMsg = elt("p", null, "新しいニックネームを入力してください(1～10文字)");
		let inputNewNickname = elt("input",{type: "text", id: "newNickname", name: "newNickname"});
		inputNicknameForm.appendChild(guideDisplayMsg);
		inputNicknameForm.appendChild(inputNewNickname);

		//送信ボタンの設定
		let changeSubmit = elt("input",{type: "submit", id: "changeSubmit", value: "変更"});
		inputNicknameForm.appendChild(changeSubmit);

		//戻るボタン設定
		let back = elt("input", {id: "back", type: "button", value: "戻る"});
		inputNicknameForm.appendChild(back);
		document.getElementById("back").addEventListener("click",personalSetting,false);

		//エラーメッセージを表示する欄の作成
		let errorMsgDisplay = elt("div", {id: "errorMsg"});
		inputNicknameForm.appendChild(errorMsgDisplay);

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
				errorMsgDisplay.textContent = null;
				errorMsgDisplay.appendChild(elt("p", null, "※ニックネームの入力が正しくありません"));
				e.preventDefault();
			}
		},false);

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
		let inputNewPass = elt("input",{type: "password", id: "newPass", name: "newPass"});
		inputPassForm.appendChild(guideNewDisplay);
		inputPassForm.appendChild(inputNewPass);

		//確認パスワードを入力
		let guideConfirmDisplay = elt("p", null, "もう一度入力してください");
		let inputConfirmPass = elt("input",{type: "password", id: "confirmPass", name: "confirmPass"});
		inputPassForm.appendChild(guideConfirmDisplay);
		inputPassForm.appendChild(inputConfirmPass);

		//戻るボタン
		let back = elt("input", {type: "button",id: "back", value: "戻る"});
		inputPassForm.appendChild(back);
		document.getElementById("back").addEventListener("click",personalSetting,false);

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
				inputPassForm.appendChild(elt("p", null, "パスワードの入力が正しくありません"));
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
				menu.appendChild(elt("h2", null, "現在立てている掲示板"));
				let myBulletinBoard = JSON.parse(req.response);
				console.log("現在立てている掲示板は"+myBulletinBoard.length);

				//自身の立てている掲示板がある場合表示
				if(myBulletinBoard.length > 0) {

					for(let board of myBulletinBoard) {
						let bulletinBoardDisplay = elt("div", {id: "boardId_"+board.id});
						let inputTitleP = elt("p");
						menu.appendChild(bulletinBoardDisplay);
						bulletinBoardDisplay.appendChild(inputTitleP);
						inputTitleP.appendChild(elt("a", {href: "/PURE/BulletinBoardServlet?id="+board.id}, board.title));

						//掲示板のタグの表示
						for(let tag of board.tagList) {
							let inputTagP = elt("p", null, "tag:");
							inputTagP.appendChild(elt("a", {href: "/PURE/SearchBulletinBoardServlet?search="+tag+"&searchSelect=1"}, tag));
							bulletinBoardDisplay.appendChild(inputTagP);
						}

						//送信するためのフォームの作成
						bulletinBoardDisplay.appendChild(elt("input", {type: "button", value: "タグ編集", id: "tagEditing_boardId_"+board.id}));
						let tagEditMenu = elt("form", {action: "/PURE/TagsUpdateServlet", method: "post", id: "tagEditMenu"});
						bulletinBoardDisplay.appendChild(tagEditMenu);

						//タグ編集ボタンが押されたら場合、編集するための各ボタンやタグを表示
						document.getElementById("tagEditing_boardId_"+board.id).addEventListener("click", function() {
							tagEditMenu.textContent = null;
							tagEditMenu.appendChild(elt("input", {type: "hidden", name: "boardId", value: board.id}));
							tagEdit(tagEditMenu, board.tagList, board.id);
							let tagEditCancel = elt("input", {type: "button", value: "取り消し", id: "tagEditCancel"});
							tagEditCancel.addEventListener("click", function() {
								tagEditMenu.textContent = null;
							},false);
							tagEditMenu.appendChild(tagEditCancel);
						},false);

						//タグ編集での送信の入力値チェック
						tagEditMenu.addEventListener("submit",function(e) {
							let boardTagsCounter = document.getElementById("tags_boardId_"+board.id).childElementCount;

							//未入力のタグがないかのチェック
							if(inputTagsCheck(boardTagsCounter)) {
								console.log("errorOK");
								e.preventDefault();
							}

						},false);

					}
				}else{
					let inputTagP = elt("h2", null, "現在作成立てている掲示板はありません");
					menu.appendChild(inputTagP);
				}

				//作成画面への遷移ボタンの設定
				let createBoardButton = elt("input",{type: "button", id: "createBoard", value: "作成"});
				menu.appendChild(createBoardButton);

				//掲示板作成画面の表示
				document.getElementById("createBoard").addEventListener("click",function() {
					menu.textContent = null;
					let counter = 0;

					//掲示板作成フォーム設定
					let createBoardForm = elt("form",{action: "/PURE/CreateBulletinBoardServlet", method: "post", id: "boardForm"});
					menu.appendChild(createBoardForm);

					//タイトル入力設定
					let inputTitleDisplay = elt("label", null, "タイトル入力:");
					let inputTitle = elt("input",{type: "text", id: "title", name: "title"});
					createBoardForm.appendChild(inputTitleDisplay);
					inputTitleDisplay.appendChild(inputTitle);

					//タグを追加するための各ボタンの表示
					tagEdit(createBoardForm);
					createBoardForm.appendChild(elt("br"));

					//戻るボタン設定
					let back = elt("input", {type: "button",id: "back", value: "戻る"});
					createBoardForm.appendChild(back);
					document.getElementById("back").addEventListener("click",bulletinBoardCreate,false);

					//掲示板のタイトルが他のと重複していないかのチェック
					let titleUseble = false;
					document.getElementById("title").addEventListener("blur",function() {
						let req = new XMLHttpRequest();
						let getTitle = document.getElementById("title").value;

						req.onreadystatechange = function() {
							if (req.readyState == 4 && req.status == 200) {
								if (!(JSON.parse(req.response))) {
									titleUseble = true;
								}
							}
						};

						//TitleCheckServletに入力されたタイトルを送信
						req.open("GET","/PURE/TitleCheckServlet?title=" + getTitle);
						req.send(null);
					},false);

					//掲示板作成時の入力値チェック
					createBoardForm.addEventListener("submit",function(e) {
						let title = document.getElementById("title").value;
						boardCounter = document.getElementById("tags_boardId_undefined").childElementCount;
						console.log(boardCounter);
						console.log(titleUseble);

						//入力した掲示板タイトルが既に登録されていた場合送信中止
						if(inputTagsCheck(boardCounter)) {
							console.log("errorInputTags");
							e.preventDefault();
						}

						if(titleUseble) {
							console.log("theSameTitle");
							e.preventDefault();
						}

						if(title.length <= 0) {
							console.log("NotInput");
							e.preventDefault();
						}

					},false);
				},false);

				//タグの重複や未入力のチェック
				function inputTagsCheck(counter) {
					console.log("現在のカウンタ"+counter);
					if(counter === 1) {
						let tag = document.getElementById("tag1").value;
						if(tag.length <= 0) {
							console.log("dodo");
							return true;
						}
					}else{
						for(let i = 1; i <= counter; i++) {
							let tag = document.getElementById("tag"+ i).value;

							for(let j = i; j < counter; j++) {
								let nextTag = document.getElementById("tag"+(j+1)).value;
								if(tag === nextTag) {
									console.log("bobo");
									return true;
								}
							}

							if(tag.length <= 0) {
								console.log("vovo");
								return true;
							}
						}
					}
					return false;
				}

				//タグ編集のための追加、削除、確定ボタンの作成
				function tagEdit(editingTag, tagList, boardId) {
					let inputTags = elt("div",{id: "tags_boardId_"+boardId});
					editingTag.appendChild(inputTags);
					let counter = 0;

					function tagEditAddInputField(tag) {
						counter++;
						console.log("タグADD"+counter);
						console.log("ok"+counter);
						let inputTagP = elt("p");
						let inputTagDisplay = elt("label", null, "タグ" + counter);
						let inputTag;
						if(tag) {
							inputTag = elt("input", {type: "text", id: "tag"+counter, name: "tag"+counter, value: tag});
						}else{
							inputTag = elt("input", {type: "text", id: "tag"+counter, name: "tag"+counter});
						}
						editingTag.appendChild(inputTags);
						inputTags.appendChild(inputTagP);
						inputTagP.appendChild(inputTagDisplay);
						inputTagDisplay.appendChild(inputTag);
					}

					if(tagList) {
						for(let tag of tagList) {
							tagEditAddInputField(tag);
						}
					}

					//タグ追加ボタンの設定
					let addTag = elt("input", {type: "button", id: "addTag", value: "タグ追加"});
					editingTag.appendChild(addTag);

					addTag.addEventListener("click", function(){

						if(counter < 6) {
							console.log("clickok");
							tagEditAddInputField();
						}

					}, false);


					//タグ削除ボタンの設定
					let tags = document.getElementById("tags");
					let removeTag = elt("input", {type: "button", id: "removeTag", value: "削除"});
					editingTag.appendChild(removeTag);

					removeTag.addEventListener("click",function(){
						if(counter >= 1) {
							counter--;
							inputTags.removeChild(inputTags.lastElementChild);
						}
					},false);

					//タグの確定ボタン設定
					let createConfirm = elt("input", {type: "submit", value: "確定"});
					editingTag.appendChild(createConfirm);

				}

			}
		};

		//自身の作成した掲示板の情報を取得
		req.open("GET","/PURE/GetMyBulletinBoardServlet");
		req.send(null);
	}



	//お気に入り画面の表示
	favorite.addEventListener("click",function(){
		menu.textContent = null;

		//お気に入りの掲示板があるかのチェック
		let req = new XMLHttpRequest();

		req.onreadystatechange = function() {
			if (req.readyState == 4 && req.status == 200) {
				menu.appendChild(elt("h2", null, "お気に入りの掲示板"));
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
				menu.appendChild(elt("h2", null, "コメント履歴"));
				let myCommentBulletinBoardList = JSON.parse(req.response);
				console.log(myCommentBulletinBoardLis);

				for(let myCommentBulletinBoard of myCommentBulletinBoardList) {
					for(let comment of myCommentBulletinBoard.commentList) {
							let inputTagP = elt("p", null, myCommentBulletinBoard.title + ":");
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
</html>