<%@ page import="bean.RegToken" %><%--
  Created by IntelliJ IDEA.
  User: longle_h
  Date: 25/10/2017
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AREA Interface</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/foundation/6.2.3/foundation.min.css" />
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,500,600,700' rel='stylesheet' type='text/css' />
</head>
<body>
<style>
    p, a, h1, h2, h3, h4, h5, h6, label, input {
        font-family: 'Open Sans', 'sans-serif';
        color: black;
    }
    .box {
        padding: 10px;
    }
    .header {
        background-color: #ADD8E6;
        text-align: center;
    }
    .btn-line {
        float: right;
        margin-top: 22px !important;
        margin-right: 2px !important;
    }
    .button.big {
        width:100%;
        margin-top: 5px;
    }
    .button {
        line-height: 0.9;
    }
    input[type="text"] {
        margin-bottom: 3px !important;
    }
    h6, a {
        font-weight: bold;
    }
    .menu-left {
        position: static;
        -moz-box-shadow: 2px 0px 10px 0px #656565;
        -webkit-box-shadow: 2px 0px 10px 0px #656565;
        -o-box-shadow: 2px 0px 10px 0px #656565;
        box-shadow: 2px 0px 10px 0px #656565;
        filter:progid:DXImageTransform.Microsoft.Shadow(color=#656565, Direction=90, Strength=10);
    }
    .interface {
        padding-top: 40px;
    }
    .notif {
        font-size: 10px;
        margin: 0;
        line-height: 1;
    }
</style>
<div class="row expanded header">
    <h2>Welcome to AREA interface</h2>
    <a href='logout.jsp'>Log out</a>
</div>
<%
    if ((session.getAttribute("session") != "TRUE" ) || (session.getAttribute("session") == "")) {
%>
You are not logged in<br/>

<a href="index.jsp">Please Login</a>
<% } else  {%>
<div class="row expanded" data-equalizer>
    <div class="large-2 columns menu-left" data-equalizer-watch>
        <div class="intra-notifications">
            <h6>Intra Epitech</h6>
            <p>Modules:</p>
            <p class="notif module-info"></p>
            <p>Project:</p>
            <p class="notif project-info"></p>
            <p>Notifications:</p>
            <p class="notif info"></p>
        </div>

    </div>
    <div class="large-10 columns interface" data-equalizer-watch>
        <div class="row">
            <div id="dropbox" class="large-4 small-12 columns">
                <h3>Dropbox</h3>
                <ul class="vertical menu accordion-menu" data-accordion-menu>
                    <li>
                        <a>Upload a file</a>
                        <ul class="menu vertical nested">
                            <li>
                                <form action="/area/dropbox/upload" method="post" enctype="multipart/form-data">
                                    <label>User Token :<input type="text" name="token"></label>
                                    <p>
                                        Select a file : <input type="file" name="file"/>
                                    </p>
                                    <input type="submit" class="button big" value="Upload file">
                                </form>
                            </li>
                        </ul>
                    </li>
                </ul>
                <ul class="vertical menu accordion-menu" data-accordion-menu>
                    <li>
                        <a>Upload local file</a>
                        <ul class="menu vertical nested">
                            <li>
                                <form action="/area/dropbox/upload-local" method="post">
                                    <label>User Token :<input type="text" name="token"></label>
                                    <label>Path :<input type="text" name="file"></label>
                                    <label>Name in dropbox :<input type="text" name="dropbox-path"></label>
                                    <input type="submit" class="button big" value="Upload file to dropbox">
                                </form>
                            </li>
                        </ul>
                    </li>
                </ul>
                <ul class="vertical menu accordion-menu" data-accordion-menu>
                    <li>
                        <a>List repository</a>
                        <ul class="menu vertical nested">
                            <li>
                                <form action="/area/dropbox/list" method="post">
                                    <label>User Token :<input type="text" name="token"></label>
                                    <input type="submit" class="button big" value="List repository">
                                </form>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div class="large-4 small-12 columns">
                <div id="twitter" class="box">
                    <h3>Twitter</h3>
                    <a style="font-weight: normal" target="_blank" href="/area/twitter/login">
                        <button class="button" >Login</button>
                    </a>
                    <form action="/area/twitter/postStatus" method="get">
                        <div class="input-group">
                            <label>Message to post :<input name="msg" class="input-group-field" type="text"></label>
                            <div class="input-group-button">
                                <input type="submit" class="button btn-line" value="Summit">
                            </div>
                        </div>
                    </form>
                    <form action="/area/twitter/listenPost" method="get">
                        <div class="input-group">
                            <label>Keyword to listen :<input name="keyword" class="input-group-field" type="text"></label>
                            <div class="input-group-button">
                                <input type="submit" class="button btn-line" value="Submit">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <%
                String token = RegToken.getToken(session);
                String token_fb = RegToken.getTokenFb(session);
                if (token.isEmpty())
            {

            %>
                <div class="large-4 small-12 columns">
                    <div class="box">
                        <h3>Intranet Epitech</h3>
                        <form action="/area/IntraNoAuth/login" method="get">
                            <div class="input-group">
                                <label>Token for user Intra :<input name="token" class="input-group-field" type="text"></label>
                                <div class="input-group-button">
                                    <input type="submit" class="button btn-line" value="Submit">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            <%} %>
            <div class="large-4 small-12 columns">
                <div class="box">
                    <h3>Gmail</h3>
                    <form action="/area/gmail/login" method="get">
                        <input type="submit" class="button" value="Login">
                    </form>
                    <form action="/area/gmail/email" method="get">
                        <input type="submit" class="button big" value="Get mail">
                    </form>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="large-4 small-12 columns">
                <div id="fb" class="box">
                    <h3>Facebook</h3>
                    <% if (token_fb == null ||  token.isEmpty()) {
           %>
                    <form action = "/area/fb/getToken" method = "get" >
                        <input type = "submit" class="button big" value = "Login" >
                    </form >
                    <%
                    }
                    %>
                    <ul class="vertical menu accordion-menu" data-accordion-menu>
                        <li>
                            <a>Post a message on your wall</a>
                            <ul class="menu vertical nested">

                                <li>
                                    <form action="/area/fb/post" method="get">
                                        <label>Message to post :<input type="text" name="string"></label>
                                        <input type="submit" class="button big" value="Post">
                                    </form>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="vertical menu accordion-menu" data-accordion-menu>
                        <li>
                            <a>Search groups</a>
                            <ul class="menu vertical nested">
                                <li>
                                    <form action="/area/fb/search_group" method="post" enctype='application/json'>
                                        <label>User Token<input type="text" name="token"></label>
                                        <label>Group name :<input type="text" name="string"></label>
                                        <input type="submit" class="button big" value="Search">
                                    </form>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="vertical menu accordion-menu" data-accordion-menu>
                        <li>
                            <a>Post on group</a>
                            <ul class="menu vertical nested">
                                <li>
                                    <form action="/area/fb/post_group" method="post" enctype='application/json'>
                                        <label>User Token :<input type="text" name="token"></label>
                                        <label>Message to post<input type="text" name="string"></label>
                                        <label>Group ID :<input type="text" name="id"></label>
                                        <input type="submit" class="button big" value="Post">
                                    </form>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="vertical menu accordion-menu" data-accordion-menu>
                        <li>
                            <a>Get your feed</a>
                            <ul class="menu vertical nested">
                                <li>
                                    <form action="/area/fb/feed" method="post" enctype='application/json'>
                                        <label>User Token :<input type="text" name="token"></label>
                                        <input type="submit" class="button big" value="Get it!">
                                    </form>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="vertical menu accordion-menu" data-accordion-menu>
                        <li>
                            <a>Like post</a>
                            <ul class="menu vertical nested">
                                <li>
                                    <form action="/area/fb/like_post" method="post">
                                        <label>User Token :<input type="text" name="token"></label>
                                        <label>Post ID :<input type="text" name="id"></label>
                                        <input type="submit" class="button big" value="Like">
                                    </form>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="vertical menu accordion-menu" data-accordion-menu>
                        <li>
                            <a>Unlike post</a>
                            <ul class="menu vertical nested">
                                <li>
                                    <form action="/area/fb/unlike_post" method="post">
                                        <label>User Token :<input type="text" name="token"></label>
                                        <label>Post ID :<input type="text" name="id"></label>
                                        <input type="submit" class="button big" value="Unlike">
                                    </form>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="vertical menu accordion-menu" data-accordion-menu>
                        <li>
                            <a>Like photo</a>
                            <ul class="menu vertical nested">
                                <li>
                                    <form action="/area/fb/like_photo" method="post">
                                        <label>User Token :<input type="text" name="token"></label>
                                        <label>Photo ID :<input type="text" name="id"></label>
                                        <input type="submit" class="button big" value="Like">
                                    </form>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="vertical menu accordion-menu" data-accordion-menu>
                        <li>
                            <a>Unlike photo</a>
                            <ul class="menu vertical nested">
                                <li>
                                    <form action="/area/fb/unlike_photo" method="post">
                                        <label>User Token :<input type="text" name="token"></label>
                                        <label>Photo ID :<input type="text" name="id"></label>
                                        <input type="submit" class="button big" value="Unlike">
                                    </form>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="vertical menu accordion-menu" data-accordion-menu>
                        <li>
                            <a>Comment a post</a>
                            <ul class="menu vertical nested">
                                <li>
                                    <form action="/area/fb/comment_post" method="post">
                                        <label>User Token :<input type="text" name="token"></label>
                                        <label>Post ID :<input type="text" name="id"></label>
                                        <label>Message to post :<input type="text" name="string"></label>
                                        <input type="submit" class="button big" value="Like">
                                    </form>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="vertical menu accordion-menu" data-accordion-menu>
                        <li>
                            <a>Comment a photo</a>
                            <ul class="menu vertical nested">
                                <li>
                                    <form action="/area/fb/comment_photo" method="post">
                                        <label>User Token :<input type="text" name="token"></label>
                                        <label>Photo ID :<input type="text" name="id"></label>
                                        <label>Message to post :<input type="text" name="string"></label>
                                        <input type="submit" class="button big" value="Unlike">
                                    </form>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>

            <div class="large-4 small-12 columns end">
                <div id="rss" class="box">
                    <h3>RSS</h3>

                    <form action="/area/RSS/aff" method="get">
                        <div class="input-group">
                            <label>Display RSS flux :<input name="rss" class="input-group-field" type="text"></label>
                            <div class="input-group-button">
                                <input type="submit" class="button btn-line" value="Submit">
                            </div>
                        </div>
                    </form>
                    <form action="/area/RSS/add" method="get">
                        <div class="input-group">
                            <label>Add RSS link :<input name="add" class="input-group-field" type="text"></label>
                            <div class="input-group-button">
                                <input type="submit" class="button btn-line" value="Submit">
                            </div>
                        </div>
                    </form>
                    <form action="/area/RSS/remove" method="get">
                        <div class="input-group">
                            <label>Remove RSS link :<input class="input-group-field" type="text" name="remove"></label>
                            <div class="input-group-button">
                                <input type="submit" class="button btn-line" value="Submit">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<% }%>
<script src="http://code.jquery.com/jquery.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/foundation/6.2.3/foundation.min.js" type="text/javascript"></script>
<script src="./assets/js/app.js" type="text/javascript"></script>
<script>
    $(document).foundation();
</script>
</body>
</html>
