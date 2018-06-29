<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.BulletinBoard" %>
<%@ page import="model.BulletinBoardList" %>
<%
BulletinBoardList newList = (BulletinBoardList) request.getAttribute("newList");
BulletinBoardList rankingList = (BulletinBoardList) request.getAttribute("rankingList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8">
<title>ホーム</title>
</head>
<body>
	<div id="page">
		<header>
			<a href="#"><h1>PURE</h1></a>

		</header>
		<main>
		<section>
			<h2>リアルタイム</h2>
			<div id="realtime">

			</div>
		</section>
		<section>
			<h2>最新</h2>
			<div id="new">
				<% for(int i = 0; i < newList.size(); i++) { %>
					<p><%= newList.get(i).getTitle() %></p>
				<% } %>
			</div>
		</section>
		</main>
		<!--                        ログイン・検索                         -->
		<div id="login">
			<fieldset>
				<a href="/PURE/LoginServlet">ログイン</a><br> <a href="#">新規の方はこちら！！！</a><br>
			</fieldset>
			<fieldset>
				<input type="search" name="search" placeholder="タグ名を入力"> <input
					type="submit" name="submit" value="検索">
			</fieldset>
		</div>
		<!--                       ランキング                               -->
		<div id="sub">
			<!--ランキング-->
			<h2><a href="/PURE/RankingServlet">ランキング</a></h2>
			<div id="ranking">
				<% for(int i = 0; i < rankingList.size(); i++) { %>
					<p><%= rankingList.get(i).getTitle() %></p>
				<% } %>
			</div>
		</div>
	</div>
	<form action="/PURE/LoginServlet" method="get">
		<input type="submit" value="ログイン">
	</form>
	<form action="/PURE/CreateAccountServlet" method="get">
		<input type="submit" value="アカウント作成">
	</form>
	<script>

	document.addEventListener('DOMContentLoaded', function(){
		setInterval(getComment, 5000);
	},false);

	function getComment(){
		let req = new XMLHttpRequest();
		let nowPass = document.getElementById("nowPass");

		req.onreadystatechange = function() {
			if (req.readyState == 4 && req.status == 200) {
				console.log("wqwq");
			}
		};

		//PassCheckServletに入力されたPassを送信
		req.open("GET","/PURE/GetRialcommentServlet");
		req.send(null);
	}
	</script>
</body>
</html>