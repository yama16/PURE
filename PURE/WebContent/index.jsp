<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
          <!--最新-->
          <h2>リアルタイム</h2>
              <textarea rows="20" cols="100"></textarea>
      </section>
      <section>
          <!--リアルタイム-->
          <h2>最新</h2>
              <textarea rows="20" cols="100"></textarea>
      </section>
    </main>
    <!--                        ログイン・検索                         -->
    <div id="login">
      <fieldset>
        <a action="/PURE/LoginServlet" method="post">ログイン</a><br>
      <a href="#">新規の方はこちら！！！</a><br>
      </fieldset>
      <fieldset>
        <input type="search" name="search" placeholder="タグ名を入力">
        <input type="submit" name="submit" value="検索">
      </fieldset>
    </div>
    <!--                       ランキング                               -->
      <div id="sub">
        <a href="#"><h2>人気のタグ</h2></a>
              <textarea rows="30" cols="50"></textarea>
      <!--ランキング-->
        <a href="#"><h2>ランキング</h2></a>
              <textarea rows="30" cols="50"></textarea>
      </div>
    </div>
    <form action="/PURE/LoginServlet" method="get">
    	<input type="submit" value="test">
    </form>
  </body>
</html>