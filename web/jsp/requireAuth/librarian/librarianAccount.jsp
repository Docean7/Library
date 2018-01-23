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
    <link rel="stylesheet" href="<c:url value="../../../css/account.css"/> "/>
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


Hello, ${sessionScope.firstname} ${sessionScope.lastname}
<br/>
<br/>
<form action="/controller" method="post">
    <div class="form-inline">
        <input type="hidden" name="command" value="OneDayOrder"/>
        <input name="login" id="login" placeholder="User's login" maxlength="25" class="form-control" type="text" required>
        <input name="book_id" placeholder="Book id" type="number" maxlength="8" class="form-control" required>
        <button type="submit" class="btn">Add 1-day order </button>
    </div>

</form>

<br/>
<table class="table table-striped">
    <tr>
        <th>Book id</th>
        <th>Book Title</th>
        <th>User id</th>
        <th>Username</th>
        <th>Delivery/expiration status</th>
        <th>Expiration Date</th>
        <th>Mark delivered</th>
        <th>Mark returned</th>
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
                    <c:otherwise>Not delivered</c:otherwise>
                </c:choose>
            </td>
            <td></td>

            <td>
                <form action="/controller" method="post">
                    <input type="hidden" name="command" value="BookDelivered"/>
                    <input type="hidden" name="order_id" value="${order.id}"/>
                    <button type="submit" class="btn <c:if test="${order.delivered == true}">disabled</c:if>"> Book Delivered </button>
                </form>
            </td>
            <td>
                <form action="/controller" method="post">
                    <input type="hidden" name="command" value="DeleteOrder"/>
                    <input type="hidden" name="user_id" value="${order.userID}"/>
                    <input type="hidden" name="book_id" value="${order.bookID}"/>
                    <button type="submit" class="btn <c:if test="${order.delivered == false}">disabled</c:if>"> Book Returned </button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>