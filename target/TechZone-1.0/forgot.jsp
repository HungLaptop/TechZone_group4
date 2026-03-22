<%-- 
    Document   : forgot
    Created on : Mar 21, 2026, 11:06:04 PM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Forgot Password</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            body {
                background: linear-gradient(to right, #ff758c, #ff7eb3);
            }
            .card {
                border-radius: 15px;
            }
        </style>
    </head>
    <body>

        <div class="container d-flex justify-content-center align-items-center" style="height:100vh">

            <div class="card p-4 shadow" style="width:360px">

                <h3 class="text-center mb-3">🔑 Forgot Your Password?</h3>

                <p class="text-center text-muted mb-3">
                    Enter your email address to receive a verification code.
                </p>

                <form action="forgot" method="post">

                    <!-- EMAIL INPUT -->
                    <input type="email"
                           name="email"
                           class="form-control mb-3"
                           placeholder="Enter your email address"
                           required>

                    <!-- SUBMIT -->
                    <button class="btn btn-danger w-100">
                        Send OTP Code
                    </button>

                    <!-- ERROR MESSAGE -->
                    <c:if test="${not empty error}">
                        <p class="text-danger mt-2 text-center">${error}</p>
                    </c:if>

                </form>

                <!-- BACK TO LOGIN -->
                <div class="text-center mt-3">
                    <a href="login">Back to Login</a>
                </div>

            </div>

        </div>

    </body>
</html>