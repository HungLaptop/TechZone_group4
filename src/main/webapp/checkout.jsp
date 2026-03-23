<%-- 
    Document   : checkout
    Created on : Mar 23, 2026, 2:31:02 AM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, model.CartItem" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Checkout</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    </head>
    <body>

        <jsp:include page="navbar.jsp"/>

        <div class="container mt-5">

            <h3>🧾 Checkout</h3>

            <%
                List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
                double total = 0;
            %>

            <% if (cart == null || cart.isEmpty()) { %>

            <div class="alert alert-warning">
                Your cart is empty.
            </div>

            <% } else { %>

            <!-- CART REVIEW -->
            <div class="card p-3 mb-4 shadow-sm">

                <h5>Order Summary</h5>

                <table class="table mt-3">
                    <thead class="table-dark">
                        <tr>
                            <th>Product</th>
                            <th>Price</th>
                            <th>Qty</th>
                            <th>Total</th>
                        </tr>
                    </thead>

                    <tbody>
                        <% for (CartItem item : cart) {
                            double t = item.getPrice() * item.getQuantity();
                            total += t;
                        %>

                        <tr>
                            <td><%= item.getName() %></td>
                            <td><%= item.getPrice() %> VND</td>
                            <td><%= item.getQuantity() %></td>
                            <td><%= t %> VND</td>
                        </tr>

                        <% } %>
                    </tbody>
                </table>

                <h4 class="text-end text-danger">Total: <%= total %> VND</h4>

            </div>

            <!-- ADDRESS -->
            <div class="card p-3 mb-4 shadow-sm">

                <h5>Shipping Address</h5>

                <textarea class="form-control mt-2" placeholder="Enter your address..." required></textarea>

            </div>

            <!-- BUTTON -->
            <div class="text-end">

                <form action="checkout" method="post">
                    <button class="btn btn-success btn-lg">
                        Confirm Order
                    </button>
                </form>

            </div>

            <% } %>

        </div>

    </body>
</html>