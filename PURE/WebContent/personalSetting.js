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