<%@ page import="application.MizDooni" %>
<%@ page import="utils.ReservationCancellationRequest" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="models.TableReservation" %>
<%@ page import="interfaces.RestaurantService" %>
<%@ page import="services.RestaurantServiceImpl" %>

<%try { %>
<%

    RestaurantService restaurantService = RestaurantServiceImpl.getInstance();

    String username = session.getAttribute("loggedInUser").toString();
    String date_time = request.getParameter("date_time");
    String restaurantName = request.getParameter("restaurantName");
    int table_number = Integer.parseInt(request.getParameter("table_number"));
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    Date date = dateFormat.parse(date_time);

    TableReservation tableReservation = new TableReservation(0, username,restaurantName , table_number, date);

    restaurantService.reserveTable(tableReservation);

    response.sendRedirect("Reservations.jsp");
%>
<%} catch (Exception e) {
    response.sendRedirect("error.jsp?error=" + e.getMessage());
}%>