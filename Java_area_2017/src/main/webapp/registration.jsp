<%@page import="bean.RegistrationDao"%>
<%@ page import="java.security.SecureRandom" %>
<jsp:useBean id="user" class="bean.RegisterBean" scope="session">
    <jsp:setProperty name="user" property="*" />
</jsp:useBean>
<%
    RegistrationDao.register(user, response, session);
%>