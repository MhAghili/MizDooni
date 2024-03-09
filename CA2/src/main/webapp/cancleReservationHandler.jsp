
<%@ page import="utils.ReservationCancellationRequest" %>
<%@ page import="interfaces.RestaurantService" %>
<%@ page import="services.RestaurantServiceImpl" %>


<%
  String username = session.getAttribute("loggedInUser").toString();
  int id = Integer.parseInt(request.getParameter("id"));
  RestaurantService restaurantService = RestaurantServiceImpl.getInstance();

  ReservationCancellationRequest req = new ReservationCancellationRequest( id,username);

  restaurantService.cancelReservation(req);

  response.sendRedirect("Reservations.jsp");

%>