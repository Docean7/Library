<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%--
  Created by IntelliJ IDEA.
  User: Денис
  Date: 15.01.2018
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your account</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="../../css/account.css"/> "/>
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


<h2>Hello, ${sessionScope.firstname} ${sessionScope.lastname}</h2>
<br/>
<p><strong>Username: ${sessionScope.login}</strong></p>
<p><strong>Telephone number: ${sessionScope.telnumber}</strong></p>
<p><strong> Email : ${sessionScope.email}</strong></p>

<table class="table table-striped">
    <tr>
        <th>Title</th>
        <th>Author</th>
        <th>Genre</th>
        <th>Category</th>
        <th>Publisher</th>
        <th>Country</th>
        <th>Year</th>
        <th>Rating</th>
        <th>Expiration</th>
    </tr>
    <c:forEach var="book" items="${sessionScope.get('bookList')}">
        <tr>
            <td>${book.title}</td>
            <td>${book.author}</td>
            <td>${book.genre}</td>
            <td>${book.category}</td>
            <td>${book.publisher}</td>
            <td>${book.country}</td>
            <td>${book.year}</td>
            <td>${book.rating}</td>
            <td>
                <c:choose>
                    <c:when test="${not empty book.expiration}">
                        <c:set var="fineAmount" value="${ctg:calculateForfeit(book.expiration)}"/>
                        expires on ${book.expiration} <c:if test="${fineAmount > 0}"><p class="s-error">Fine
                        of ${fineAmount} hryvnas</p></c:if>
                    </c:when>
                    <c:otherwise>
                        Not delivered
                    </c:otherwise>
                </c:choose>

            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
