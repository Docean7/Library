<%--
  Created by IntelliJ IDEA.
  User: Денис
  Date: 06.01.2018
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:set var="curlocale" value="${sessionScope.locale}"/>
<fmt:setLocale value="${curlocale}" scope="session" />
<fmt:setBundle basename="pagecontent" />

<c:set var="bookList" value="${sessionScope.get('bookList')}"/>
<c:set var="user_id" value="${sessionScope.get('userId')}"/>
<html>
<head>
    <title><fmt:message key="main.catalog"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/catalog.css"/>">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <li role="presentation" class="active"><a href="#"><fmt:message key="main.catalog"/></a></li>
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
<form method="post" name="frm" action="/controller">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <div id="custom-search-input">
                    <div class="input-group col-md-12">
                        <input type="text" class="form-control input-lg" name="query"
                               placeholder="<fmt:message key="search.placeholder"/>" required/>
                        <span class="input-group-btn">
                            <input type="hidden" name="command" value="searchbook"/>
                        <button class="btn btn-info btn-lg" type="submit">
                            <i class="glyphicon glyphicon-search"></i>
                        </button>
                    </span>
                    </div>
                </div>
                <div class="s-error">${requestScope.searchError}</div>
            </div>
        </div>
    </div>

</form>
<form method="post" name="sortform" action="/controller">
    <select name="sort">
        <option value="name" ${sessionScope.name}><fmt:message key="sort.name"/></option>
        <option value="author" ${sessionScope.author}><fmt:message key="sort.author"/></option>
        <option value="rating" ${sessionScope.rating}><fmt:message key="sort.rating"/></option>
        <option value="year" ${sessionScope.year}><fmt:message key="sort.year"/></option>
        <option value="publisher" ${sessionScope.publisher}><fmt:message key="sort.pubisher"/></option>
    </select>

    <input type="hidden" name="command" value="getcatalog"/>
    <button type="submit" id="submit" class="btn btn-warning btn-xs">&nbsp&nbsp&nbsp&nbspSort <span
            class="glyphicon glyphicon-sort"></span>&nbsp&nbsp&nbsp&nbsp
    </button>
</form>

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
    <c:forEach var="book" items="${sessionScope.get('catalog')}">
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


<c:set var="pages" value="${sessionScope.pages}"/>

<nav aria-label="Page navigation">
    <ul class="pagination">
        <%--<li>--%>
        <%--<a href="#" aria-label="Previous">--%>
        <%--<span aria-hidden="true">&laquo;</span>--%>
        <%--</a>--%>
        <%--</li>--%>
        <c:forEach var="i" begin="1" end="${pages}">
            <li><a href="/controller?command=getcatalog&page=${i}">${i}</a></li>
        </c:forEach>


        <%--<li>--%>
        <%--<a href="#" aria-label="Next">--%>
        <%--<span aria-hidden="true">&raquo;</span>--%>
        <%--</a>--%>
        <%--</li>--%>
    </ul>
</nav>
</body>
</html>
