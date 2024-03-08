<%@ page import="javax.servlet.http.*" %>
<%@ page import="application.MizDooni" %>
<%@ page import="enums.UserType" %>
<%@ page import="models.User" %>


<%
  String username = request.getParameter("username");
  String password = request.getParameter("password");
  String role = request.getParameter("role");

  MizDooni mizDooni = (MizDooni) application.getAttribute("mizDooni");


  if (mizDooni.getUserService().login(username, password)) {

    session.setAttribute("loggedInUser", username);

//    User curUser = mizDooni.getDataBase().getUsers().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    if (role.equals(UserType.client.toString())) {
      response.sendRedirect("client_home.jsp");
    } else {
      response.sendRedirect("manager_home.jsp");
    }

  } else {
    response.sendRedirect("Login.jsp");
  }
%>
