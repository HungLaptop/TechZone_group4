<%-- 
    Document   : order-detail
    Created on : Mar 23, 2026, 1:28:36 AM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, model.OrderDetail" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Order Detail</title>

        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Icons -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

        <style>
            .card {
                border-radius: 15px;
            }

            .item-row:hover {
                background-color: #f8f9fa;
                transition: 0.2s;
            }
        </style>
    </head>
    <body>

        <jsp:include page="navbar.jsp"/>

        <div class="container mt-5">

            <!-- HEADER -->
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3>
                    <i class="bi bi-receipt"></i>
                    Order Detail - #${orderId}
                </h3>

                <a href="orders" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left"></i> Back
                </a>
            </div>

            <%
                List<OrderDetail> list = (List<OrderDetail>) request.getAttribute("details");
                double total = 0;
            %>

            <% if (list == null || list.isEmpty()) { %>

            <div class="alert alert-warning text-center">
                <i class="bi bi-exclamation-circle"></i>
                No items found.
            </div>

            <% } else { %>

            <div class="card shadow-sm p-3">

                <!-- TABLE -->
                <table class="table align-middle">

                    <thead class="table-dark text-center">
                        <tr>
                            <th>Product</th>
                            <th width="150">Price</th>
                            <th width="120">Quantity</th>
                            <th width="150">Total</th>
                        </tr>
                    </thead>

                    <tbody>

                        <% for (OrderDetail d : list) {
                            total += d.getTotal();
                        %>

                        <tr class="item-row text-center">

                            <!-- PRODUCT -->
                            <td class="text-start">
                                <i class="bi bi-box"></i>
                                <%= d.getProductName() %>
                            </td>

                            <!-- PRICE -->
                            <td>
                                <span class="text-danger fw-bold">
                                    <%= d.getPrice() %> VND
                                </span>
                            </td>

                            <!-- QTY -->
                            <td>
                                <span class="badge bg-secondary">
                                    x<%= d.getQuantity() %>
                                </span>
                            </td>

                            <!-- TOTAL -->
                            <td class="fw-bold">
                                <%= d.getTotal() %> VND
                            </td>

                        </tr>

                        <% } %>

                    </tbody>

                </table>

                <!-- SUMMARY -->
                <div class="d-flex justify-content-end mt-3">

                    <div class="text-end">

                        <h5>Total Amount</h5>

                        <h3 class="text-danger fw-bold">
                            <%= total %> VND
                        </h3>

                    </div>

                </div>

            </div>

            <% } %>

        </div>

    </body>
</html>