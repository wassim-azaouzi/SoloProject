<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Details Page for Creator/Viewer</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
	<h1> Welcome <c:out value="${user.firstName} ${user.lastName}"/> !</h1>

    <form action="/logout" method="POST">
        <button type="submit" class="btn btn-danger">Logout</button>
    </form>
	<div class="container">
	    <c:if test="${user.id == book.leadUser.id}">
	        <form:form modelAttribute="book" action="/books/edit/${book.id}" method="POST">
	            <input type="hidden" name="_method" value="put">
	
	            <div class="form-group">
	                <form:label path="title">Title:</form:label>
	                <form:errors path="title" cssClass="text-danger" />
	                <form:input path="title" class="form-control" />
	            </div>
	            <p>Added by: ${book.leadUser.firstName} ${book.leadUser.lastName}</p>
	            <p>Added on: ${book.createdAt}</p>
	            <p>Last updated on: ${book.updatedAt}</p>
	            <div class="form-group">
	                <form:label path="description">Description:</form:label>
	                <form:errors path="description" cssClass="text-danger" />
	                <form:textarea rows="4" class="form-control" path="description" />
	            </div>
	            <form:hidden path="id" />
	            <form:hidden path="favoritedByUsers" />
	            <form:hidden path="leadUser" />
	            
	            
	            <button type="submit" class="btn btn-primary">Update</button>
	        </form:form>
	        <form action="/books/delete/${book.id}" method="POST">
       			<input type="hidden" name="_method" value="delete">
        	<button type="submit" class="btn btn-danger">Delete</button>
    </form>
	    </c:if>
	    <c:if test="${user.id != book.leadUser.id}">
	        <h1>${book.title}</h1>
	        <p>Added by: ${book.leadUser.firstName} ${book.leadUser.lastName}</p>
	        <p>Added on: ${book.createdAt}</p>
	        <p>Last updated on: ${book.updatedAt}</p>
	        <p>Description: ${book.description}</p>
	    </c:if>
	</div>
	
	<div class="container">
	    <h1>Users who liked this book</h1>
	    <ul>
	        <c:forEach var="potentialuser" items="${book.favoritedByUsers}">
	            <li>
	                <c:choose>
	                    <c:when test="${user.id == potentialuser.id}">
	                        ${potentialuser.firstName} ${potentialuser.lastName}
	                        <form action="/removeFavorite/${book.id}" method="POST" style="display:inline;">
	                            <input type="hidden" name="_method" value="delete">
	                            <button type="submit" class="btn btn-danger">Unfavorite</button>
	                        </form>
	                    </c:when>
	                    <c:otherwise>
	                        ${potentialuser.firstName} ${potentialuser.lastName}
	                    </c:otherwise>
	                </c:choose>
	            </li>
	        </c:forEach>
	    </ul>
	</div>
	
	<div class="container">	
	 	<c:if test="${not empty user and not book.favoritedByUsers.contains(user)}">
		    <form action="/addFavorite/${book.id}" method="POST">
		        <button type="submit" class="btn btn-primary">Add to Favorites</button>
		    </form>
		</c:if>
	</div>
    
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
