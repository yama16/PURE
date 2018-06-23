<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.BulletinBoard" %>
<%@ page import="model.Comment" %>
<%@ page import="java.util.List" %>
<%
BulletinBoard bulletinBoard = (BulletinBoard) session.getAttribute("bulletinBoard");
List<Comment> commentList = bulletinBoard.getCommentList();
String bulletinBoardTitle = bulletinBoard.getTitle();
String ceatedAt = bulletinBoard.getCreatedAt().toString();
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="commentDesign.css">
    <title>PURE</title>
  </head>
  <body>
    <h1>PURE</h1>
    <!-- 　コメント欄　 -->
    <div id="commentField">
      <div id="title">
      	<p><%= bulletinBoardTitle %></p>
      	<input id="favoriteButton" type="button" value="お気に入り">
      </div>
      <!-- 以下コメントが入る -->
      <% for(Comment cmt: commentList) { %>
      	<div class="comment" id="<%= cmt.getId() %>">
      		<dl>
      			<dt><span>No<%= cmt.getId() %></span></dt>
      			<dt><span><%= cmt.getNickName() %></span></dt>
      			<dt><span><%= cmt.getAccountId() %></span></dt>
      			<dt><span><%= cmt.getCreatedAt() %></span></dt>
      			<dd><span><%= cmt.getComment() %></span></dd>
      		</dl>
      		<input class="pureButton" type="button" value="PURE">
      		<input class="replyButton" type="button" value="返信">
      	</div>
      <% } %>
    </div>

    <form id="inputComment">
      <label>ここに入力：</label><br>
      <textarea id="inputField" rows="5" cols="40" maxlength="200"></textarea><br>
      <input id="sendButton" type="button" value="送信">
      <input id="clearButton" type="button" value="クリア">
    </form>

    <footer>
      <address></address>
    </footer>
  </body>
  <script type="text/javascript">
    // ボタン初期設定(PUREボタン, 返信ボタン, お気に入りボタン)
    if( document.getElementsByClassName("comment") ) {
    	let pureButtons = document.getElementsByClassName("pureButton");
        let replyButtons = document.getElementsByClassName("replyButton");
        let favoriteButton = document.getElementById("favoriteButton");

        for(let button of pureButtons) {
          button.addEventListener("click", pureButtonEvent, false);
        }
        for(let button of replyButtons) {
          button.addEventListener("click", replyButtonEvent, false);
        }
    }
    favoriteButton.addEventListener("click", function() {
      // 初期処理
      let req = new XMLHttpRequest();
      // リクエスト処理
      req.onreadystatechange = function() {
        if(req.readyState == 4) {
          if(req.status == 200) {
            // データ受信成功時の処理
            // お気に入りボタンの色を変える！！
          }
        }
      }
      req.open("POST", "");
      req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
      req.send(null);
    }, false)

    // 5秒ごとに更新情報を取得
    setInterval(function() {
      // 初期処理
      let req = new XMLHttpRequest();
      // リクエスト処理
      req.onreadystatechange = function() {
        if(req.readyState == 4) {
          if(req.status == 200) {
            // データ受信成功時の処理
            if(req.response) {
              commentDisplay( JSON.parse(req.response) );
            }
          }
        }
      }
      req.open("GET", "/PURE/GetNewCommentServlet");
      req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
      req.send(null);
    }, 5000);

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
          if(req.readyState == 4) {
            if(req.status == 200) {
              // データ受信成功時の処理
              if(req.response) {
            	console.log("受信成功！！")
                commentDisplay( JSON.parse(req.response) );
              }
              text.value = ""; // 入力欄をクリア
            }
          }
        }
        req.open("POST", "/PURE/PostCommentServlet");
        req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        req.send( "comment=" + comment ); // コメントオブジェクトを送信
      }
    }, false);

    // コメントデータを受け取り画面に表示する処理
    function commentDisplay(commentArray) {
      // 初期処理
      let commentField = document.getElementById("commentField");   // コメント欄のdiv要素取得
      //let comments = document.getElementsByClassName("comment");

      // コメントを表示またはコメントをPURE(未実装)
      for(let cmtObj of commentArray) {
        cmtField.appendChild( createCommentFrame(cmtObj) );         // コメント枠をコメント欄に追加
      }
    }

    function createCommentFrame(commentObject) {
      // 初期処理
      let commentFrame = document.createElement("div");          // コメント枠を生成　
      let dlElement = document.createElement("dl");              // dl要素生成
      let dtElement = document.createElement("dt");              // dt要素生成
      let ddElementComment = document.createElement("dd");       // dd要素生成
      let spanElementCommentId = document.createElement("span"); // span要素(commentId)
      let spanElementNickName = document.createElement("span");  // span要素(NickName)
      let spanElementAccountId = document.createElement("span"); // span要素(AccountId)
      let spanElementCreatedAt = document.createElement("span"); // span要素(CreatedAt)
      let pureButton = document.createElement("input");          // input要素(PUREボタン)
      pureButton.setAttribute("type", "button");
      pureButton.setAttribute("value", "PURE");
      let replyButton = document.createElement("input");         // input要素(返信ボタン)
      replyButton.setAttribute("type", "button");
      replyButton.setAttribute("value", "返信");
      commentFrame.setAttribute("class", "comment");             // コメント枠にclass属性を設定
      commentFrame.setAttribute("id", commentObject.commentId);  // コメント枠にId属性(commentId)を設定

      // span要素にcmtObjのフィールドを追加
      spanElementCommentId.appendChild( document.createTextNode("No" + commentObject.commentId) );
      spanElementNickName.appendChild( document.createTextNode(commentObject.nickName) );
      spanElementAccountId.appendChild( document.createTextNode(commentObject.accountId) );
      spanElementCreatedAt.appendChild( document.createTextNode(commentObject.createdAt) );
      // dt要素にspan要素を追加
      dtElement.appendChild(spanElementCommentId);
      dtElement.appendChild(spanElementNickName);
      dtElement.appendChild(spanElementAccountId);
      dtElement.appendChild(spanElementCreatedAt);

      // linesを改行処理してdd要素にコメントを追加
      let lines = commentObject.comment.replace(/\r\n|\r/g, "\n");     // 改行コードを"\n"に変換(変換は何でもいい)
      lines = lines.split("\n");                                       // "\n"で分割して配列に
      for(let i = 0; i < lines.length; i++) {
        let cText = document.createTextNode( lines[i] );               // textNode生成
        ddElementComment.appendChild(cText);                           // TextNodeをp要素に追加
        ddElementComment.appendChild( document.createElement("br") );  // <br>を生成してp要素に追加
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

    function pureButtonEvent(e) {
      // 初期処理
      let commentId =  e.currentTarget.parentNode.getAttribute("id");
      let req = new XMLHttpRequest();
      // リクエスト処理
      req.onreadystatechange = function() {
        if(req.readyState == 4) {
          if(req.status == 200) {
            // データ受信成功時の処理
            // PURE解除の場合も考慮する処理！！
          }
        }
      }
      req.open("POST", "");
      req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
      req.send( "commentId=" + commentId );
    }

    function replyButtonEvent(e) {
      let commentId = e.currentTarget.parentNode.getAttribute("id");
      document.getElementById("inputField").value += ">>>" + commentId;
    }
  </script>
</html>
