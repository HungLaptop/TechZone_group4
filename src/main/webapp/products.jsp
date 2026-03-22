<%-- 
    Document   : products
    Created on : Mar 21, 2026, 3:43:33 PM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page import="java.util.*, model.Product" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Shop</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

        <jsp:include page="navbar.jsp"/>

        <div class="container mt-4">

            <div class="row">

                <!-- SIDEBAR -->
                <div class="col-md-3">

                    <!-- CATEGORY -->
                    <div class="card p-3 shadow-sm mb-3">
                        <h5>Categories</h5>

                        <a href="products" class="d-block">All Products</a>
                        <a href="products?category=phone" class="d-block">Smartphones</a>
                        <a href="products?category=laptop" class="d-block">Laptops</a>
                        <a href="products?category=tablet" class="d-block">Tablets</a>
                        <a href="products?category=accessory" class="d-block">Accessories</a>
                    </div>

                    <!-- PRICE FILTER -->
                    <div class="card p-3 shadow-sm">
                        <h5>Price Filter</h5>

                        <a href="products?price=1" class="d-block">Below 5,000,000 VND</a>
                        <a href="products?price=2" class="d-block">5,000,000 - 10,000,000 VND</a>
                        <a href="products?price=3" class="d-block">10,000,000 - 20,000,000 VND</a>
                        <a href="products?price=4" class="d-block">Above 20,000,000 VND</a>
                    </div>

                </div>

                <!-- PRODUCT LIST -->
                <div class="col-md-9">

                    <div class="row">

                        <%
                            List<Product> list = (List<Product>) request.getAttribute("list");
                            if (list != null && !list.isEmpty()) {
                                for (Product p : list) {
                        %>

                        <div class="col-md-4">
                            <div class="card shadow-sm mb-4">

                                <!-- IMAGE -->
                                <img src="<%= p.getImage() %>" class="card-img-top">

                                <div class="card-body text-center">

                                    <!-- NAME -->
                                    <h6><%= p.getName() %></h6>

                                    <!-- PRICE -->
                                    <p class="text-danger fw-bold"><%= p.getPrice() %> VND</p>

                                    <!-- ADD TO CART -->
                                    <a href="cart?id=<%= p.getId() %>&name=<%= p.getName() %>&price=<%= p.getPrice() %>"
                                       class="btn btn-success btn-sm">
                                        Add to Cart
                                    </a>

                                </div>

                            </div>
                        </div>

                        <%
                                }
                            } else {
                        %>

                        <!-- NO PRODUCT -->
                        <div class="col-12">
                            <div class="alert alert-warning text-center">
                                No products found.
                            </div>
                        </div>

                        <%
                            }
                        %>

                    </div>

                </div>

            </div>

        </div>

    </body>
</html>