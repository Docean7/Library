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
    <link rel="stylesheet" href="<c:url value="../../../css/account.css"/> "/>
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

<fmt:message key="account.hello"/>, ${sessionScope.firstname} ${sessionScope.lastname}
<br/>
<br/>
<form action="/controller" method="post">
    <div class="form-inline">
        <input type="hidden" name="command" value="OneDayOrder"/>
        <input name="login" id="login" placeholder="<fmt:message key="librarian.username"/>" maxlength="25" class="form-control" type="text" required>
        <input name="book_id" placeholder="<fmt:message key="librarian.bookid"/>" type="number" maxlength="8" class="form-control" required>
        <button type="submit" class="btn"><fmt:message key="librarian.dayorder"/> </button>
    </div>

</form>

<br/>
<table class="table table-striped">
    <tr>
        <th><fmt:message key="librarian.bookid"/></th>
        <th><fmt:message key="librarian.bookTitle"/></th>
        <th><fmt:message key="librarian.userId"/></th>
        <th><fmt:message key="librarian.username"/></th>
        <th><fmt:message key="librarian.expiration"/></th>
        <th><fmt:message key="librarian.markDelivered"/></th>
        <th><fmt:message key="librarian.markReturned"/></th>
    </tr>
    <c:forEach var="order" items="${sessionScope.get('orders')}">
        <tr>
            <td>${order.bookID}</td>
            <td>${order.bookTitle}</td>
            <td>${order.userID}</td>
            <td>${order.username}</td>
            <td>
                <c:choose>
                    <c:when test="${order.delivered}">
                        ${order.expirationDate}
                    </c:when>
                    <c:otherwise><fmt:message key="order.notdelivered"/></c:otherwise>
                </c:choose>
            </td>

            <td>
                <form action="/controller" method="post">
                    <input type="hidden" name="command" value="BookDelivered"/>
                    <input type="hidden" name="order_id" value="${order.id}"/>
                    <button type="submit" class="btn <c:if test="${order.delivered == true}">disabled</c:if>"><fmt:message key="librarian.delivered"/> </button>
                </form>
            </td>
            <td>
                <form action="/controller" method="post">
                    <input type="hidden" name="command" value="DeleteOrder"/>
                    <input type="hidden" name="user_id" value="${order.userID}"/>
                    <input type="hidden" name="book_id" value="${order.bookID}"/>
                    <button type="submit" class="btn <c:if test="${order.delivered == false}">disabled</c:if>"> <fmt:message key="librarian.returned"/> </button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>