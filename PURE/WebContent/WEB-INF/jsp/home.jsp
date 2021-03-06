<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Account" %>
<%@ page import="model.BulletinBoard" %>
<%@ page import="model.BulletinBoardList" %>
<%@ page import="model.CommentList" %>
<%
Account account = (Account) session.getAttribute("account");
BulletinBoardList newList = (BulletinBoardList) request.getAttribute("newList");
BulletinBoardList rankingList = (BulletinBoardList) request.getAttribute("rankingList");
CommentList commentList;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="home.css">
<title>ホーム</title>
</head>
<body>
	<div id="page">
		<header>
			<img alt="" src="pure_logo.png" height = "220" width = "500">
		</header>

		<nav>
          <ul>
            <li><a href="/PURE/HomeServlet">TOP</a></li>
            <li><a href="/PURE/RankingServlet">ランキング</a></li>
            <li><a href="/PURE/SearchBulletinBoardServlet">検索</a></li>
            <% if(account == null) { %>
            	<li><a href="/PURE/LoginServlet">ログイン</a></li>
            	<li><a href="/PURE/CreateAccountServlet">新規作成</a></li>
            <% }else{ %>
            	<li><a href="/PURE/AccountHomeServlet">アカウントホーム</a></li>
            	<li><a href="/PURE/LogoutServlet">ログアウト</a></li>
            <% } %>
          </ul>
        </nav>

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
					<p><a href="/PURE/BulletinBoardServlet?id=<%= newList.get(i).getId() %>"><%= newList.get(i).getTitle() %></a></p>
				<% } %>
			</div>
		</section>
		</main>

		<!--                       ランキング                               -->
		<div id="sub">
			<!--ランキング-->
			<h2><a href="/PURE/RankingServlet">ランキング</a></h2>
			<div id="ranking">
				<% for(int i = 0; i < rankingList.size(); i++) { %>
					<p><a href="/PURE/BulletinBoardServlet?id=<%= rankingList.get(i).getId() %>"><%= rankingList.get(i).getTitle() %></a></p>
				<% } %>
			</div>
		</div>
	</div>

	<script>
	document.addEventListener('DOMContentLoaded', function(){

		let nowTime = Date.now();
		let rialtime = document.getElementById("realtime");
		let commentList = new Array();

		setInterval(getComment, 5000);
		setInterval(function(){
			if(commentList[0]) {;
				let comment = commentList[0];
				console.log(comment)

				let inputTagP = elt("p", null, comment.boardTitle + ":");
				rialtime.appendChild(inputTagP);
				inputTagP.appendChild(elt("a", {href: "/PURE/BulletinBoardServlet?id=" + comment.boardId}, comment.comment));

				commentList.shift();
			}
		},1000);

		function getComment(){
			let bulletinBoardList;
			let req = new XMLHttpRequest();

			req.onreadystatechange = function() {
				if (req.readyState == 4 && req.status == 200) {
					bulletinBoardList = JSON.parse(req.response);
					console.log(bulletinBoardList);

					for(let bulletinBoard of bulletinBoardList) {
						for(let commentObject of bulletinBoard.commentList) {
							comment = {boardId: bulletinBoard.id, boardTitle: bulletinBoard.title, comment: commentObject.comment};
							commentList.push(comment);
						}
					}
				}
			};

			req.open("GET","/PURE/GetRealTimeCommentServlet?time=" + nowTime);
			req.send(null);
			nowTime = Date.now();
			console.log(nowTime);
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