
<%@ page import="application.MizDooni" %>
<%@ page import="enums.UserType" %>
<%@ page import="models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="interfaces.UserService" %>
<%@ page import="services.UserServiceImplementation" %>

<%try { %>
<%
  MizDooni mizDooni = MizDooni.getInstance();
  UserService userService = UserServiceImplementation.getInstance();
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

  if (userService.login(username, password)) {
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