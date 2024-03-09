<%@ page import="application.MizDooni" %>


<%
    MizDooni mizDooni = MizDooni.getInstance();
    mizDooni.run();
    application.setAttribute("mizDooni", mizDooni);
    response.sendRedirect("Login.jsp");

%>


