<html lang="en"><head>
    <meta charset="UTF-8">
    <title>Client Home</title>
</head>


<body>
    <h1>Welcome <%= session.getAttribute("loggedInUser")%>  <a href="Logout.jsp" style="color: red">Log Out</a></h1>
    

    <ul type="square">
        <li>
            <a href="Restaurants.jsp">Restaurants</a>
        </li>
        <li>
            <a href="Reservations.jsp">Reservations</a>
        </li>
    </ul>
    

</body></html>