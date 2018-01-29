<%--
  Created by IntelliJ IDEA.
  User: Денис
  Date: 21.01.2018
  Time: 21:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="curlocale" value="${sessionScope.locale}"/>
<fmt:setLocale value="${curlocale}" scope="session" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
    <title><fmt:message key="admin.account"/>  </title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
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

<h2><fmt:message key="admin.addbookToCatalog"/></h2>
<%--сделать валидацию форм и дизайн--%>
<form action="/controller" method="post">

    <input type="hidden" name="command" value="addBook">

    <div class="form-group">
        <input name="title" placeholder="<fmt:message key="table.title"/>" class="form-control" required>
    </div>
    <div class="form-group">
        <input name="author" placeholder="<fmt:message key="table.author"/>" class="form-control" required>
    </div>
    <div class="form-group">
        <input name="genre" placeholder="<fmt:message key="table.genre"/>" class="form-control" required>
    </div>
    <div class="form-group">
        <input name="category" placeholder="<fmt:message key="table.category"/>" class="form-control" required>
    </div>
    <div class="form-group">
        <input name="publisher" placeholder="<fmt:message key="table.publisher"/>" class="form-control" required>
    </div>

    <div class="form-group">
        <input name="country" placeholder="<fmt:message key="table.country"/>" class="form-control" required>
    </div>
    <div class="form-group">
        <input name="year" placeholder="<fmt:message key="table.year"/>" class="form-control" required>
    </div>
    <div class="form-group">
        <input name="rating" placeholder="<fmt:message key="table.rating"/>" class="form-control" required>
    </div>
    <div class="form-group">
        <input name="quantity" placeholder="<fmt:message key="table.quantity"/>" class="form-control" required>
    </div>

    <button type="submit" class="btn btn-default"><fmt:message key="admin.addbook"/></button>
</form>
<h2><fmt:message key="admin.deletebookByID"/></h2>
<form action="/controller" method="post">
    <input type="hidden" name="command"  value="deleteBook">
    <div class="form-group">
        <input name="book_id" placeholder="<fmt:message key="librarian.bookid"/>" class="form-control" required>
    </div>

    <button type="submit" class="btn btn-default"><fmt:message key="admin.deletebook"/></button>
</form>

<h2><fmt:message key="admin.editbook"/></h2>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="FindBook">
    <div class="form-group">
        <input name="book_id" placeholder="<fmt:message key="librarian.bookid"/>" class="form-control" required>
    </div>
    <button type="submit" class="btn btn-default"><fmt:message key="search.find"/></button>
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
<h2><fmt:message key="admin.addlibrarian"/></h2>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="ChangeType">
    <input type="hidden" name="type" value="2">
    <div class="form-group">
        <input name="username" class="form-control" placeholder="<fmt:message key="librarian.username"/>" required>
    </div>
   <button type="submit" class="btn btn-default"><fmt:message key="admin.makelibrarian"/></button>
</form>
<h2><fmt:message key="admin.deletelibrarian"/></h2>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="ChangeType">
    <input type="hidden" name="type" value="0">
    <div class="form-group">
        <input name="username" class="form-control" placeholder="<fmt:message key="librarian.username"/>" required>
    </div>
    <button type="submit" class="btn btn-default"><fmt:message key="admin.deletelibrarian"/></button>
</form>
<h2><fmt:message key="admin.banuser"/></h2>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="ChangeType">
    <input type="hidden" name="type" value="4">
    <div class="form-group">
        <input name="username" class="form-control" placeholder="<fmt:message key="librarian.username"/>" required>
    </div>
    <button type="submit" class="btn btn-default"><fmt:message key="admin.banuser"/></button>
</form>

<h2><fmt:message key="admin.forgiveuser"/></h2>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="ChangeType">
    <input type="hidden" name="type" value="0">
    <div class="form-group">
        <input name="username" class="form-control" placeholder="<fmt:message key="librarian.username"/>" required>
    </div>
    <button type="submit" class="btn btn-default"><fmt:message key="admin.forgiveuser"/></button>
</form>

</body>
</html>
