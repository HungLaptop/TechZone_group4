<%-- 
    Document   : staff
    Created on : Mar 21, 2026, 4:06:48 PM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page import="model.Account" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    Account acc = (Account) session.getAttribute("account");

    // Authorization check
    if (acc == null || !"staff".equals(acc.getRole())) {
        response.sendRedirect("login");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Staff Dashboard</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

        <div class="container mt-4">

            <h2>Staff Dashboard</h2>

            <div class="card p-4 mt-3 shadow-sm">

                <h5>Staff Responsibilities</h5>

                <ul class="mt-3">
                    <li>Manage customer orders</li>
                    <li>Confirm and process orders</li>
                    <li>Provide customer support</li>
                </ul>

            </div>

            <a href="orders" class="btn btn-primary mt-3">
                View Orders
            </a>

        </div>

    </body>
</html>