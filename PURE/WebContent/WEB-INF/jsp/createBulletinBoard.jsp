<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PURE</title>
</head>
<body>
	<h1>PURE</h1>
    <div id="commentField">
      <div id="title"><p>掲示板タイトル</p></div>
    </div>

    <form id="inputComment">
      <label>ここに入力：</label><br>
      <textarea id="inputField" rows="5" cols="40" maxlength="200"></textarea><br>
      <input id="sendButton" type="button" value="送信">
    </form>

    <footer>
      <address></address>
    </footer>

</body>
</html>