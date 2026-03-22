<%-- 
    Document   : home
    Created on : Mar 21, 2026, 3:16:42 PM
    Author     : Dang Vinh Hung - CE170162
--%>
<%@ page import="model.Account" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    Account acc = (Account) session.getAttribute("account");

    // Not logged in → redirect to login page
    if (acc == null) {
        response.sendRedirect("login");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<jsp:include page="navbar.jsp"/>

<div class="container mt-4">

    <h2>🏠 Home Page</h2>

    <!-- USER INFO -->
    <p>
        Welcome, 
        <strong>
            <%= acc.getFullName() != null ? acc.getFullName() : acc.getEmail() %>
        </strong>
    </p>

    <hr>

    <!-- USER FEATURES -->
    <h4>User Features:</h4>
    <ul>
        <li>Browse products</li>
        <li>Add items to cart</li>
        <li>Place orders</li>
    </ul>

    <!-- ACTION -->
    <a href="products" class="btn btn-primary mt-3">Go to Products</a>
    <a href="logout" class="btn btn-danger mt-3">Logout</a>

</div>

</body>
</html>