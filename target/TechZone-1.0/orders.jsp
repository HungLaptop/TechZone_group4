<%-- 
    Document   : orders
    Created on : Mar 21, 2026, 3:59:10 PM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
    <head>
        <title>My Orders</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

        <div class="container mt-4">

            <h2>📦 My Orders</h2>

            <%
                List<String> orders = (List<String>) request.getAttribute("orders");
            %>

            <!-- NO ORDERS -->
            <%
                if (orders == null || orders.isEmpty()) {
            %>
            <div class="alert alert-warning mt-3">
                You have no orders yet.
            </div>
            <%
                } else {
            %>

            <!-- ORDER LIST -->
            <div class="mt-3">
                <%
                    for (String o : orders) {
                %>
                <div class="card p-3 mb-2 shadow-sm">
                    <%= o %>
                </div>
                <%
                    }
                %>
            </div>

            <%
                }
            %>

        </div>

    </body>
</html>