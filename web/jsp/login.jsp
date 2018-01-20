<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Денис
  Date: 06.01.2018
  Time: 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">


    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">


    <link rel="stylesheet" href="<c:url value="../css/login.css"/> "/>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">

    <form role="form" class="well form-horizontal" action="/controller" method="POST"
          id="contact-form">
        <input type="hidden" name="command" value="login" />

        <fieldset>
            <!-- Form Name -->
            <legend><h2><b>Login Form</b></h2></legend>
            <br>

            <!-- Text input-->

            <div class="form-group">
                <label class="col-md-4 control-label">Login</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input name="login" id="login" placeholder="Login" maxlength="25" class="form-control" type="text" value="${requestScope.cashedLogin}"
                               required>
                    </div>
                    <div class="s-error">${requestScope.loginCheck}</div>
                    <c:remove var="loginCheck" scope="request"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-4 control-label">Password</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input name="password" id="password" placeholder="Password" maxlength="15" class="form-control" type="password"
                               required>
                    </div>
                    <div class="s-error">${requestScope.passwordCheck}</div>
                    <c:remove var="passwordCheck" scope="request"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4"><br>
                    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<button type="submit" id="submit" class="btn btn-warning" >&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspSUBMIT <span class="glyphicon glyphicon-send"></span>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</button>
                </div>
            </div>
        </fieldset>
        <a href="/jsp/register.jsp">Register</a>
    </form>
</div>
</body>
</html>
