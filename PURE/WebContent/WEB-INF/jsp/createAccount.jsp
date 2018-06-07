<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
			ニックネーム：<input id="nickname" type="text" name="nickname">
			<output id="nicknameErrMsg"></output>
		</p>
		<p>
			ID:<input id="id" type="text" name="id">
			<output id="idErrMsg"></output>
		</p>
		<p>
			パスワード：<input id="password" type="password" name="password">
			<output id="passwordErrMsg"></output>
		</p>
		<p>
			パスワード再入力：<input id="passwordConfirm" type="password" name="passConfirm">
			<output id="passwordConfirmErrMsg"></output>
		</p><br>
		キャプチャー<br>
		<input type="submit" value="作成">
	</form>

	<script>
	document.addEventListener('DOMContentLoaded', function(){
		const id = document.getElementById("id");
		const nickname = document.getElementById("nickname");
		const password = document.getElementById("password");
		const passwordConfirm = document.getElementById("passwordConfirm");
		const form = document.getElementById("form");

		const nicknameErrMsg = document.getElementById("nicknameErrMsg");
		const idErrMsg = document.getElementById("idErrMsg");
		const passwordErrMsg = document.getElementById("passwordErrMsg");
		const passwordConfirmErrMsg = document.getElementById("passwordConfirmErrMsg");

		//
		id.oninput = idCheck;
		nickname.oninput = nicknameLengthCheck;
		password.oninput = passwordLengthCheck;
		passwordConfirm.oninput = passwordConfirmLengthCheck;

		//送信時に入力の不具合がある場合送信を中止
		form.onsubmit = function() {
			let isUnappropriate = false;
			if(!nicknameLengthCheck()){
				isUnappropriate = true;
			}
			if(!idLengthCheck()){
				isUnappropriate = true;
			}
			if(!passwordLengthCheck()){
				isUnappropriate = true;
			}
			if(!passwordConfirmLengthCheck()){
				isUnappropriate = true;
			}

			if (isUnappropriate) {
				return false;
			}else if(password.value !== passwordConfirm.value) {
				errMsgAppend(passwordConfirmErrMsg, "パスワードと確認パスワードが違う");
				return false;
			}else{
				return true;
			}
		};

		function idCheck() {
			//入力文字数をチェック
			if(!idLengthCheck()){
				return false;
			}

			let req = new XMLHttpRequest();
			req.onreadystatechange = function() {
				if (req.readyState == 4 && req.status == 200) {
					//IDが使われていたら注意メッセージを表示
					if (JSON.parse(req.response)) {
						errMsgAppend(idErrMsg, "既にそのIDは使われています");
					} else {
						idErrMsg.textContent = null;
					}
				}
			};

			//IdCheckServletに入力されたIDを送信
			req.open("GET", "/PURE/IdUsableCheckServlet?id=" + encodeURIComponent(id.value), true);
			req.send(null);
		}

		//
		function inputLengthCheck(low, high, checkNode, errMsgNode){
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

		function errMsgAppend(errMsgNode, errMsg){
			let errMsgTextNode = document.createTextNode(errMsg);
			errMsgNode.textContent = null;
			errMsgNode.appendChild(errMsgTextNode);
		}

		function idLengthCheck(){
			return inputLengthCheck(6, 12, id, idErrMsg);
		}
		function nicknameLengthCheck() {
			return inputLengthCheck(1, 10, nickname, nicknameErrMsg);
		}
		function passwordLengthCheck() {
			return inputLengthCheck(8, 16, password, passwordErrMsg);
		}
		function passwordConfirmLengthCheck() {
			return inputLengthCheck(8, 16, passwordConfirm, passwordConfirmErrMsg);
		}
	}, false);
	</script>
</body>
</html>