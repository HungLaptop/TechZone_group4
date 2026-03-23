<%-- 
    Document   : address
    Created on : Mar 23, 2026, 2:15:40 AM
    Author     : Dang Vinh Hung - CE170162
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>My Addresses</title>

        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Icons -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

        <style>
            .card {
                border-radius: 15px;
            }
            .address-item:hover {
                background-color: #f8f9fa;
                transform: scale(1.01);
                transition: 0.2s;
            }
        </style>
    </head>
    <body>

        <jsp:include page="navbar.jsp"/>

        <div class="container mt-5">

            <div class="row justify-content-center">

                <div class="col-md-8">

                    <div class="card shadow-lg p-4">

                        <!-- TITLE -->
                        <h3 class="mb-4 text-center">
                            <i class="bi bi-geo-alt-fill text-danger"></i>
                            My Addresses
                        </h3>

                        <!-- ADD FORM -->
                        <form action="address" method="post" class="mb-4">

                            <div class="input-group">
                                <input type="text"
                                       name="address"
                                       class="form-control"
                                       placeholder="Enter new address..."
                                       required>

                                <button class="btn btn-success">
                                    <i class="bi bi-plus-circle"></i> Add
                                </button>
                            </div>

                        </form>

                        <!-- LIST -->
                        <c:if test="${empty list}">
                            <div class="alert alert-warning text-center">
                                <i class="bi bi-exclamation-circle"></i>
                                No address yet. Please add one!
                            </div>
                        </c:if>

                        <c:if test="${not empty list}">
                            <ul class="list-group">

                                <c:forEach items="${list}" var="a">

                                    <li class="list-group-item d-flex justify-content-between align-items-center address-item">

                                        <div>
                                            <i class="bi bi-geo"></i>
                                            ${a}
                                        </div>

                                        <!-- OPTIONAL DELETE (sau này làm) -->
                                        <button class="btn btn-sm btn-outline-danger">
                                            <i class="bi bi-trash"></i>
                                        </button>

                                    </li>

                                </c:forEach>

                            </ul>
                        </c:if>

                    </div>

                </div>

            </div>

        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>