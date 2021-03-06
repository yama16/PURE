<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.BulletinBoard" %>
<%@ page import="model.Comment" %>
<%@ page import="model.CommentList" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Sanitize" %>
<%
Sanitize sanitize = new Sanitize();
// 掲示板とコメントリストを取得
BulletinBoard bulletinBoard = (BulletinBoard) session.getAttribute("bulletinBoard");
CommentList commentList = bulletinBoard.getCommentList();
// PUREされているコメントのリストを取得
List<Integer> pureCommentList = (List<Integer>) request.getAttribute("pureCommentList");
// 自分がPUREしているコメントのリストを取得
List<Integer> myPureCommentList = (List<Integer>) request.getAttribute("myPureCommentList");
// 掲示板をお気に入りに入れているか判断する
boolean isFavorite = (boolean) request.getAttribute("isFavorite");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <link rel="stylesheet" href="bulletinBoard.css">
    <title>PURE</title>
</head>
<body>
    <h1><a href="/PURE/HomeServlet"><img alt="" src="pure_logo.png" height = "220" width = "500"></a></h1>
    <!-- 　コメント欄　 -->
    <div id="commentField">
      <div id="title">
      	<h2><%= sanitize.execute(bulletinBoard.getTitle()) %></h2>
      	<input id="favoriteButton" type="button" value="<%= isFavorite==true?"お気に入り解除":"お気に入り登録" %>">
      </div>
    </div>

    <form id="inputComment">
      <label>コメント入力</label><br>
      <textarea id="inputField" rows="5" cols="40" maxlength="200"></textarea><br>
      <input id="sendButton" type="button" value="送信">
      <input id="clearButton" type="button" value="クリア">
    </form>

    <footer>
      <address></address>
    </footer>
