<%@ page import="application.MizDooni" %>


<%
    MizDooni mizDooni = (MizDooni) application.getAttribute("mizDooni");
    if (mizDooni == null) {

        mizDooni = MizDooni.getInstance();
        mizDooni.run();
        application.setAttribute("mizDooni", mizDooni);
    }
    response.sendRedirect("Login.jsp");


%>


