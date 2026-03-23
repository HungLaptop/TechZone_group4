<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, model.Order" %>

<!DOCTYPE html>
<html>
    <head>
        <title>My Orders</title>

        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Icons -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

        <style>
            .order-card {
                border-radius: 15px;
                transition: 0.2s;
            }

            .order-card:hover {
                transform: scale(1.01);
                box-shadow: 0 5px 20px rgba(0,0,0,0.1);
            }
        </style>
    </head>
    <body>

        <jsp:include page="navbar.jsp"/>

        <div class="container mt-5">

            <h2 class="mb-4">
                <i class="bi bi-box-seam"></i> My Orders
            </h2>

            <%
                List<Order> orders = (List<Order>) request.getAttribute("orders");
            %>

            <% if (orders == null || orders.isEmpty()) { %>

            <div class="alert alert-warning text-center">
                <i class="bi bi-exclamation-circle"></i>
                You have no orders yet.
            </div>

            <% } else { %>

            <div class="row">

                <% for (Order o : orders) { %>

                <div class="col-md-6 mb-4">

                    <div class="card order-card p-3 shadow-sm">

                        <!-- HEADER -->
                        <div class="d-flex justify-content-between align-items-center mb-2">
                            <h5 class="mb-0">#<%= o.getOrderId() %></h5>

                            <!-- STATUS -->
                            <%
                                String status = o.getStatus();
                                String color = "secondary";

                                if ("Pending".equalsIgnoreCase(status)) color = "warning";
                                else if ("Done".equalsIgnoreCase(status)) color = "success";
                                else if ("Cancelled".equalsIgnoreCase(status)) color = "danger";
                            %>

                            <span class="badge bg-<%= color %>">
                                <%= status %>
                            </span>
                        </div>

                        <!-- BODY -->
                        <p class="mb-1">
                            <i class="bi bi-calendar"></i>
                            <%= o.getOrderDate() %>
                        </p>

                        <p class="fw-bold text-danger">
                            <i class="bi bi-cash-stack"></i>
                            <%= o.getTotal() %> VND
                        </p>

                        <!-- ACTION -->
                        <div class="text-end">
                            <a href="order-detail?id=<%= o.getOrderId() %>" 
                               class="btn btn-sm btn-primary">
                                <i class="bi bi-eye"></i> View Detail
                            </a>
                        </div>

                    </div>

                </div>

                <% } %>

            </div>

            <% } %>

        </div>

    </body>
</html>