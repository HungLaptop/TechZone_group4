<%-- 
    Document   : verify
    Created on : Mar 23, 2026, 12:00:28 AM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Verify OTP</title>

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

                <h3 class="text-center mb-3">📩 OTP Verification</h3>

                <p class="text-center text-muted">
                    Please enter the OTP code sent to your email.
                </p>

                <form action="verify" method="post">

                    <!-- OTP INPUT -->
                    <input type="text"
                           name="otp"
                           class="form-control mb-3 text-center"
                           placeholder="Enter OTP code"
                           required>

                    <!-- SUBMIT -->
                    <button class="btn btn-success w-100">
                        Verify
                    </button>

                    <!-- ERROR MESSAGE -->
                    <c:if test="${not empty error}">
                        <p class="text-danger mt-2 text-center">${error}</p>
                    </c:if>

                </form>

            </div>

        </div>

    </body>
</html>