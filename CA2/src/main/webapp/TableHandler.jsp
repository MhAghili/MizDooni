<%@ page import="models.Table" %>
<%@ page import="services.RestaurantServiceImpl" %>
<%@ page import="interfaces.RestaurantService" %>

<%try { %>
<%

    RestaurantService restaurantService = RestaurantServiceImpl.getInstance();
    String username = session.getAttribute("loggedInUser").toString();
    String restaurantName = request.getParameter("restaurantName");
    int table_number = Integer.parseInt(request.getParameter("table_number"));
    int seats_number = Integer.parseInt(request.getParameter("seats_number"));

    Table table = new Table( table_number,restaurantName, username , seats_number);

    restaurantService.addTable(table);

    response.sendRedirect("manager_home.jsp");

%>
<%} catch (Exception e) {
    response.sendRedirect("error.jsp?error=" + e.getMessage());
    }%>

