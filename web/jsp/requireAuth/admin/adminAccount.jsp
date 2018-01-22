<%--
  Created by IntelliJ IDEA.
  User: Денис
  Date: 21.01.2018
  Time: 21:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin panel</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <li role="presentation"><a href="<c:url value="/jsp/requireAuth/catalog.jsp"/>">Catalog</a></li>
            <li role="presentation" class="active"><a href="#">Profile</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <form action="/controller" method="post">
                <input type="hidden" name="command" value="Logout">
                <button type="submit" class="btn btn-danger navbar-btn">Logout</button>
            </form>
        </ul>
    </div>
</nav>

<h2>Add book to catalog</h2>
<%--сделать валидацию форм и дизайн--%>
<form action="/controller" method="post">

    <input type="hidden" name="command" value="addBook">

    <div class="form-group">
        <input name="title" placeholder="Book title" class="form-control" required>
    </div>
    <div class="form-group">
        <input name="author" placeholder="Author" class="form-control" required>
    </div>
    <div class="form-group">
        <input name="genre" placeholder="Genre" class="form-control" required>
    </div>
    <div class="form-group">
        <input name="category" placeholder="Category" class="form-control" required>
    </div>
    <div class="form-group">
        <input name="publisher" placeholder="Publisher" class="form-control" required>
    </div>

    <div class="form-group">
        <input name="country" placeholder="Country" class="form-control" required>
    </div>
    <div class="form-group">
        <input name="year" placeholder="Year" class="form-control" required>
    </div>
    <div class="form-group">
        <input name="rating" placeholder="Rating" class="form-control" required>
    </div>
    <div class="form-group">
        <input name="quantity" placeholder="Quantity" class="form-control" required>
    </div>

    <button type="submit" class="btn btn-default">Add book</button>
</form>
<h2>Delete book by id</h2>
<form action="/controller" method="post">
    <input type="hidden" name="command"  value="deleteBook">
    <div class="form-group">
        <input name="book_id" placeholder="Book id" class="form-control" required>
    </div>

    <button type="submit" class="btn btn-default">Delete book</button>
</form>

<h2>Edit book</h2>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="FindBook">
    <div class="form-group">
        <input name="book_id" placeholder="Book id" class="form-control" required>
    </div>
    <button type="submit" class="btn btn-default">Find</button>
</form>
<c:set var="rBook" value="${requestScope.get('returnedBook')}"/>
<c:if test="${not empty rBook}">

    <form action="/controller" method="post">
        <input type="hidden" name="command" value="EditBook">
        <input type="hidden" name="book_id" value="${rBook.id}">
        <div class="form-group">
            <input name="title" value="${rBook.title}" class="form-control">
        </div>
        <div class="form-group">
            <input name="author"  value="${rBook.author}" class="form-control">
        </div>
        <div class="form-group">
            <input name="genre"  value="${rBook.genre}" class="form-control">
        </div>
        <div class="form-group">
            <input name="category"  value="${rBook.category}" class="form-control">
        </div>
        <div class="form-group">
            <input name="publisher"  value="${rBook.publisher}" class="form-control">
        </div>
        <div class="form-group">
            <input name="country"  value="${rBook.country}" class="form-control">
        </div>
        <div class="form-group">
            <input name="year"  value="${rBook.year}" class="form-control">
        </div>
        <div class="form-group">
            <input name="rating"  value="${rBook.rating}" class="form-control">
        </div>
        <div class="form-group">
            <input name="quantity"  value="${rBook.quantity}" class="form-control">
        </div>

        <button type="submit" class="btn btn-default">Edit</button>
    </form>
</c:if>
<h2>Add librarian</h2>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="ChangeType">
    <input type="hidden" name="type" value="2">
    <div class="form-group">
        <input name="username" class="form-control" placeholder="Username" required>
    </div>
   <button type="submit" class="btn btn-default">Make librarian</button>
</form>
<h2>Delete librarian</h2>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="ChangeType">
    <input type="hidden" name="type" value="0">
    <div class="form-group">
        <input name="username" class="form-control" placeholder="Username" required>
    </div>
    <button type="submit" class="btn btn-default">Delete librarian</button>
</form>
<h2>Ban user</h2>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="ChangeType">
    <input type="hidden" name="type" value="4">
    <div class="form-group">
        <input name="username" class="form-control" placeholder="Username" required>
    </div>
    <button type="submit" class="btn btn-default">Ban user</button>
</form>

<h2>Unban user</h2>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="ChangeType">
    <input type="hidden" name="type" value="0">
    <div class="form-group">
        <input name="username" class="form-control" placeholder="Username" required>
    </div>
    <button type="submit" class="btn btn-default">Ban user</button>
</form>

</body>
</html>
