<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book Club</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h1> Welcome <c:out value="${user.firstName} ${user.lastName}"/> !</h1>

    <form action="/logout" method="POST">
        <button type="submit" class="btn btn-danger">Logout</button>
    </form>



	<div class="container">
	    <h1 class="mt-3">Add a new Book</h1>	    
	
		<form:form action="/books/new" method="post" modelAttribute="newBook" class="mt-3">
	
		    <div class="form-group">
		        <form:label path="title">Title:</form:label>
		        <form:errors path="title" cssClass="text-danger"/>
		        <form:input path="title" class="form-control"/>
		    </div>
		    <div class="form-group">
		        <form:label path="description">Description:</form:label>
		        <form:errors path="description" cssClass="text-danger"/>
				<form:textarea rows="4" class="form-control" path="description"/>	    
			</div>
		    
		    
		    <form:hidden path="leadUser" value="${user.id}" />
		    
		    <input type="submit" class="btn btn-primary" value="Add"/>
		    
		</form:form> 
	</div>
	
	<div class="container">
	    <h3 class="mt-4">All Books</h3>
	
	    <table class="table table-striped">
	        <thead>
	            <tr>
	                <th>Book</th>
	                <th>Added by</th>
	                <th>Action</th>
	            </tr>
	        </thead>
	        <tbody>
				<c:forEach var="book" items="${books}">
				    <tr>
				        <td><a href="/books/${book.id}">${book.title}</a></td>
				        <td>${book.leadUser.firstName} ${book.leadUser.lastName}</td>
				        <td>
							<c:if test="${user.favorite_books.contains(book)}">
					        	<p>This is one of your favorites</p>
					    	</c:if>
					    	<c:if test="${!user.favorite_books.contains(book)}">
					        	<form action="/addFavorite/${book.id}" method="POST">
					            	<button type="submit" class="btn btn-primary">Add to Favorites</button>
					        	</form>
					    	</c:if>
				        </td>
				    </tr>
				</c:forEach>
	        </tbody>
	    </table>
	</div>
    
    
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
