<%--
  Created by IntelliJ IDEA.
  User: Денис
  Date: 12.01.2018
  Time: 12:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Search result</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
</head>
<body>

<c:set var="books" value="${sessionScope.get('foundBooks')}"/>
<c:choose>
    <c:when test="${not empty books }">
        <h1>Found:</h1><br/>
        <c:forEach var="found" items="${books}">
        Book: <c:out value="${found}"/><p>
            <form action="/controller" method="post">
                <input type="hidden" name="command" value="AddBookToUser"/>
                <input type="hidden" name="user_id" value="${sessionScope.userId}"/>
                <input type="hidden" name="book_id" value="${found.id}"/>
                <button type="submit" class="btn">Add book</button>
            </form> <p>
        </c:forEach>
    </c:when>
    <c:otherwise>
            <h1>Nothing has been found</h1>
            </c:otherwise>
            </c:choose>


</body>
</html>
