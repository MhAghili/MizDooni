<%@ page import="application.MizDooni" %>
<%@ page import="models.Feedback" %>
<%@ page import="models.Table" %>
<%@ page import="utils.ReservationCancellationRequest" %>


<%
  MizDooni mizDooni = (MizDooni) application.getAttribute("mizDooni");
  String username = session.getAttribute("loggedInUser").toString();
  int id = Integer.parseInt(request.getParameter("id"));


  ReservationCancellationRequest req = new ReservationCancellationRequest( id,username);

  mizDooni.getRestaurantService().cancelReservation(req);

  response.sendRedirect("Reservations.jsp");

%>