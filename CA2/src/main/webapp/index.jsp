
<%@ page import="models.User" %>

<%
    // Check if the user is logged in
    User user = (User) session.getAttribute("user");
    int i = 0;
    if (i == 1) {
        // User is logged in, redirect to client_home.jsp
        response.sendRedirect("client_home.jsp");
    } else {
        // User is not logged in, redirect to login page
        response.sendRedirect("Login.jsp");
    }
%>

