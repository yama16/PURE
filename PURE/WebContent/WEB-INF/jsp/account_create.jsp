<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8">
<title>アカウント作成</title>
</head>
<body>

	<!--入力フォーム-->
	<form action="AccountCreateServlet" method="post" id="form">
		ニックネーム：<input type="text" name="nicname" id="nicname">
		<p>
			<output id="errorNicname"></output>
		</p>
		ID:<input type="text" name="id" id="id">
		<p>
			<output id="errorId"></output>
		</p>
		<br> パスワード：<input type="password" name="pass" id="pass">
		<p>
			<output id="errorPass"></output>
		</p>
		<br> パスワード再入力：<input type="password" name="passConfirm"
			id="passConfirm">
		<p>
			<output id="errorPassConfirm"></output>
		</p>
		<br> キャプチャー<br> <input type="submit" value="作成する"> <input
			type="button" value="テスト" id="test">
		<p>
			<output id="errorForm"></output>
		</p>
	</form>
	<script>
		let id = document.getElementById("id");
		let test = document.getElementById("test");
		let nicname = document.getElementById("nicname");
		let pass = document.getElementById("pass");
		let passConfirm = document.getElementById("passConfirm");
		let form = document.getElementById("form");

		//入力されたときに実行
		id.oninput = function() {
			let errorId = document.getElementById("errorId");
			let req = new XMLHttpRequest();

			if(id.value.length < 6) {
				errorId.innerHTML = "文字数が足りていません";
				return;
			}else if(id.value.length > 12) {
				errorId.innerHTML = "登録できる文字数を超えています";
				return;
			}else{
				errorId.innerHTML = null;
			}

			req.onreadystatechange = function() {
				if (req.readyState == 4) {
					if (req.status == 200) {
						//IDが使われていたら注意メッセージを表示
						if (JSON.parse(req.response)) {
							errorId.innerHTML = "既にそのIDは使われています";
						} else {
							errorId.innerHTML = null;
						}
					}
				}
			};

			//IdCheckServletに入力されたIDを送信
			req.open("GET", "/PURE/IdCheckServlet?name="
					+ encodeURIComponent(id.value), true);
			req.send(null);
		};

		nicname.oninput = function() {
			let errorMsg = document.getElementById("errorNicname");
			if (!nicname.value) {
				errorMsg.innerHTML = "未入力です";
			} else if (nicname.value.length > 10) {
				errorMsg.innerHTML = "登録できる文字数を超えています";
			} else {
				errorMsg.innerHTML = null;
			}
		};

		pass.oninput = function() {
			let errorMsg = document.getElementById("errorPass");
			if (pass.value.length < 8) {
				errorMsg.innerHTML = "文字数が足りていません";
			} else if (pass.value.length > 16) {
				errorMsg.innerHTML = "登録できる文字数を超えています";
			} else {
				errorMsg.innerHTML = null;
			}
		};

		passConfirm.oninput = function() {
			let errorMsg = document.getElementById("errorPassConfirm");
			if (passConfirm.value.length < 8) {
				errorMsg.innerHTML = "文字数が足りていません";
			} else if (passConfirm.value.length > 16) {
				errorMsg.innerHTML = "登録できる文字数を超えています";
			} else {
				errorMsg.innerHTML = null;
			}
		};

		form.onsubmit = function() {
			let errorForm = document.getElementById("errorForm");
			if ((nicname.value.length < 1) || (nicname.value.length > 10)
					|| (id.value.length < 6) || (id.value.length > 12)
					|| (pass.value.length < 8) || (pass.value.length > 16)
					|| (passConfirm.value.length < 8) || (passConfirm.value.length > 16)) {

				return false;
			}else if(pass.value !== passConfirm.value) {

				return false;
			}else{
				return true;
			}
		}
	</script>
</body>
</html>