</body>
<script type="text/javascript">
	// コメントを読み込む
	window.addEventListener('DOMContentLoaded', ()=>{getNewComment();});

    favoriteButton.addEventListener("click", function(e) {
		// 初期処理
      	let favorite = e.currentTarget;
      	let req = new XMLHttpRequest();
      	// リクエスト処理
      	req.onreadystatechange = function() {
        	if(req.readyState == 4 && req.status == 200) {
          		switch (JSON.parse(req.response)) {
            		case 0:

              		break;

            		case 1:
              		favorite.value = "お気に入り解除";
              		break;

            		case -1:
              		favorite.value = "お気に入り登録";
              		break;

            		default:
          		}
        	}
      	}
      	req.open("POST", "/PURE/UpdateFavoriteServlet");
      	req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
      	req.send(null);
    }, false)

    // 5秒ごとに更新情報を取得
    setInterval(getNewComment, 5000);

    // クリアボタン
    document.getElementById("clearButton").addEventListener("click", function() {
		let inputField = document.getElementById("inputField");
      	if(inputField.value) {
        	inputField.value = "";
      	}
    }, false);

    // 送信ボタンを押したらコメントを送信
    document.getElementById("sendButton").addEventListener("click", function() {
      	console.log("sendButton入力確認"); // テスト
      	// 初期処理
      	let text = document.getElementById("inputField");
      	let comment = text.value;
      	// textareaに文字列が入力されているかチェック
      	if(comment) {
        	let req = new XMLHttpRequest();
        	// リクエスト処理
        	req.onreadystatechange = function() {
          		if(req.readyState == 4 && req.status == 200) {
            		// データ受信成功時の処理
            		if(req.response) {
              			console.log("受信成功！！");
              			getNewComment();
            		}
            		text.value = ""; // 入力欄をクリア
          		}
        	}
        	req.open("POST", "/PURE/PostCommentServlet");
        	req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        	req.send( "comment=" + comment ); // コメントオブジェクトを送信
     	}
    }, false);

 	// コメントデータを受け取り画面に表示する処理
    function commentDisplay(commentList) {
      	// 初期処理
      	let commentField = document.getElementById("commentField");   // コメント欄のdiv要素取得

      	// コメントを表示またはコメントをPURE(未実装)
      	for(let cmtObj of commentList) {
      		let commentFrame = createCommentFrame(cmtObj);
    	   	commentField.appendChild( commentFrame );   // コメント枠をコメント欄に追加
			commentFrame.scrollIntoView(true);
      	}
    }

    function createCommentFrame(commentObject) {
		// 初期処理
      	let commentFrame = document.createElement("div");            // コメント枠を生成　
		let dlElement = document.createElement("dl");                // dl要素生成
		let dtElement = document.createElement("dt");                // dt要素生成
		let ddElementComment = document.createElement("dd");         // dd要素生成
		let smallElementCommentId = document.createElement("small"); // small要素(commentId)
      	let smallElementNickName = document.createElement("small");  // small要素(NickName)
      	let smallElementAccountId = document.createElement("small"); // small要素(AccountId)
      	let smallElementCreatedAt = document.createElement("small"); // small要素(CreatedAt)
      	let pureButton = document.createElement("input");            // input要素(PUREボタン)
      	pureButton.setAttribute("type", "button");
      	pureButton.setAttribute("value", "PURE");
		let replyButton = document.createElement("input");           // input要素(返信ボタン)
      	replyButton.setAttribute("type", "button");
      	replyButton.setAttribute("value", "返信");
      	commentFrame.setAttribute("class", "comment");               // コメント枠にclass属性を設定
      	commentFrame.setAttribute("id", commentObject.id);  		 // コメント枠にId属性(commentId)を設定

      	// span要素にcmtObjのフィールドを追加
      	smallElementCommentId.appendChild( document.createTextNode("No." + commentObject.id) );
      	smallElementNickName.appendChild( document.createTextNode(" " + commentObject.nickname) );
      	smallElementAccountId.appendChild( document.createTextNode(" ID:" + commentObject.accountId) );
      	smallElementCreatedAt.appendChild( document.createTextNode(" " + commentObject.createdAt) );
      	// dt要素にsmall要素を追加
      	dtElement.appendChild(smallElementCommentId);
      	dtElement.appendChild(smallElementNickName);
      	dtElement.appendChild(smallElementAccountId);
      	dtElement.appendChild(smallElementCreatedAt);

      	// linesを改行処理してdd要素にコメントを追加
      	let lines = commentObject.comment.replace(/\r\n|\r/g, "\n");      // 改行コードを"\n"に変換(変換は何でもいい)
      	lines = lines.split("\n");                                        // "\n"で分割して配列に
      	for(let i = 0; i < lines.length; i++) {
        	let cText = document.createTextNode( lines[i] );              // textNode生成
        	ddElementComment.appendChild(cText);                          // TextNodeをp要素に追加
        	ddElementComment.appendChild( document.createElement("br") ); // <br>を生成してp要素に追加
      	}

      	// dl要素にdt要素とdd要素を追加
      	dlElement.appendChild(dtElement);
      	dlElement.appendChild(ddElementComment);

      	// PUREボタンと返信ボタンにイベントハンドラを登録
      	// PUREボタン
      	pureButton.addEventListener("click", pureButtonEvent, false);
      	// 返信ボタン
      	replyButton.addEventListener("click", replyButtonEvent, false);

		// コメント枠に追加
		commentFrame.appendChild(dlElement);
		commentFrame.appendChild(pureButton);
		commentFrame.appendChild(replyButton);

      	return commentFrame;
    }

    function pureComment(pureCommentList) {
    	// 初期宣言
    	let pureComments = document.getElementsByClassName("pure"); // 現在PUREしているコメント
    	let index1 = pureCommentList.length - 1;
      	let index2 = pureComments.length - 1;



      	while(index1 >= 0 || index2  >= 0) {
      		if(index1 >= 0 && index2 <= -1) {
      			// コメントをPURE
      			let pureImg = document.createElement("img");                // imgタグ
      			document.getElementById(pureCommentList[index1]).lastElementChild.previousElementSibling.value = "PURE解除";
      			pureImg.setAttribute("class", "pureImg");
      			pureImg.setAttribute("src", "/PURE/pure_img.JPG");
      			document.getElementById(pureCommentList[index1]).appendChild(pureImg);
      			document.getElementById(pureCommentList[index1--]).classList.add("pure");
      			continue;
      		}
			if(index2 >= 0 && index1 <= -1) {
				// PUREを解除
				pureComments[index2].removeChild(pureComments[index2].querySelector("img"));
				pureComments[index2].lastElementChild.previousElementSibling.value = "PURE";
				pureComments[index2--].classList.remove("pure");
      			continue;
      		}
      		if(pureCommentList[index1] < Number(pureComments[index2].id)) {
      			// PUREを解除
      			pureComments[index2].removeChild(pureComments[index2].querySelector("img"));
      			pureComments[index2].lastElementChild.previousElementSibling.value = "PURE";
      			pureComments[index2--].classList.remove("pure");
      			continue;

      		} else if(pureCommentList[index1] > Number(pureComments[index2].id)) {
      			// コメントをPURE
      			let pureImg = document.createElement("img");                // imgタグ
      			document.getElementById(pureCommentList[index1]).lastElementChild.previousElementSibling.value = "PURE解除";
      			pureImg.setAttribute("class", "pureImg");
      			pureImg.setAttribute("src", "/PURE/pure_img.JPG");
      			document.getElementById(pureCommentList[index1]).appendChild(pureImg);
      			document.getElementById(pureCommentList[index1--]).classList.add("pure");
      			continue;

  			} else if(pureCommentList[index1] == Number(pureComments[index2].id)) {
      			index1--;
      			index2--;
      		}
      	}
    }

    function getNewComment() {
      	// 初期処理
      	let req = new XMLHttpRequest();
      	// リクエスト処理
      	req.onreadystatechange = function() {
        	if(req.readyState == 4 && req.status == 200) {
          		// データ受信成功時の処理
          		if(req.response) {
            		console.log(req.response);
            		let commentData = JSON.parse(req.response);
            		console.log(commentData);
            		commentDisplay(commentData.commentList);
            		pureComment(commentData.pureCommentList);
          		}
        	}
      	}
      	req.open("GET", "/PURE/GetNewCommentServlet");
      	req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
      	req.send(null);
    }

    function pureButtonEvent(e) {
   	  	console.log("PUREボタン入力確認");
      	// 初期処理
      	let pureButton = e.currentTarget;
      	let commentId = e.currentTarget.parentNode.getAttribute("id");
      	let req = new XMLHttpRequest();
      	// リクエスト処理
      	req.onreadystatechange = function() {
        	if(req.readyState == 4 && req.status == 200) {
        		if(JSON.parse(req.response) != 0) {
        			getNewComment();
        		}
          	}
      	}
      	req.open("POST", "/PURE/UpdatePureServlet");
      	req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
      	req.send( "commentId=" + commentId );
    }

    function replyButtonEvent(e) {
      	let commentId = e.currentTarget.parentNode.getAttribute("id");
      	document.getElementById("inputField").value += ">>>" + commentId + "\n";
    }
</script>
</html>
