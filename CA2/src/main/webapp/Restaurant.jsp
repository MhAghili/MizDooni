<%@ page import="javax.servlet.http.*" %>
<%@ page import="application.MizDooni" %>
<%@ page import="enums.UserType" %>
<%@ page import="models.User" %>
<%@ page import="models.Restaurant" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Feedback" %>

<%

  MizDooni mizDooni = (MizDooni) application.getAttribute("mizDooni");
  String restaurantName = request.getParameter("restaurantName");
  Restaurant restaurant = mizDooni.getRestaurantService().getCurrentRes(restaurantName);
  List<Feedback> feedbacks = mizDooni.getFeedbackService().getReviewsByRestaurantName(restaurantName);

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
      <li id="time">Time: <%= restaurant.getStartTime() %> - <%= restaurant.getEndTime() %></li>
      <li id="address">Address: <%= restaurant.getAddress().getCountry() %> - <%= restaurant.getAddress().getCity() %> - <%= restaurant.getAddress().getStreet() %></li>
      <li id="description">Description: <%= restaurant.getDescription() %></li>
      <li id="rate">Scores:</li>
<%--      <ul>--%>
<%--        <li>Food: 3.45</li>--%>
<%--        <li>Service: 2.5</li>--%>
<%--        <li>Ambiance: 4.59</li>--%>
<%--        <li>Overall: 4.1</li>--%>
<%--      </ul>--%>
    </ul>
    <br>



<%--    <table border="1" cellpadding="10">--%>
<%--      <tr>--%>
<%--          <td>--%>
<%--              <label>Reserve Table:</label>--%>
<%--              <form action="" method="post">--%>
<%--                <label>Table:</label>--%>
<%--                <select id="table_number" name="table_number">--%>
<%--                  <option value="1">1</option>--%>
<%--                  <option value="2">2</option>--%>
<%--                  <option value="3">3</option>--%>
<%--                </select>--%>
<%--                <label>Date & Time:</label>--%>
<%--                <input type="datetime-local" id="date_time" name="date_time">--%>
<%--                <br>--%>
<%--                <button type="submit" name="action" value="reserve">Reserve</button>--%>
<%--              </form>--%>
<%--          </td>--%>
<%--      </tr>--%>
<%--  </table>--%>

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
