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
<ul class="nav nav-tabs">
    <li role="presentation"><a href="<c:url value="/jsp/requireAuth/catalog.jsp"/>">Catalog</a></li>
    <li role="presentation" class="active"><a href="#">Profile</a></li>

</ul>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="Logout">
    <button type="submit" class="btn btn-danger">Logout</button>
</form>


Hello, ${sessionScope.firstname} ${sessionScope.lastname}
<br/>
Your login is ${sessionScope.login}
<br/>
Telephone number : ${sessionScope.telnumber}
Email : ${sessionScope.email}
<c:forEach var="book" items="${sessionScope.get('bookList')}">
    <h3>${book.id}</h3>
    <c:out value="${book}"/>
    <c:if test="${not empty book.expiration}">
        <c:set var="fineAmount" value="${ctg:calculateForfeit(book.expiration)}"/>
        <c:out value="expires on ${book.expiration}"/> <c:if test="${fineAmount > 0}"><p class="s-error">Fine
        of ${fineAmount} hryvnas</p></c:if>
    </c:if>

</c:forEach>
</body>
</html>
