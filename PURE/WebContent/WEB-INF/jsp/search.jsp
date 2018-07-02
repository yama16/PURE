<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
      <meta charset="utf-8">
      <title>PURE</title>
      <link rel="stylesheet" href="test.css">
  </head>
  <body>
    <header>
        <a href="#"><h1>PURE</h1></a>
    </header>
    <form id="sea">
        <select id="searchSelect">
            <option value="1"> タグ</option>
            <option value="2">掲示板</option>
        </select>
            <input type="search" id="search" name="search" placeholder="キーワードを入力" style="width:300px;">
            <input type="button" id="submit" name="submit" value="検索">
            <hr>
    </form>
    <div id="searchResult">

    </div>
    <script>
    	document.addEventListener('DOMContentLoaded', function(){
    		let keyword = document.getElementById("search");
    		let search = document.getElementById("submit");
    		let select = document.getElementById("searchSelect");
    		let result = document.getElementById("searchResult");
    		let target;

    		search.addEventListener("click",function() {
    			result.textContent = null;

    			for(let option of select.children) {
        			if(option.selected) {
        				target = option.value;
        			}
        		}


    	    	let req = new XMLHttpRequest();

    			req.onreadystatechange = function() {
    				if (req.readyState == 4 && req.status == 200) {
    					let searchBulletinBoardList = JSON.parse(req.response);

    					for(let bulletinBoard of searchBulletinBoardList) {
    						let resultBoard = elt("div", {id: "resultBoard"});
    						let inputTagP = elt("p");
    						result.appendChild(resultBoard);
    						resultBoard.appendChild(inputTagP);
    						inputTagP.appendChild(elt("a", {href: "/PURE/BulletinBoardServlet?id=" + bulletinBoard.id},bulletinBoard.title));

    						for(let tag of bulletinBoard.tagList) {
								resultBoard.appendChild(elt("p", null, tag));
    						}
    					}
    				}
    			};

    			req.open("GET","/PURE/SearchBulletinBoardServlet?target=" + target + "&keyword=" + encodeURIComponent(keyword.value));
    			req.send(null);
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
