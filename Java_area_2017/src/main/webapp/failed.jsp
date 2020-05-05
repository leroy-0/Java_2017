<%@ page import="java.util.concurrent.TimeUnit" %>
<%
    if ((session.getAttribute("session") == null) || (session.getAttribute("session") == "")) {
        session.invalidate();
%>
Username or Password not match with the database<br/>

<a href="index.jsp">Please Login</a>

<%
    }
%>