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
    <li role="presentation"><a href="catalog.jsp">Catalog</a></li>
    <li role="presentation" class="active"><a href="#">Profile</a></li>

</ul>
Hello, ${sessionScope.firstname} ${sessionScope.lastname}
<br/>
Your login is ${sessionScope.login}
<br/>
Telephone number : ${sessionScope.telnumber}
Email : ${sessionScope.email}
<c:forEach var="order" items="${pageContext.request.session.getAttribute('orders')}">
   <p>${order} </p>
</c:forEach>
</body>
</html>