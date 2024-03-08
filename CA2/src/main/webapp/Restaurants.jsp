
<%@ page import="application.MizDooni" %>
<%@ page import="models.Restaurant" %>
<%@ page import="java.util.List" %>

<%

    MizDooni mizDooni = (MizDooni) application.getAttribute("mizDooni");
    List<Restaurant> restaurants = mizDooni.getDataBase().getRestaurants().toList();

%>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Restaurants</title>
</head>
<body>
<p id="username">username: <%= session.getAttribute("loggedInUser") %> <a href="client_home.jsp">Home</a> <a href="Logout.jsp" style="color: red">Log Out</a></p>
    <br><br>
    <form action="" method="POST">
        <label>Search:</label>
        <input type="text" name="search" value="">
        <button type="submit" name="action" value="search_by_type">Search By Type</button>
        <button type="submit" name="action" value="search_by_name">Search By Name</button>
        <button type="submit" name="action" value="search_by_city">Search By City</button>
        <button type="submit" name="action" value="clear">Clear Search</button>
    </form>
    <br><br>
    <form action="" method="POST">
        <label>Sort By:</label>
        <button type="submit" name="action" value="sort_by_rate">Overall Score</button>
    </form>
    <br><br>
    <table style="width:100%; text-align:center;" border="1">
        <tr>
            <th>Name</th> 
            <th>City</th>
            <th>Type</th>
            <th>Time</th>
            <th>Food Score</th>
            <th>Service Score</th>
            <th>Ambiance Score</th>
            <th>Overall Score</th>
        </tr>
        <% for (Restaurant restaurant : restaurants) { %>
        <tr>
            <td id="name"><a href="Restaurant.jsp?restaurantName=<%= restaurant.getName() %>" ><%= restaurant.getName()%></a></td>
            <td id="city">City: <%= restaurant.getAddress().getCity() %></td>
            <td id="type">Type: <%= restaurant.getType() %></td>
            <td id="time">Time: <%= restaurant.getStartTime() %> - <%= restaurant.getEndTime() %></td>
<%--            <td>Food: 3.45</td>--%>
<%--            <td>Service: 2.5</td>--%>
<%--            <td>Ambiance: 4.59</td>--%>
<%--            <td>Overall: 4.1</td>--%>
        </tr>
        <br>
        <% } %>
    </table>
</body>
</html>