<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Денис
  Date: 18.01.2018
  Time: 19:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="<c:url value="../css/register.css"/> "/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">

    <form role="form" class="well form-horizontal" id="contact-form" name="registration">
        <fieldset>
            <!-- Form Name -->
            <legend><h2><b>Registration Form</b></h2></legend>
            <br>

            <!-- Text input-->

            <div class="form-group">
                <label class="col-md-4 control-label">First Name</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input name="first_name" id="name" placeholder="First Name" class="form-control" value="${sessionScope.firstname}" type="text"  minlength="2" maxlength="25" required>
                    </div>
                </div>
            </div>

            <!-- Text input-->

            <div class="form-group">
                <label class="col-md-4 control-label">Last Name</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input name="last_name" minlength="3" maxlength="25" id="last" placeholder="Last Name" class="form-control" value="${sessionScope.lastname}" type="text" required>
                    </div>
                </div>
            </div>

            <!-- Text input-->

            <div class="form-group">
                <label class="col-md-4 control-label">Username</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input name="user_name" id="login" placeholder="Username" class="form-control" type="text"
                               pattern="^[_A-z0-9]{1,}$" maxlength="25"  value="${sessionScope.checklogin}" required>
                    </div>
                    <div class="s-error">${sessionScope.errorLogin}</div>
                    <c:remove var="errorLogin" scope="session"/>
                </div>
            </div>

            <!-- Text input-->

            <div class="form-group">
                <label class="col-md-4 control-label">Password</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input name="user_password" id="password" placeholder="Password" class="form-control"
                               type="password" pattern="^[_A-z0-9]{1,}$" maxlength="15" minlength="4" required>
                    </div>
                </div>
            </div>

            <!-- Text input-->

            <div class="form-group">
                <label class="col-md-4 control-label">Confirm Password</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input name="confirm_password" id="confirm_password" placeholder="Confirm Password"
                               class="form-control" type="password" pattern="^[_A-z0-9]{1,}$" maxlength="15" minlength="4" required>
                    </div>
                </div>
            </div>


            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label">E-Mail</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                        <input name="email" id="email" maxlength="32" placeholder="E-Mail Address" class="form-control" value="${sessionScope.checkemail}" type="email"
                            required>
                    </div>
                    <div class="s-error"> ${sessionScope.errorEmail}</div>
                    <c:remove var="errorEmail" scope="session"/>
                </div>
            </div>


            <!-- Text input-->

            <div class="form-group">
                <label class="col-md-4 control-label">Contact No.</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span>
                        <input name="contact_no" maxlength="12" id="tel" placeholder="Your number" class="form-control" pattern="^[0-9]{7,}$" value="${sessionScope.contact}" type="text" required>
                    </div>
                </div>
            </div>

            <!-- Select Basic -->

            <!-- Success message -->
            <div class="alert alert-success" role="alert" id="success_message">Success <i
                    class="glyphicon glyphicon-thumbs-up"></i> Success!.
            </div>

            <!-- Button -->

            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4"><br>
                    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                    <button type="submit" id="submit" class="btn btn-warning">
                        &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspSUBMIT <span class="glyphicon glyphicon-send"></span>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                    </button>
                </div>
            </div>
            <a href="/jsp/login.jsp">Login</a>
        </fieldset>
    </form>
</div>
<script src="../js/registration.js"></script>
</body>
</html>
