
<%@ page import="application.MizDooni" %>
<%@ page import="models.Restaurant" %>
<%@ page import="java.util.List" %>
<%@ page import="services.RestaurantServiceImpl" %>
<%@ page import="models.Feedback" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="interfaces.RestaurantService" %>
<%try { %>
<%
    RestaurantService restaurantService = RestaurantServiceImpl.getInstance();
    MizDooni mizDooni = MizDooni.getInstance();
    List<Restaurant> restaurants;


    String buttonClicked = request.getParameter("action");
    String searchValue = request.getParameter("search");

    ArrayList<Restaurant> filteredRestaurants = new ArrayList<Restaurant>();
    if ("search_by_type".equals(buttonClicked)) {
        filteredRestaurants.addAll(RestaurantServiceImpl.getInstance().getRestaurantByType(searchValue));
    } else if ("search_by_name".equals(buttonClicked)) {
        filteredRestaurants.add(RestaurantServiceImpl.getInstance().getRestaurantByName(searchValue));
    } else if ("search_by_city".equals(buttonClicked)) {
        filteredRestaurants.addAll(RestaurantServiceImpl.getInstance().getRestaurantsByCity(searchValue));
    }
    else
        filteredRestaurants.addAll(mizDooni.getDataBase().getRestaurants().toList());

    restaurants = filteredRestaurants;
%>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Restaurants</title>
</head>
<body>
<p id="username">username: <%= session.getAttribute("loggedInUser") %> <a href="client_home.jsp">Home</a> <a href="Logout.jsp" style="color: red">Log Out</a></p>
    <br><br>
    <form action="Restaurants.jsp" method="POST">
        <label>Search:</label>
        <input type="text" name="search" value="">
        <button type="submit" name="action" value="search_by_type">Search By Type</button>
        <button type="submit" name="action" value="search_by_name">Search By Name</button>
        <button type="submit" name="action" value="search_by_city">Search By City</button>
        <button type="submit" name="action" value="clear">Clear Search</button>
    </form>
    <br><br>
    <form action="Restaurants.jsp" method="POST">
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
        <% Feedback restaurantAverageRating = restaurantService.getAverageFeedbackOfRestaurant(restaurant.getName()); %>
        <tr>
            <td id="name"><a href="Restaurant.jsp?restaurantName=<%= restaurant.getName() %>" ><%= restaurant.getName()%></a></td>
            <td id="city"> <%= restaurant.getAddress().getCity() %></td>
            <td id="type"> <%= restaurant.getType() %></td>
            <td id="time"> <%= restaurant.getStartTime() %> - <%= restaurant.getEndTime() %></td>
            <td id="foodRaing"><%= restaurantAverageRating.getFoodRate() %></td>
            <td id ="serviceRating"><%= restaurantAverageRating.getServiceRate() %></td>
            <td id="ambianceRating"><%= restaurantAverageRating.getAmbianceRate() %></td>
            <td id="overallRating"><%= restaurantAverageRating.getOverallRate() %></td>
        </tr>
        <br>
        <% } %>
    </table>
</body>
</html>

<%} catch (Exception e) {
    response.sendRedirect("error.jsp?error=" + e.getMessage());
}%>