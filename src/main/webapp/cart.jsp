<%-- 
    Document   : cart
    Created on : Mar 21, 2026, 3:53:49 PM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, model.CartItem" %>

<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<jsp:include page="navbar.jsp"/>

<div class="container mt-4">

    <h2>Shopping Cart</h2>

    <%
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        double grandTotal = 0;
    %>

    <% if (cart == null || cart.isEmpty()) { %>

        <div class="alert alert-warning mt-3">
            Your cart is currently empty.
        </div>

    <% } else { %>

        <table class="table table-bordered text-center align-middle mt-3">

            <thead class="table-dark">
                <tr>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                    <th>Action</th>
                </tr>
            </thead>

            <tbody>

                <% for (CartItem item : cart) {
                    double total = item.getPrice() * item.getQuantity();
                    grandTotal += total;
                %>

                <tr>
                    <td><%= item.getName() %></td>
                    <td><%= item.getPrice() %> VND</td>

                    <td>
                        <form action="update-cart" method="post" class="d-flex justify-content-center gap-2">
                            <input type="hidden" name="id" value="<%= item.getId() %>">

                            <input type="number"
                                   name="quantity"
                                   value="<%= item.getQuantity() %>"
                                   min="1"
                                   class="form-control"
                                   style="width:80px">

                            <button class="btn btn-sm btn-primary">Update</button>
                        </form>
                    </td>

                    <td><%= total %> VND</td>

                    <td>
                        <form action="update-cart" method="post">
                            <input type="hidden" name="id" value="<%= item.getId() %>">
                            <input type="hidden" name="quantity" value="0">
                            <button class="btn btn-danger btn-sm">Remove</button>
                        </form>
                    </td>
                </tr>

                <% } %>

            </tbody>

        </table>

        <div class="d-flex justify-content-between mt-3">
            <h4>Total: <%= grandTotal %> VND</h4>

            <a href="checkout" class="btn btn-success">
                Checkout
            </a>
        </div>

    <% } %>

</div>

</body>
</html>