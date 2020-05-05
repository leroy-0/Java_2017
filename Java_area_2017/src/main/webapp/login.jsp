<%@page import="bean.LoginDao"%>
<jsp:useBean id="user" class="bean.LoginBean" scope="session">

  <jsp:setProperty name="user" property="*" />
</jsp:useBean>
<%
    boolean status = LoginDao.validate(user, response, session);
%>