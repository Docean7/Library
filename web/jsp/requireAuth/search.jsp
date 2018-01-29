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
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="curlocale" value="${sessionScope.locale}"/>
<fmt:setLocale value="${curlocale}" scope="session" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
    <title><fmt:message key="search.result"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <li role="presentation"><a href="<c:url value="/jsp/requireAuth/catalog.jsp"/> "><fmt:message key="main.catalog"/></a></li>
            <c:set var="userType" value="${sessionScope.userType}"/>
            <c:choose>
                <c:when test="${userType == 2}">
                    <li role="presentation"><a href="<c:url value="/jsp/requireAuth/librarian/librarianAccount.jsp"/> "><fmt:message key="main.profile"/></a>
                    </li>
                </c:when>
                <c:when test="${userType == 3}">
                    <li role="presentation"><a
                            href="<c:url value="/jsp/requireAuth/admin/adminAccount.jsp"/> "><fmt:message key="main.profile"/></a></li>
                </c:when>
                <c:otherwise>
                    <li role="presentation"><a href="<c:url value="/jsp/requireAuth/account.jsp"/> "><fmt:message key="main.profile"/></a></li>
                </c:otherwise>
            </c:choose>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><form action="/controller" method="post">
                <input type="hidden" name="command" value="Logout">
                <button type="submit" class="btn btn-danger navbar-btn"><fmt:message key="main.logout"/></button>
            </form></li>
        </ul>
    </div>

</nav>
<c:set var="books" value="${sessionScope.get('foundBooks')}"/>
<c:choose>
    <c:when test="${not empty books }">
        <h1><fmt:message key="search.found"/>:</h1><br/>
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
                <th><fmt:message key="table.order"/></th>
            </tr>
            <c:forEach var="book" items="${books}">
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
                        <form action="/controller" method="post">
                            <input type="hidden" name="command" value="AddBookToUser"/>
                            <input type="hidden" name="user_id" value="${sessionScope.userId}"/>
                            <input type="hidden" name="book_id" value="${book.id}"/>
                            <button type="submit"
                                    class="btn <c:if test="${ctg:checkPresence(bookList, book.id)}">disabled</c:if>"><fmt:message key="table.orderbook"/>
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
            <h1><fmt:message key="search.nothing"/></h1>
            </c:otherwise>
            </c:choose>


</body>
</html>
