<%@ page import="application.MizDooni" %>


<%
    MizDooni mizDooni = (MizDooni) application.getAttribute("mizDooni");
    if (mizDooni == null) {
        // If MizDooni instance doesn't exist, create a new one and set it in the application scope
        mizDooni = MizDooni.getInstance();
        mizDooni.run();
        application.setAttribute("mizDooni", mizDooni);
    }
    response.sendRedirect("Login.jsp");


%>


