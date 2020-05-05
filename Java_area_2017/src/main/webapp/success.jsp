<%@ page import="java.util.concurrent.TimeUnit" %>
<%
    if ((session.getAttribute("session") == null) || (session.getAttribute("session") == "")) {
%>
You are not logged in<br/>

<a href="index.jsp">Please Login</a>
<%} else {
    response.sendRedirect("UserInterface.jsp");
    }
%>