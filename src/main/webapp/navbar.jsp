<%-- 
    Document   : navbar
    Created on : Mar 21, 2026, 4:28:25 PM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page import="model.Account, java.util.*, model.CartItem" %>

<%
    Account acc = (Account) session.getAttribute("account");
    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
    int cartSize = (cart != null) ? cart.size() : 0;
%>

<!-- ? BOOTSTRAP ICON -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">

        <!-- LOGO -->
        <a class="navbar-brand fw-bold" href="home">
            <i class="bi bi-lightning-charge-fill text-warning"></i> TechZone
        </a>

        <!-- TOGGLE -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- MENU -->
        <div class="collapse navbar-collapse" id="navbarContent">

            <!-- LEFT MENU -->
            <ul class="navbar-nav me-auto">

                <li class="nav-item">
                    <a class="nav-link" href="home">Home</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="products">Shop</a>
                </li>

                <!-- CATEGORY -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">
                        Categories
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="products?category=phone">Smartphones</a></li>
                        <li><a class="dropdown-item" href="products?category=laptop">Laptops</a></li>
                        <li><a class="dropdown-item" href="products?category=tablet">Tablets</a></li>
                        <li><a class="dropdown-item" href="products?category=accessory">Accessories</a></li>
                    </ul>
                </li>

            </ul>

            <!-- SEARCH -->
            <form action="products" method="get" class="d-flex me-3">
                <input class="form-control me-2"
                       type="text"
                       name="search"
                       placeholder="Search products...">
                <button class="btn btn-outline-light">Search</button>
            </form>

            <!-- ? CART (FIX CHU?N) -->
            <a href="cart" class="btn btn-warning position-relative me-3">
                <i class="bi bi-cart"></i>

                <% if (cartSize > 0) { %>
                <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                    <%= cartSize %>
                </span>
                <% } %>
            </a>

            <!-- ACCOUNT -->
            <%
                if (acc == null) {
            %>

            <a href="login" class="btn btn-outline-light me-2">Login</a>
            <a href="register" class="btn btn-success">Sign Up</a>

            <%
                } else {
            %>

            <!-- USER -->
            <span class="text-white me-2">
                <i class="bi bi-person-circle"></i>
                <%= acc.getFullName() != null ? acc.getFullName() : acc.getEmail() %>
            </span>

            <!-- ADMIN -->
            <%
                if ("admin".equals(acc.getRole())) {
            %>
            <a href="admin/dashboard" class="btn btn-danger me-2">
                <i class="bi bi-speedometer2"></i> Admin
            </a>
            <%
                }
            %>
            <!-- PROFILE BUTTON -->
            <a href="profile" class="btn btn-info me-2">
                <i class="bi bi-person"></i>
            </a>

            <!-- LOGOUT -->
            <a href="logout" class="btn btn-outline-light">
                <i class="bi bi-box-arrow-right"></i> Logout
            </a>

            <%
                }
            %>

        </div>

    </div>
</nav>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>