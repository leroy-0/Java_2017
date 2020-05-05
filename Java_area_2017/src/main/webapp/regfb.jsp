<%@ page import="com.area.jersey.LoginFbBean" %>
<%@ page import="javax.ws.rs.core.Response" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.net.URLConnection" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="bean.FbLoginProvider" %>
<%@ page import="bean.RegistrationDao" %>
<%--
  Created by IntelliJ IDEA.
  User: epitech
  Date: 03/11/2017
  Time: 20:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
<%
    RegistrationDao.getinfoFb(request, response, session);
%>
</body>
</html>
