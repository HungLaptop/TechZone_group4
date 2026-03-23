<%-- 
    Document   : profile
    Created on : Mar 23, 2026, 2:07:33 AM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Account" %>

<%
    Account acc = (Account) request.getAttribute("account");
%>

<!DOCTYPE html>
<html>
    <head>
        <title>My Profile</title>

        <!-- 🔥 Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Bootstrap Icons -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    </head>
    <body>

        <jsp:include page="navbar.jsp"/>

        <div class="container mt-5">

            <div class="row justify-content-center">

                <div class="col-md-6">

                    <div class="card shadow-lg p-4">

                        <h3 class="text-center mb-4">
                            <i class="bi bi-person-circle"></i> My Profile
                        </h3>

                        <!-- UPDATE INFO -->
                        <form action="profile" method="post">

                            <div class="mb-3">
                                <label class="form-label">Full Name</label>
                                <input type="text"
                                       class="form-control"
                                       name="name"
                                       value="<%= acc.getFullName() %>">
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Email</label>
                                <input type="email"
                                       class="form-control"
                                       name="email"
                                       value="<%= acc.getEmail() %>">
                            </div>

                            <button class="btn btn-primary w-100 mb-3">
                                <i class="bi bi-save"></i> Update Info
                            </button>

                        </form>

                        <hr>

                        <!-- CHANGE PASSWORD -->
                        <form action="change-password" method="post">

                            <div class="mb-3">
                                <label class="form-label">Old Password</label>
                                <input type="password"
                                       name="oldPassword"
                                       class="form-control">
                            </div>

                            <div class="mb-3">
                                <label class="form-label">New Password</label>
                                <input type="password"
                                       name="newPassword"
                                       class="form-control">
                            </div>

                            <button class="btn btn-danger w-100">
                                <i class="bi bi-key"></i> Change Password
                            </button>

                        </form>

                        <!-- MESSAGE -->
                        <% if (request.getAttribute("error") != null) { %>
                        <div class="alert alert-danger mt-3">
                            <%= request.getAttribute("error") %>
                        </div>
                        <% } %>

                        <% if (request.getAttribute("success") != null) { %>
                        <div class="alert alert-success mt-3">
                            <%= request.getAttribute("success") %>
                        </div>
                        <% } %>

                    </div>

                </div>

            </div>

        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>