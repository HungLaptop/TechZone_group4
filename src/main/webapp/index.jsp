<%-- 
    Document   : index
    Created on : Mar 21, 2026, 4:23:04 PM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page import="java.util.*, model.Product" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <title>TechZone - Your Ultimate Tech Store</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            .hero {
                background: linear-gradient(135deg, #0d3b66, #1a659e);
                color: white;
                padding: 100px 0;
                text-align: center;
            }

            .hero h1 {
                font-size: 3rem;
            }

            .card:hover {
                transform: scale(1.05);
                transition: 0.3s;
            }

            .feature-box {
                padding: 20px;
            }
        </style>
    </head>

    <body>

        <jsp:include page="navbar.jsp"/>

        <!-- 🔥 HERO SECTION -->
        <div class="hero">
            <div class="container">
                <h1 class="fw-bold">⚡ Welcome to TechZone</h1>

                <p class="lead mt-3">
                    Your one-stop destination for the latest smartphones, laptops, and tech accessories. 
                    Experience premium quality products with unbeatable prices and reliable service.
                </p>

                <div class="mt-4">
                    <a href="products" class="btn btn-warning btn-lg fw-bold me-2">
                        🛒 Shop Now
                    </a>

                    <a href="products?category=phone" class="btn btn-outline-light btn-lg">
                        🔥 Explore Deals
                    </a>
                </div>
            </div>
        </div>

        <!-- 🔥 FEATURES -->
        <div class="container mt-5 text-center">
            <div class="row">

                <div class="col-md-4 feature-box">
                    <h5>🚚 Fast & Reliable Delivery</h5>
                    <p>Get your products delivered quickly and safely to your doorstep.</p>
                </div>

                <div class="col-md-4 feature-box">
                    <h5>💎 Premium Quality Products</h5>
                    <p>We offer only genuine and high-quality electronics from trusted brands.</p>
                </div>

                <div class="col-md-4 feature-box">
                    <h5>🔒 Secure & Easy Payment</h5>
                    <p>Enjoy safe transactions with multiple payment options.</p>
                </div>

            </div>
        </div>

        <!-- 🔥 FEATURED PRODUCTS -->
        <div class="container mt-5">

            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3>🔥 Featured Products</h3>
                <a href="products" class="btn btn-outline-primary btn-sm">View All</a>
            </div>

            <div class="row">

                <%
                    List<Product> list = (List<Product>) request.getAttribute("list");

                    if (list != null && !list.isEmpty()) {
                        for (int i = 0; i < Math.min(4, list.size()); i++) {
                            Product p = list.get(i);
                %>

                <div class="col-md-3">
                    <div class="card shadow mb-4">

                        <!-- IMAGE -->
                        <img src="<%= p.getImage() %>" class="card-img-top">

                        <div class="card-body text-center">

                            <!-- NAME -->
                            <h6><%= p.getName() %></h6>

                            <!-- PRICE -->
                            <p class="text-danger fw-bold"><%= p.getPrice() %> VND</p>

                            <!-- BUTTON -->
                            <a href="cart?id=<%= p.getId() %>" 
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

                <div class="col-12 text-center">
                    <div class="alert alert-warning">
                        No featured products available.
                    </div>
                </div>

                <%
                    }
                %>

            </div>

        </div>

    </body>
</html>