<%-- 
    Document   : admin.jsp
    Created on : Mar 21, 2026, 3:17:15 PM
    Author     : Dang Vinh Hung - CE170162
--%>

<%-- 
    File: admin.jsp
    Description: Admin Dashboard Page
--%>

<%@ page import="model.Account" %>
<%
    Account acc = (Account) session.getAttribute("account");

    // Authorization check
    if (acc == null || !"admin".equals(acc.getRole())) {
        response.sendRedirect("login");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-4">

    <h2>Admin Dashboard</h2>

    <div class="row mt-3">

        <!-- Product Management -->
        <div class="col-md-4">
            <div class="card p-3 shadow">
                <h5>Product Management</h5>
            </div>
        </div>

        <!-- User Management -->
        <div class="col-md-4">
            <div class="card p-3 shadow">
                <h5>User Management</h5>
            </div>
        </div>

        <!-- Analytics -->
        <div class="col-md-4">
            <div class="card p-3 shadow">
                <h5>Analytics & Reports</h5>
            </div>
        </div>

    </div>

</div>

</body>
</html>