<%@ page import="javax.servlet.http.*" %>
<%@ page import="application.MizDooni" %>
<%@ page import="enums.UserType" %>
<%@ page import="java.util.List" %>
<%@ page import="models.*" %>
<%@ page import="services.RestaurantServiceImpl" %>
<%@ page import="interfaces.RestaurantService" %>
<%@ page import="interfaces.FeedbackService" %>
<%@ page import="services.FeedbackServiceImpl" %>
<%try { %>
<%

  RestaurantService restaurantService = RestaurantServiceImpl.getInstance();

  FeedbackService feedbackService = FeedbackServiceImpl.getInstance();

  String restaurantName = request.getParameter("restaurantName");
  Restaurant restaurant = restaurantService.getCurrentRes(restaurantName);
  List<Feedback> feedbacks = feedbackService.getReviewsByRestaurantName(restaurantName);

%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Restaurant</title>
  </head>
  <body>
    <p id="username">username: <%= session.getAttribute("loggedInUser") %> <a href="client_home.jsp">Home</a> <a href="Logout.jsp" style="color: red">Log Out</a></p>
    <br>
    <h2>Restaurant Info for <%= restaurant.getName() %> </h2>

    <ul>
      <li id="name">Name: <%= restaurant.getName() %></li>
      <li id="type">Type: <%= restaurant.getType() %></li>
      <li id="time">Time: <%= restaurant.getStartTime().getHours() %> - <%= restaurant.getEndTime().getHours() %></li>
      <li id="address">Address: <%= restaurant.getAddress().getCountry() %> - <%= restaurant.getAddress().getCity() %> - <%= restaurant.getAddress().getStreet() %></li>
      <li id="description">Description: <%= restaurant.getDescription() %></li>
      <li id="rate">Scores:</li>
      <%  Feedback restaurantAverageRating = restaurantService.getAverageFeedbackOfRestaurant(restaurant.getName()); %>
      <ul>
        <li>Food: <%= restaurantAverageRating.getFoodRate() %></li>
        <li>Service: <%= restaurantAverageRating.getServiceRate() %> </li>
        <li>Ambiance: <%= restaurantAverageRating.getAmbianceRate() %></li>
        <li>Overall: <%= restaurantAverageRating.getOverallRate() %></li>
      </ul>
    </ul>
    <br>


    <%
      List<Table> availableTableInfos = restaurantService.getTablesByRestaurant(restaurant.getName());
    %>



    <table border="1" cellpadding="10">
      <tr>
          <td>
              <label>Reserve Table:</label>
              <form action="ReserveHandler.jsp?restaurantName=<%= restaurantName %>" method="post">
                <label>Table:</label>
                <select id="table_number" name="table_number">
                  <% for (Table table : availableTableInfos) { %>
                    <option value="<%= table.getTableNumber()%>"><%= table.getTableNumber()%></option>
                  <% } %>
                </select>
                <label>Date & Time:</label>
                <input type="datetime-local" id="date_time" name="date_time">
                <br>
                <button type="submit" name="action" value="reserve">Reserve</button>
              </form>
          </td>
      </tr>
  </table>

    <table border="1" cellpadding="10">
      <tr>
          <td>
              <label>Feedback:</label>
              <form action="feedbackHandler.jsp?restaurantName=<%= restaurantName %>" method="post">
                <label>Food Rate:</label>
                <input type="number" id="food_rate" name="food_rate" step="0.1" min="0" max="5">
                <label>Service Rate:</label>
                <input type="number" id="service_rate" name="service_rate" step="0.1" min="0" max="5">
                <label>Ambiance Rate:</label>
                <input type="number" id="ambiance_rate" name="ambiance_rate" step="0.1" min="0" max="5">
                <label>Overall Rate:</label>
                <input type="number" id="overall_rate" name="overall_rate" step="0.1" min="0" max="5">
                <br>
                <label>Comment:</label>
                <textarea name="comment"  id="" cols="30" rows="5" placeholder="Enter your comment"></textarea>
                <!-- <input type="textarea" name="comment" value="" /> -->
                <br>
                <button type="submit" name="action" value="feedback">Submit</button>
              </form>
          </td>
      </tr>
  </table>

    <br/>


    <br/>
    <table style="width: 100%; text-align: center;" border="1">
      <caption><h2>Feedbacks</h2></caption>
      <% for (Feedback feedback : feedbacks) { %>
      <tr>
        <th>Username</th>
        <th>Comment</th>
        <th>Food Rate</th>
        <th>Service Rate</th>
        <th>Ambiance Rate</th>
        <th>Overall Rate</th>
      </tr>
      <tr>
        <td><%= feedback.getUsername()%></td>
        <td><%= feedback.getComment()%></td>
        <td><%= feedback.getFoodRate()%></td>
        <td><%= feedback.getServiceRate()%></td>
        <td><%= feedback.getAmbianceRate()%></td>
        <td><%= feedback.getOverallRate()%></td>
      </tr>
      <% } %>
    </table>
    <br><br>

  </body>
</html>
<%} catch (Exception e) {
  response.sendRedirect("error.jsp?error=" + e.getMessage());
  }%>