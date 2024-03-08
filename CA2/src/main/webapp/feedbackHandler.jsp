<%@ page import="application.MizDooni" %>
<%@ page import="models.Feedback" %>


<%
    MizDooni mizDooni = (MizDooni) application.getAttribute("mizDooni");
    String username = session.getAttribute("loggedInUser").toString();
    String restaurantName = request.getParameter("restaurantName");
    String comment = request.getParameter("comment");
    double food_rate = Double.parseDouble(request.getParameter("food_rate"));
    double service_rate = Double.parseDouble(request.getParameter("service_rate"));
    double ambiance_rate = Double.parseDouble(request.getParameter("ambiance_rate"));
    double overall_rate = Double.parseDouble(request.getParameter("overall_rate"));

    Feedback feedback = new Feedback(username, restaurantName, food_rate, service_rate, ambiance_rate, overall_rate, comment);

    mizDooni.getFeedbackService().addReview(feedback);

    response.sendRedirect("Restaurant.jsp?restaurantName=" + restaurantName);

%>