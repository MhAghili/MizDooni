<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h2>Welcome, <%= session.getAttribute("loggedInUser") %></h2>
<a href="Logout.jsp">Logout</a>
</body>
</html>
