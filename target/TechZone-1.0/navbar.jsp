<%-- 
    Document   : navbar
    Created on : Mar 21, 2026, 4:28:25 PM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page import="model.Account" %>

<%
    Account acc = (Account) session.getAttribute("account");
%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">

        <!-- LOGO -->
        <a class="navbar-brand fw-bold" href="index.jsp">? TechZone</a>

        <!-- TOGGLE (mobile) -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- MENU -->
        <div class="collapse navbar-collapse" id="navbarContent">

            <!-- LEFT MENU -->
            <ul class="navbar-nav me-auto">

                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">Home</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="products">Shop</a>
                </li>

                <!-- CATEGORIES -->
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

            <!-- CART -->
            <a href="cart.jsp" class="btn btn-warning me-2">?</a>

            <!-- ACCOUNT -->
            <%
                if (acc == null) {
            %>

            <a href="login" class="btn btn-outline-light me-2">Login</a>
            <a href="register" class="btn btn-success">Sign Up</a>

            <%
                } else {
            %>

            <!-- USER NAME -->
            <span class="text-white me-2">
                ? <%= acc.getFullName() != null ? acc.getFullName() : acc.getEmail() %>
            </span>

            <!-- ADMIN BUTTON -->
            <%
                if ("admin".equals(acc.getRole())) {
            %>
            <a href="admin/dashboard" class="btn btn-danger me-2">Admin</a>
            <%
                }
            %>

            <a href="logout" class="btn btn-outline-light">Logout</a>

            <%
                }
            %>

        </div>

    </div>
</nav>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>