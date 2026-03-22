<%-- 
    Document   : index
    Created on : Mar 21, 2026, 4:23:04 PM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page import="java.util.*, model.Product, dao.ProductDAO" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <title>TechZone - Home</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            .banner {
                height: 300px;
                background: linear-gradient(to right, #000428, #004e92);
                color: white;
                display: flex;
                align-items: center;
                justify-content: center;
                text-align: center;
            }

            .card:hover {
                transform: scale(1.05);
                transition: 0.3s;
            }
        </style>
    </head>
    <body>

        <jsp:include page="navbar.jsp"/>

        <!-- 🔥 HERO BANNER -->
        <div class="banner">
            <h1>🔥 Hot Deals on Smartphones 🔥</h1>
        </div>

        <!-- 🔥 FEATURED PRODUCTS -->
        <div class="container mt-4">

            <h3 class="mb-3">Featured Products</h3>

            <div class="row">

                <%
                    ProductDAO dao = new ProductDAO();
                    List<Product> list = dao.getAll();

                    for (int i = 0; i < Math.min(4, list.size()); i++) {
                        Product p = list.get(i);
                %>

                <div class="col-md-3">
                    <div class="card shadow mb-4">

                        <!-- PRODUCT IMAGE -->
                        <img src="<%= p.getImage() %>" class="card-img-top">

                        <div class="card-body text-center">

                            <!-- PRODUCT NAME -->
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
                %>

            </div>

        </div>

    </body>
</html>