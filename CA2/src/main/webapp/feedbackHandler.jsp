<%@ page import="models.Feedback" %>
<%@ page import="interfaces.FeedbackService" %>
<%@ page import="services.FeedbackServiceImpl" %>
<%try { %>

<%
    String username = session.getAttribute("loggedInUser").toString();
    String restaurantName = request.getParameter("restaurantName");
    String comment = request.getParameter("comment");
    double food_rate = Double.parseDouble(request.getParameter("food_rate"));
    double service_rate = Double.parseDouble(request.getParameter("service_rate"));
    double ambiance_rate = Double.parseDouble(request.getParameter("ambiance_rate"));
    double overall_rate = Double.parseDouble(request.getParameter("overall_rate"));

    FeedbackService feedbackService = FeedbackServiceImpl.getInstance();

    Feedback feedback = new Feedback(username, restaurantName, food_rate, service_rate, ambiance_rate, overall_rate, comment);

    feedbackService.addReview(feedback);

    response.sendRedirect("Restaurant.jsp?restaurantName=" + restaurantName);

%>

<%} catch (Exception e) {
    response.sendRedirect("error.jsp?error=" + e.getMessage());
}%>