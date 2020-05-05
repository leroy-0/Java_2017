<%@ page import="bean.LoginDao" %><%
    session.setAttribute("session",null);
    session.setAttribute("token", null);
    LoginDao.setToken(session);
    session.setAttribute("name", null);
    session.invalidate();

    response.sendRedirect("index.jsp");
%>