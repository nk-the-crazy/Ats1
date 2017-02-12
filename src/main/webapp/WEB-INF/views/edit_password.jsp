<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8"
	    errorPage="error.jsp"%>

<%@page import="common.utils.system.SystemUtils,java.util.Locale" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- ************************************* -->

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title><spring:message code="label.page.edit_password.title"/></title>

<!-- Bootstrap -->
<link href="resources/lib/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Font Awesome -->
<link href="resources/lib/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">

<!-- NProgress -->
<link href="resources/lib/nprogress/nprogress.css" rel="stylesheet">

<!-- Custom Theme Style -->
<link href="resources/css/custom.css" rel="stylesheet">
</head>

<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<!-- sidebar -->
			<jsp:include page="include/sidebar.jsp"><jsp:param name="page"
					value="edit_password.vw" /></jsp:include>
			<!-- /sidebar -->

			<!-- top navigation -->
			<jsp:include page="include/header.jsp"><jsp:param name="page"
					value="edit_password.vw" /></jsp:include>
			<!-- /top navigation -->

			<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<div class="row">
						<div class="col-md-5 col-sm-5 col-xs-5">
							<div class="x_panel">
								<div class="x_title">
									<h2><spring:message code="label.page.edit_password.title"/></h2>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
                                <form>
                                    <table class="table table-bordered dataTable">
                                      <tbody>
                                      
                                        <!-- ---------------------- -->
                                        <c:choose>
                                            <c:when test="${requestScope.errorMessage != null}">
                                                <div class="alert alert-danger alert-dismissible fade in" role="alert">
                                                   <spring:message code="${requestScope.errorMessage}"/>
                                                </div>
                                            </c:when>
                                            <c:when test="${requestScope.successMessage != null}">
                                                <div class="alert alert-danger alert-dismissible fade in" role="alert">
                                                   <spring:message code="${requestScope.successMessage}"/>
                                                </div>
                                            </c:when>
                                        </c:choose>
                                        <!-- ---------------------- -->
                                        <tr>
                                          <th scope="row" class="col-md-1">
                                          <label class="control-label" for="current-password">
                                            <spring:message code="label.password.current" />:&nbsp;</label></th>
                                          <td class="col-md-2">
                                            <input type="password" name="currentPassword"
                                                        id="current-password" class="form-control input-sm">
                                          </td>
                                        </tr>
                                        <tr>
                                          <th scope="row" class="col-md-1">
                                          <label class="control-label" for="new-password">
                                            <spring:message code="label.password.new" />:&nbsp;</label></th>
                                          <td class="col-md-2">
                                            <input type="password" name="newPassword"
                                                        id="new-password" class="form-control input-sm">
                                          </td>
                                        </tr>
                                        <tr>
                                          <th scope="row" class="col-md-1">
                                          <label class="control-label" for="confirm-password">
                                            <spring:message code="label.password.confirm" />:&nbsp;</label></th>
                                          <td class="col-md-2">
                                            <input type="password" name="confirmPassword"
                                                        id="confirm-password" class="form-control input-sm">
                                          </td>
                                        </tr>
                                        <tr>
                                            <th scope="row" class="col-md-1"></th>
                                            <td>
                                            <button type="submit" class="btn btn-primary btn-xs">
                                                <i class="fa fa-check">&nbsp;&nbsp;</i><spring:message code="label.action.submit" />
                                            </button>
                                            </td>
                                        </tr>
                                      </tbody>
                                    </table>
                                </form>
                                </div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /page content -->

			<!-- footer content -->
			<jsp:include page="include/footer.jsp"><jsp:param name="page"
					value="main" /></jsp:include>
			<!-- /footer content -->
		</div>
	</div>

	<!-- jQuery -->
	<script src="resources/lib/jquery/js/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script src="resources/lib/bootstrap/js/bootstrap.min.js"></script>
	
	<!-- FastClick -->
    <script src="resources/lib/fastclick/js/fastclick.js"></script>
    
    <!-- NProgress -->
    <script src="resources/lib/nprogress/nprogress.js"></script>
	
	<!-- Custom Theme Scripts -->
	<script src="resources/js/custom.min.js"></script>
</body>
</html>
