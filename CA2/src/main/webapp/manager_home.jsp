
<%@ page import="application.MizDooni" %>
<%@ page import="models.Restaurant" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Table" %>

<%
    MizDooni mizDooni = (MizDooni) application.getAttribute("mizDooni");
    String username = session.getAttribute("loggedInUser").toString();
    Restaurant restaurant = mizDooni.getRestaurantService().getRestaurantByManager(username);
    List<Table> tables = mizDooni.getRestaurantService().getTablesByRestaurant(restaurant.getName());
%>



<html lang="en"><head>
    <meta charset="UTF-8">
    <title>Manager Home</title>
</head>
<body>
    <h1>Welcome  <%= username%> <a href="Logout.jsp" style="color: red">Log Out</a></h1>

    <h2>Your Restaurant Information:</h2>
    <ul>

        <li id="name">Name: <%= restaurant.getName() %> </li>
        <li id="type">Type:  <%= restaurant.getType() %></li>
        <li id="time">Time: <%= restaurant.getStartTime().getHours() %> - <%= restaurant.getEndTime().getHours() %></li>
        <li id="description"> <%= restaurant.getDescription() %></li>
        <li id="address"> <%= restaurant.getAddress().getCountry() %>,<%= restaurant.getAddress().getCity() %>,<%= restaurant.getAddress().getStreet() %></li>
        <li id="tables">Tables:</li>

        <ul>
            <% for (Table table : tables) { %>
                <li> Table Number: <%= table.getTableNumber() %> Seats:<%= table.getSeatsNumber() %> </li>
            <% } %>
        </ul>

    </ul>
    
    <table border="1" cellpadding="10">
        <tr>
            <td>

            <h3>Add Table:</h3>
            <form method="post" action="TableHandler.jsp?restaurantName=<%= restaurant.getName() %>">
                <label>Table Number:</label>
                <input name="table_number" type="number" min="0"/>
                <br>
                <label>Seats Number:</label>
                <input name="seats_number" type="number" min="1"/>
                <br>
                <button type="submit">Add</button>
            </form>

            </td>
        </tr>
    </table>
    

</body></html>