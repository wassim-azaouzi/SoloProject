<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login & Registration</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

    <div class="container mt-5">

        <div class="row">
            <!-- Register Form -->
            <div class="col-md-6">
                <h2>Registration</h2>
                <form:form action="/register" method="post" modelAttribute="newUser">

                    <div class="form-group">
                        <form:label path="firstName">First Name:</form:label>
                        <form:errors path="firstName" cssClass="text-danger"/>
                        <form:input path="firstName" class="form-control"/>
                    </div>
                    <div class="form-group"> 
                        <form:label path="lastName">Last Name:</form:label>
                        <form:errors path="lastName" cssClass="text-danger"/>
                        <form:input path="lastName" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <form:label path="email">Email:</form:label>
                        <form:errors path="email" cssClass="text-danger"/>
                        <form:input path="email" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <form:label path="password">Password:</form:label>
                        <form:errors path="password" cssClass="text-danger"/>
                        <form:input type="password" path="password" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <form:label path="confirm">Confirm Password:</form:label>
                        <form:errors path="confirm" cssClass="text-danger"/>
                        <form:input type="password" path="confirm" class="form-control"/>
                    </div>
                    <button type="submit" class="btn btn-primary">Register</button>
                </form:form>
            </div>

            <!-- Login Form -->
            <div class="col-md-6">
                <h2>Login</h2>
                <form:form action="/login" method="post" modelAttribute="newLogin">

                    <div class="form-group">
                        <form:label path="email">Email:</form:label>
                        <form:errors path="email" cssClass="text-danger"/>
                        <form:input path="email" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <form:label path="password">Password:</form:label>
                        <form:errors path="password" cssClass="text-danger"/>
                        <form:input type="password" path="password" class="form-control"/>
                    </div>
                    <button type="submit" class="btn btn-primary">Login</button>
                </form:form>
            </div>
        </div>

    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>