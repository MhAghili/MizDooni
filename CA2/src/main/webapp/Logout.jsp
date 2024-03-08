<%@ page import="javax.servlet.http.*" %>
<%
    session = request.getSession(false);
    if (session != null) {
        session.invalidate();
    }
    response.sendRedirect("Login.jsp");
%>
