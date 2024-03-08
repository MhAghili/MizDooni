<%@ page import="application.MizDooni" %>
<%@ page import="models.Feedback" %>
<%@ page import="models.Table" %>


<%
    MizDooni mizDooni = (MizDooni) application.getAttribute("mizDooni");
    String username = session.getAttribute("loggedInUser").toString();
    String restaurantName = request.getParameter("restaurantName");
    int table_number = Integer.parseInt(request.getParameter("table_number"));
    int seats_number = Integer.parseInt(request.getParameter("seats_number"));

    Table table = new Table( table_number,restaurantName, username , seats_number);

    mizDooni.getRestaurantService().addTable(table);

    response.sendRedirect("manager_home.jsp");

%>