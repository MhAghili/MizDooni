
<%@ page import="application.MizDooni" %>
<%@ page import="java.util.List" %>
<%@ page import="models.TableReservation" %>

<%
    String username = session.getAttribute("loggedInUser").toString();
    MizDooni mizDooni = (MizDooni) application.getAttribute("mizDooni");
    List<TableReservation> userReservations = mizDooni.getRestaurantService().getReservationsByUserName(username);

%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reservations</title>
</head>
<body>
<p id="username">username: <%= session.getAttribute("loggedInUser") %> <a href="client_home.jsp">Home</a> <a href="Logout.jsp" style="color: red">Log Out</a></p>
    <h1>Your Reservations:</h1>
    <br><br>
    <br><br>
    <table style="width:100%; text-align:center;" border="1">
        <tr>
            <th>Reservation Id</th>
            <th>Resturant Name</th>
            <th>Table Number</th>
            <th>Date & Time</th>
            <th>Canceling</th>
        </tr>
        <% for(TableReservation userReservation : userReservations) {%>
        <tr>
            <td><%= userReservation.getNumber()  %></td>
            <td><a href="Restaurant.jsp?restaurantName=<%=userReservation.getRestaurantName()%>"><%=userReservation.getRestaurantName()%></a></td>
            <td><%= userReservation.getTableNumber()  %></td>
            <td><%= userReservation.getDatetime()  %></td>
            <td>
                <form action="cancleReservationHandler.jsp?id=<%=userReservation.getNumber()%>" method="POST">
                    <button type="submit" name="action" value="1532">Cancel This</button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
</body>
</html>