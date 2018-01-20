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


<fmt:setBundle basename="resources.pagecontent" var="rb"/>
<html>
<head>
    <title>Catalog</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="../../css/catalog.css"/>">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
</head>
<body>
<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">Catalog</a></li>
    <c:set var="userType" value="${sessionScope.userType}"/>
    <c:choose>
        <c:when test="${userType == 2}">
            <li role="presentation"><a href="<c:url value="/jsp/requireAuth/librarianAccount.jsp"/> ">Profile</a></li>
        </c:when>
        <c:otherwise>
            <li role="presentation"><a href="<c:url value="/jsp/requireAuth/account.jsp"/> ">Profile</a></li>
        </c:otherwise>
    </c:choose>

</ul>
<form method="post" name="frm" action="/controller">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <div id="custom-search-input">
                    <div class="input-group col-md-12">
                        <input type="text" class="form-control input-lg" name="query" placeholder="Search by title or author"/>
                        <span class="input-group-btn">
                            <input type="hidden" name="command" value="searchbook"/>
                        <button class="btn btn-info btn-lg" type="submit">
                            <i class="glyphicon glyphicon-search"></i>
                        </button>
                    </span>
                    </div>
                </div>
            </div>
        </div>
    </div>

</form>
<form method="post" name="sortform" action="/controller">
    <select name="sort">
        <option value="name" ${sessionScope.name}>Sort by name</option>
        <option value="author" ${sessionScope.author}>Sort by author</option>
        <option value="rating" ${sessionScope.rating}>Sort by rating</option>
        <option value="year" ${sessionScope.year}>Sort by year</option>
        <option value="publisher" ${sessionScope.publisher}>Sort by publisher</option>
    </select>

    <input type="hidden" name="command" value="getcatalog"/>
    <button type="submit" id="submit" class="btn btn-warning btn-xs">&nbsp&nbsp&nbsp&nbspSort <span
            class="glyphicon glyphicon-sort"></span>&nbsp&nbsp&nbsp&nbsp
    </button>
</form>

<c:forEach var="book" items="${pageContext.request.session.getAttribute('catalog')}">
<c:out value="${book}"/>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="AddBookToUser"/>
    <input type="hidden" name="user_id" value="${sessionScope.userId}"/>
    <input type="hidden" name="book_id" value="${book.id}"/>
    <button type="submit" class="btn">Add book</button>
</form> <p>
    <c:set var="pages" value="${sessionScope.pages}"/>
    </c:forEach>
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
