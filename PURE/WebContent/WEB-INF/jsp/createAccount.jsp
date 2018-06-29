<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Account" %>
<%
Account account = (Account)session.getAttribute("createAccount");
String nickname = "";
String id = "";
String pass = "";
if(account != null){
	nickname = account.getNickname();
	id = account.getId();
	pass = account.getPassword();
}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>アカウント作成</title>
</head>
<body>
	<!--入力フォーム-->
	<form id="form" action="ConfirmCreateAccountServlet" method="post">
		<p>
			ニックネーム：<input id="nickname" type="text" name="nickname" value=<%= nickname %>>
			<output id="nicknameErrMsg"></output>
		</p>
		<p>
			ID:<input id="id" type="text" name="id" value=<%= id %>>
			<output id="idErrMsg"></output>
		</p>
		<p>
			パスワード：<input id="pass" type="password" name="pass" value=<%= pass %>>
			<output id="passErrMsg"></output>
		</p>
		<p>
			パスワード再入力：<input id="passConfirm" type="password" name="passConfirm">
			<output id="passConfirmErrMsg"></output>
		</p><br>

		<input type="submit" value="作成">
	</form>

	<script>
	document.addEventListener('DOMContentLoaded', function(){
		const id = document.getElementById("id");
		const nickname = document.getElementById("nickname");
		const pass = document.getElementById("pass");
		const passConfirm = document.getElementById("passConfirm");
		const form = document.getElementById("form");

		const nicknameErrMsg = document.getElementById("nicknameErrMsg");
		const idErrMsg = document.getElementById("idErrMsg");
		const passErrMsg = document.getElementById("passErrMsg");
		const passConfirmErrMsg = document.getElementById("passConfirmErrMsg");

		let idUsable = false;

		//
		id.oninput = idCheck;
		nickname.oninput = nicknameCheck;
		pass.oninput = passCheck;
		passConfirm.oninput = passConfirmCheck;

		//送信時に入力の不具合がある場合送信を中止
		form.onsubmit = function() {
			let isAppropriate = true;
			let isPassLenAppropriate = true;
			if(!nicknameCheck()){
				isAppropriate = false;
			}
			if(!idCheck()){
				isAppropriate = false;
			}
			if(!passCheck()){
				isAppropriate = false;
				isPassLenAppropriate = false;
			}
			if(!passConfirmCheck()){
				isAppropriate = false;
				isPassLenAppropriate = false;
			}
			if(isPassLenAppropriate && pass.value !== passConfirm.value){
				errMsgAppend(passConfirmErrMsg, "パスワードと確認パスワードが違う");
				isAppropriate = false;
			}
			if(idUsable){
				isAppropriate = false;
			}

			return isAppropriate;
		};


		function idUsableCheck(){
			let req = new XMLHttpRequest();
			req.onreadystatechange = function() {
				if (req.readyState == 4 && req.status == 200) {
					//IDが使われていたら注意メッセージを表示
					if (JSON.parse(req.response)) {
						errMsgAppend(idErrMsg, "既にそのIDは使われています");
						idUsable = true;
					} else {
						idErrMsg.textContent = null;
						idUsable = false;
					}
				}
			};

			//IdCheckServletに入力されたIDを送信
			req.open("GET", "/PURE/IdUsableCheckServlet?id=" + encodeURIComponent(id.value), true);
			req.send(null);
		}


		function idCheck(){
			let regexStr = /^[a-zA-Z0-9]*$/;
			if(!inputFormatCheck(regexStr, id, idErrMsg)){
				return false;
			}
			if(!inputLenCheck(6, 12, id, idErrMsg)){
				return false;
			}
			idUsableCheck();
			return true;
		}
		function nicknameCheck() {
			if(!inputLenCheck(1, 10, nickname, nicknameErrMsg)){
				return false;
			}
			return true;
		}
		function passCheck() {
			let regexStr = /^[a-zA-Z0-9]*$/;
			if(!inputFormatCheck(regexStr, pass, passErrMsg)){
				return false;
			}
			if(!inputLenCheck(8, 16, pass, passErrMsg)){
				return false;
			}
			return true;
		}
		function passConfirmCheck() {
			let regexStr = /^[a-zA-Z0-9]*$/;
			if(!inputFormatCheck(regexStr, passConfirm, passConfirmErrMsg)){
				return false;
			}
			if(!inputLenCheck(8, 16, passConfirm, passConfirmErrMsg)){
				return false;
			}
			return true;
		}


		//
		function inputLenCheck(low, high, checkNode, errMsgNode){
			if(checkNode.value.length < low){
				errMsgAppend(errMsgNode, "文字数が足りていません");
				return false;
			}else if(checkNode.value.length > high){
				errMsgAppend(errMsgNode, "登録できる文字数を超えています");
				return false;
			}else{
				errMsgNode.textContent = null;
				return true;
			}
		}

		/**
		 *
		 * @param {object} 正規表現
		 * @param {object} 正規表現でチェックする要素
		 * @param {object} 正規表現に従っていなかった場合にメッセージを表示する要素
		 * @return {boolean} 正規表現に従っていればtrue。従っていなければfalse。
		 */
		function inputFormatCheck(regexStr, checkNode, errMsgNode){
			let regex = new RegExp(regexStr);
			if(regex.test(checkNode.value)){
				return true;
			}else{
				errMsgAppend(errMsgNode, "使用できない文字が含まれています。");
				return false;
			}
		}

		/**
		 * errMsgNodeにerrMsgを表示する。
		 * @param {Object} errMsgNode メッセージを表示する要素
		 * @param {String} errMsg 表示するメッセージ
		 */
		function errMsgAppend(errMsgNode, errMsg){
			let errMsgTextNode = document.createTextNode(errMsg);
			errMsgNode.textContent = null;
			errMsgNode.appendChild(errMsgTextNode);
		}
	}, false);
	</script>
</body>
</html>