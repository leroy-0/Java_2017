<%@ page import="bean.FbLoginProvider" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="./assets/css/login.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/foundation/6.2.3/foundation.min.css" />
</head>
<body>
<div class="row expanded header">
    <h2>Welcome to AREA interface</h2>
</div>
<%
    if ((session.getAttribute("session") == "TRUE" ))
        response.sendRedirect("success.jsp");
    String url = "https://www.facebook.com/dialog/oauth?client_id=" + FbLoginProvider.appId + "&redirect_uri=" + FbLoginProvider.redirect_url
            + "&=scope=email&scope=user_friends";
%>
<div class="container">
    <div class="login-form small-12 large-6 columns small-centered">
        <form method="post" action="login.jsp">
            <label>Username<input type="text" name="uname" required/></label>
            <label>Password<input type="password" name="pass" required/></label>
            <input class="button" type="submit" value="Login" />
        </form>
        <a href="reg.jsp">No account ? Register here</a>
    </div>

    <form method="get" class="form-login-fb" action="area/login/fb">
        <button type="submit" class="login-fb"><img src="./assets/img/fb_login.png"></button>
    </form>
</div>
</body>
</html>