<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.BulletinBoard" %>
<%@ page import="model.BulletinBoardList" %>
<%
BulletinBoardList bulletinBoards = (BulletinBoardList) request.getAttribute("bulletinBoards");
String keyword = (String)request.getAttribute("keyword");
String target = (String)request.getAttribute("target");
%>
<!DOCTYPE html>
<html>
  <head>
      <meta charset="utf-8">
      <title>PURE</title>
      <link rel="stylesheet" href="test.css">
  </head>
  <body>
    <header>
        <h1><a href="/PURE/HomeServlet">PURE</a></h1>
    </header>
    <form id="sea" action="/PURE/SearchBulletinBoardServlet">
        <select name="searchSelect">
            <option value="1" <%= (target != null && target.equals("1")) ? "selected" : "" %>> タグ</option>
            <option value="2" <%= (target != null && target.equals("2")) ? "selected" : "" %>>掲示板</option>
        </select>
        <input type="search" id="search" name="search" value="<%= keyword == null ? "" : keyword %>" placeholder="キーワードを入力" style="width:300px;">
        <input type="submit" id="submit" name="submit" value="検索">
    </form>
    <p><%= bulletinBoards == null ? 0 : bulletinBoards.size() %>件</p>
    <hr>
    <div id="searchResult">
	    <% if(bulletinBoards != null) { %>
			<% for(int i = 0; i < bulletinBoards.size(); i++) { %>
				<p><a href="/PURE/BulletinBoardServlet?id=<%= bulletinBoards.get(i).getId() %>"><%= bulletinBoards.get(i).getTitle() %></a></p>
			<% } %>
		<% } %>
    </div>
    <script>
  		document.addEventListener('DOMContentLoaded',function() {
  			let result = document.getElementById("searchResult");

  			document.getElementById("submit").addEventListener("submit",function(e) {
  				let inputText = document.getElementById("search");

  				if(inputText.value.length <= 0) {
  					result.appendChild(elt("p", null, "キーワードを入力してください入力してください"));
  					e.preventDefault();
  				}
  			},false)
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
