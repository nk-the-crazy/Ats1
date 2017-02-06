<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8"
	    errorPage="error.jsp"%>
	
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- *********************************** -->

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title><spring:message code="label.page.login.title"/></title>

<!-- Bootstrap -->
<link href="resources/lib/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Font Awesome -->
<link href="resources/lib/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">

<!-- Custom Theme Style -->
<link href="resources/css/custom.css" rel="stylesheet">
</head>
<!-- PNotify 
<link href="resources/lib/pnotify/dist/pnotify.css" rel="stylesheet">
<link href="resources/lib/pnotify/dist/pnotify.buttons.css" rel="stylesheet">
<link href="resources/lib/pnotify/dist/pnotify.nonblock.css" rel="stylesheet">
-->
<body class="login">
    <div class="login_wrapper">
             <div class="login_wrapper_title">
             <div class="row">
                <span class="col-md-2"><img src="resources/images/art-monitor2.png" class="login_wrapper_title_logo"></span>
                <span class="col-md-10 login_wrapper_title_text">
                    &nbsp;Электронная Системa Тестирования<br>
                    <span class="system_title">ATS1</span></span>
             </div>
             </div>
        	 <div class="animate form login_form">
                <section class="login_content">
                    <div class="login_content_title"><spring:message code="label.page.login.title"/></div>
                    <form id="login-form" action="login.do" method="POST" data-parsley-validate class="form-horizontal form-label-left">
                      <!-- ---------------------- -->
                      <c:if test="${requestScope.errorMessage != null}">
                          <div class="alert alert-danger alert-dismissible fade in" role="alert">
                               <spring:message code="${requestScope.errorMessage}"/>
                          </div>
                      </c:if>
                      <!-- ---------------------- -->
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="login-name"><spring:message code="label.user.login"/>:<span class="required"></span>
                        </label>
                        <div class="col-md-8 col-sm-8 col-xs-12">
                          <input value="admin1" type="text" id="login-name" name="userName" required="required" class="form-control col-md-7 col-xs-12">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="password"><spring:message code="label.user.password"/>:<span class="required"></span>
                        </label>
                        <div class="col-md-8 col-sm-8 col-xs-12">
                          <input value="secret" type="password" id="password" name="password" required="required" class="form-control col-md-7 col-xs-12">
                        </div>
                      </div>
                      <div class="form-group">
                        <div class="col-md-10 col-sm-10 col-xs-12 col-md-offset-3">
                          <button type="submit" class="btn btn-primary btn-sm">
                            <i class="fa fa-sign-in"></i>&nbsp;&nbsp;
                            <spring:message code="label.action.login"/></button>
                        </div>
                      </div>  
                     </form>                           
				</section>
            </div>
	</div>
<!-- PNotify 
<script src="reseources/lib/pnotify/dist/pnotify.js"></script>
<script src="reseources/lib/pnotify/dist/pnotify.buttons.js"></script>
<script src="reseources/lib/pnotify/dist/pnotify.nonblock.js"></script>
-->
</body>
</html>
