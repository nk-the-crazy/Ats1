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

<body class="login">
    <div class="login_wrapper">
             <div class="login_wrapper_title">
             <div class="row">
                <span class="col-md-2"><img src="resources/images/art-monitor2.png" class="login_wrapper_title_logo"></span>
                <span class="col-md-10 login_wrapper_title_text">
                    &nbsp;Электронная Системa Тестирования<br>
                    <span class="system_title">ATS1</span></span>
             </div>
                 <div class="row">
             </div>
             </div>
        	 <div class="animate form login_form">
                <section class="login_content">
                    <div class="login_content_title"><spring:message code="label.page.login.title"/></div>
                    <form id="login-form" action="login.do" method="POST" autocomplete="off" data-parsley-validate class="form-horizontal form-label-left">
                      <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
                      <input type="hidden"  name="userName"   value="token"/>
                                         <!-- ---------------------- -->
                      <c:if test="${param.errorMessage != null}">
                          <div class="alert alert-danger alert-dismissible fade in" role="alert">
                               <spring:message code="${param.errorMessage}"/>
                          </div>
                      </c:if>
                      <!-- ---------------------- -->
                      
                      <div class="form-group">
                        <label class="control-label col-md-2" for="token"><spring:message code="label.user.token"/>:
                            <span class="required"></span>
                        </label>
                        <div class="col-md-6">
                          <input type="text" id="password" name="password" required="required" class="form-control" autocomplete="off">
                        </div>
                        <div class="col-md-2">
                          <button type="submit" class="btn btn-primary btn-sm">
                                <i class="fa fa-sign-in"></i>&nbsp;&nbsp;
                                <spring:message code="label.action.login"/>
                          </button>
                        </div>
                      </div>
                      <br>
                      <hr/>
                        <div class="col-md-10 col-sm-10 col-xs-12 col-md-offset-8">
                          <a href="login_system.vw" class="float:right;col-md-offset-3">
                            <i class="fa fa-user"></i>&nbsp;&nbsp;<spring:message code="label.action.login.admin"/>
                          </a>
                        </div>
                     </form>                           
				</section>
            </div>
            
	</div>
    
</body>
</html>
