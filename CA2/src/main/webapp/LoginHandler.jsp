
<%@ page import="application.MizDooni" %>
<%@ page import="enums.UserType" %>
<%@ page import="models.User" %>
<%@ page import="java.util.List" %>

<%try { %>
<%
  MizDooni mizDooni = (MizDooni) application.getAttribute("mizDooni");

  String username = request.getParameter("username");
  String password = request.getParameter("password");
  UserType role = null;


  List<User> users = mizDooni.getDataBase().getUsers().toList();


  for (User user : users){
    if(user.getUsername().equals(username)){
      role = user.getRole();
      break;
    }
  }

  if (mizDooni.getUserService().login(username, password)) {
    session.setAttribute("loggedInUser", username);

    if (role == UserType.client) {
      response.sendRedirect("client_home.jsp");
    } else {
      response.sendRedirect("manager_home.jsp");
    }

  } else {
    response.sendRedirect("Login.jsp");
  }
%>

<%} catch (Exception e) {
  response.sendRedirect("error.jsp?error=" + e.getMessage());
}%>