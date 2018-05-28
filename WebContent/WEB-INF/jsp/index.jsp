<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8">
<title>アカウント作成</title>
</head>
<body>
	<div id="page" >
    <h1><a href="file:///C:/Users/matsumoto/Desktop/pomu/login.html">PURE</a></h1>
<!--                        作成項目                          -->
    <form action="/createAccount" method="post">
        <ul id="form">
            <li class="nickname">
                <label for="nickname">ニックネーム：</label>
                <input type="text" id="nickname" name="nickname" required size="25" maxlength="10" >
            </li>
<!--                          ログイン    -->
            <li class="user_id">
                <label for="user_id">ユーザーID：</label>
                <input type="text" id="user_id" name="user_id" pattern="^[0-9a-z]+$" size="25" maxlength="16" >
            </li>
            <!--                 パスワード                       -->
            <li class="password">
                <label for="password">パスワード:</label>
                <input type="password" id="password" size="25" maxlength="16" />
            </li>
            <li class="passwordConfirm">
                <label for="passwordConfirm">パスワード再入力:</label>
                <input type="password" id="passwordConfirm" size="25" maxlength="16"  />
            </li>
<!--                        メアド                           -->
            <li class="email">
                <label for="email">メールアドレス：</label>
                <input type="email" id="email" name="email" size="25" maxlength="40">
            </li>
            <li class="submit">
                <input type="submit" id="submit" value="アカウント作成">
            </li>
        </ul>
    </form>
</div>
</body>
</html>