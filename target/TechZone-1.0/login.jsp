<%-- 
    Document   : login
    Created on : Mar 21, 2026, 2:52:16 PM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            body {
                background: linear-gradient(to right, #667eea, #764ba2);
            }
            .card {
                border-radius: 15px;
            }
        </style>
    </head>
    <body>

        <div class="container d-flex justify-content-center align-items-center" style="height:100vh">

            <div class="card p-4 shadow" style="width:360px">

                <h3 class="text-center mb-3">🔐 Login</h3>

                <!-- SUCCESS MESSAGE -->
                <c:if test="${not empty success}">
                    <p class="text-success text-center">${success}</p>
                </c:if>

                <form action="login" method="post">

                    <!-- EMAIL -->
                    <input type="email"
                           name="email"
                           class="form-control mb-3"
                           placeholder="Enter your email"
                           required>

                    <!-- PASSWORD -->
                    <input type="password"
                           name="password"
                           class="form-control mb-3"
                           placeholder="Enter your password"
                           required>

                    <!-- SUBMIT -->
                    <button class="btn btn-primary w-100">
                        Sign In
                    </button>

                    <!-- ERROR MESSAGE -->
                    <c:if test="${not empty error}">
                        <p class="text-danger mt-2 text-center">${error}</p>
                    </c:if>

                </form>

                <!-- FORGOT PASSWORD -->
                <div class="text-center mt-2">
                    <a href="forgot">Forgot password?</a>
                </div>

                <!-- REGISTER -->
                <div class="text-center mt-3">
                    <span>Don't have an account?</span><br>
                    <a href="register">Create one</a>
                </div>

            </div>

        </div>

    </body>
</html>