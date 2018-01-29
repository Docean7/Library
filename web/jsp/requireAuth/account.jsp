<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Денис
  Date: 15.01.2018
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="curlocale" value="${sessionScope.locale}"/>
<fmt:setLocale value="${curlocale}" scope="session" />
<fmt:setBundle basename="pagecontent" />

<html>
<head>
    <title><fmt:message key="account.title"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="../../css/account.css"/> "/>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <li role="presentation"><a href="<c:url value="/jsp/requireAuth/catalog.jsp"/>"><fmt:message key="main.catalog"/></a></li>
            <li role="presentation" class="active"><a href="#"><fmt:message key="main.profile"/></a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><form action="/controller" method="post">
                <input type="hidden" name="command" value="Logout">
                <button type="submit" class="btn btn-danger navbar-btn"><fmt:message key="main.logout"/></button>
            </form></li>

        </ul>
    </div>
</nav>

<form action="/controller" method="post">

    <label>
        <select name="locale">
            <c:forEach items="${applicationScope.locales}" var="locale">
                <c:set var="selected" value="${locale.key == curlocale? 'selected' : '' }"/>
                <option value="${locale.key}" ${selected}>${locale.value} </option>
            </c:forEach>
        </select>
    </label>
    <input type="hidden" name="command" value="changeLocale">
    <input type="submit" value="<fmt:message key="account.changeLang"/>">
</form>

<h2><fmt:message key="account.hello"/>, ${sessionScope.firstname} ${sessionScope.lastname}</h2>
<br/>
<p><strong><fmt:message key="account.username"/>: ${sessionScope.login}</strong></p>
<p><strong><fmt:message key="account.telnumber"/>: ${sessionScope.telnumber}</strong></p>
<p><strong> <fmt:message key="account.email"/> : ${sessionScope.email}</strong></p>

<table class="table table-striped">
    <tr>
        <th><fmt:message key="table.title"/></th>
        <th><fmt:message key="table.author"/></th>
        <th><fmt:message key="table.genre"/></th>
        <th><fmt:message key="table.category"/></th>
        <th><fmt:message key="table.publisher"/></th>
        <th><fmt:message key="table.country"/></th>
        <th><fmt:message key="table.year"/></th>
        <th><fmt:message key="table.rating"/></th>
        <th><fmt:message key="table.expiration"/></th>
        <th><fmt:message key="table.cancelorder"/></th>
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
                         ${book.expiration} <c:if test="${fineAmount > 0}"><p class="s-error"><fmt:message key="order.fine"/>
                            ${fineAmount} <fmt:message key="order.hryvnas"/> </p></c:if>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="order.notdelivered"/>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                   <c:when test="${empty book.expiration}">
                       <form action="/controller" method="post">
                           <input type="hidden" name="command" value="DeleteOrder">
                           <input type="hidden" name="user_id" value="${sessionScope.userId}"/>
                           <input type="hidden" name="book_id" value="${book.id}"/>
                           <button class="btn btn-danger"><fmt:message key="table.cancelorder"/></button>
                       </form>
                   </c:when>
                    <c:otherwise>
                        <fmt:message key="order.delivered"/>
                    </c:otherwise>
                </c:choose>

            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
