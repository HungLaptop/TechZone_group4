<%-- 
    Document   : register
    Created on : Mar 21, 2026, 3:29:25 PM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Register</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            body {
                background: #f5f5f5;
            }
            .container {
                margin-top: 80px;
                max-width: 420px;
                background: white;
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0 0 12px rgba(0,0,0,0.1);
            }
        </style>
    </head>
    <body>

        <div class="container">

            <h3 class="text-center mb-4">Create an Account</h3>

            <form action="register" method="post">

                <!-- FULL NAME -->
                <div class="mb-3">
                    <label class="form-label">Full Name</label>
                    <input type="text"
                           name="fullname"
                           class="form-control"
                           placeholder="Enter your full name"
                           required>
                </div>

                <!-- EMAIL -->
                <div class="mb-3">
                    <label class="form-label">Email Address</label>
                    <input type="email"
                           name="email"
                           class="form-control"
                           placeholder="Enter your email"
                           required>
                </div>

                <!-- PASSWORD -->
                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <input type="password"
                           name="password"
                           class="form-control"
                           placeholder="Enter your password"
                           required minlength="6">
                </div>

                <!-- CONFIRM PASSWORD -->
                <div class="mb-3">
                    <label class="form-label">Confirm Password</label>
                    <input type="password"
                           name="confirm"
                           class="form-control"
                           placeholder="Re-enter your password"
                           required>
                </div>

                <!-- ERROR MESSAGE -->
                <c:if test="${not empty error}">
                    <p class="text-danger text-center">${error}</p>
                </c:if>

                <!-- SUBMIT -->
                <button type="submit" class="btn btn-primary w-100">
                    Sign Up
                </button>

                <!-- LOGIN LINK -->
                <div class="text-center mt-3">
                    <span>Already have an account?</span><br>
                    <a href="login">Sign in here</a>
                </div>

            </form>

        </div>

    </body>
</html>