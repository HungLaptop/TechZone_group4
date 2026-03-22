<%-- 
    Document   : cart
    Created on : Mar 21, 2026, 3:53:49 PM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page import="java.util.*, model.CartItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

            <!-- EMPTY CART -->
            <c:if test="${empty cartItems}">
                <div class="alert alert-warning mt-3">
                    Your cart is currently empty.
                </div>
            </c:if>

            <!-- CART TABLE -->
            <c:if test="${not empty cartItems}">

                <table class="table table-bordered mt-3 text-center align-middle">

                    <thead class="table-dark">
                        <tr>
                            <th>Product Image</th>
                            <th>Product Name</th>
                            <th>Unit Price</th>
                            <th>Quantity</th>
                            <th>Total Price</th>
                            <th>Action</th>
                        </tr>
                    </thead>

                    <tbody>

                        <c:set var="grandTotal" value="0"/>

                        <c:forEach items="${cartItems}" var="item">

                            <tr>
                                <!-- IMAGE -->
                                <td>
                                    <img src="${item.key.image}" width="80" class="img-fluid">
                                </td>

                                <!-- NAME -->
                                <td>${item.key.name}</td>

                                <!-- PRICE -->
                                <td>${item.key.price} VND</td>

                                <!-- QUANTITY UPDATE -->
                                <td>
                                    <form action="update-cart" method="post" class="d-flex justify-content-center gap-2">
                                        <input type="hidden" name="id" value="${item.key.id}">

                                        <input type="number"
                                               name="quantity"
                                               value="${item.value}"
                                               min="1"
                                               class="form-control"
                                               style="width:80px">

                                        <button class="btn btn-sm btn-primary">Update</button>
                                    </form>
                                </td>

                                <!-- ITEM TOTAL -->
                                <td>
                                    ${item.key.price * item.value} VND
                                </td>

                                <!-- REMOVE -->
                                <td>
                                    <form action="update-cart" method="post">
                                        <input type="hidden" name="id" value="${item.key.id}">
                                        <input type="hidden" name="quantity" value="0">
                                        <button class="btn btn-sm btn-danger">Remove</button>
                                    </form>
                                </td>
                            </tr>

                            <!-- GRAND TOTAL -->
                            <c:set var="grandTotal" value="${grandTotal + (item.key.price * item.value)}"/>

                        </c:forEach>

                    </tbody>

                </table>

                <!-- TOTAL -->
                <div class="d-flex justify-content-between align-items-center mt-3">

                    <h4>Total Amount: ${grandTotal} VND</h4>

                    <a href="checkout" class="btn btn-success">
                        Proceed to Checkout
                    </a>

                </div>

            </c:if>

        </div>

    </body>
</html>