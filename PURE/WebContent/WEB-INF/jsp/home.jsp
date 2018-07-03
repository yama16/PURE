<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.BulletinBoard" %>
<%@ page import="model.BulletinBoardList" %>
<%@ page import="model.CommentList" %>
<%
BulletinBoardList newList = (BulletinBoardList) request.getAttribute("newList");
BulletinBoardList rankingList = (BulletinBoardList) request.getAttribute("rankingList");
CommentList commentList;
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
			<h1>PURE</h1>
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
				<a href="/PURE/SearchBulletinBoardServlet">検索</a>
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

		let nowTime = Date.now();
		let rialtime = document.getElementById("realtime");
		let commentList = new Array();

		setInterval(getComment, 5000);
		setInterval(function(){
			for(let comment of commentList) {

				rialtime.appendChild(elt("p", null, comment));

				bulletinBoards.shiht();
			}
		},1000);

		function getComment(){
			let bulletinBoardList;
			let req = new XMLHttpRequest();

			req.onreadystatechange = function() {
				if (req.readyState == 4 && req.status == 200) {
					bulletinBoardList = JSON.parse(req.response);

					for(let bulletinBoard of bulletinBoardList) {
						for(let comment of bulletinBoard.commentList) {
							comment = {id: bulletinBoard.id, title: bulletinBoard.title, comment: comment};
							commentList.push(comment);
						}
					}
				}
			};

			req.open("GET","/PURE/GetRealTimeCommentServlet?time=" + nowTime);
			req.send(null);
			nowTime = Date.now();
		}
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