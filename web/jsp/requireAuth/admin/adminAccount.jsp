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
</head>
<body>
<h2>Add book to catalog</h2>
<%--сделать валидацию форм и дизайн--%>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="addBook">
    <input name="title" placeholder="Book title" required>
    <input name="author" placeholder="Author" required>
    <input name="genre" placeholder="Genre" required>
    <input name="category" placeholder="Category" required>
    <input name="publisher" placeholder="Publisher" required>
    <input name="country" placeholder="Country" required>
    <input name="year" placeholder="Year" required>
    <input name="rating" placeholder="Rating" required>
    <input name="quantity" placeholder="Quantity" required>
    <button type="submit">Add book</button>
</form>
<h2>Delete book by id</h2>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="deleteBook">
    <input name="book_id" placeholder="Book id">
    <button type="submit">Delete book</button>
</form>
</body>
</html>
