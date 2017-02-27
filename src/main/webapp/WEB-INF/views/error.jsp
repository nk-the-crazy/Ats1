<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8"
	    isErrorPage="true" %>

<%@page import="common.utils.system.SystemUtils,java.util.Locale" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- ************************************* -->

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title><spring:message code="label.page.error.title"/></title>

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
<!-- ***************************** -->
<c:set var="errorData" value="${requestScope.errorData}"/>
<c:set var="dateTimeFormatShort" value="${SystemUtils.getSettings('system.app.date.time.format.short')}"/>
<c:if test="${empty errorData}">
    <c:set var="errorData" value="${exception}"/>   
</c:if>

<!-- ***************************** -->

<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<!-- sidebar -->
			<jsp:include page="include/sidebar.jsp"><jsp:param name="page"
					value="error.vw" /></jsp:include>
			<!-- /sidebar -->

			<!-- top navigation -->
			<jsp:include page="include/header.jsp"><jsp:param name="page"
					value="error.vw" /></jsp:include>
			<!-- /top navigation -->

			<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<div class="row">
						<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="x_panel">
								<div class="x_title">
									<h2><span class="fa fa-exclamation-triangle danger"></span>
                                    <spring:message code="label.page.error.title"/></h2>
									<ul class="nav navbar-right panel_toolbox">
										<li><a class="collapse-link"><i
												class="fa fa-chevron-up"></i></a></li>
										
									</ul>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
                                    <div class="col-md-6">
                                    <table class="table table-bordered dataTable">
                                      <thead>
                                        <tr>
                                            <th colspan="2"></th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <tr>
                                          <th scope="row" class="col-md-3"><spring:message code="label.error.type" />:</th>
                                          <td class="col-md-5 danger"><c:out value="${errorData.toString()}"/></td>
                                        </tr>
                                        <tr>
                                          <th scope="row" class="col-md-3"><spring:message code="label.error.cause" />:</th>
                                          <td class="col-md-5"><c:out value="${errorData.getCause()}"/></td>
                                        </tr>
                                        <tr>
                                          <th scope="row" class="col-md-3"><spring:message code="label.error.desc" />:</th>
                                          <td class="col-md-5"><c:out value="${errorData.getMessage()}"/></td>
                                        </tr>
                                        <tr>
                                          <th scope="row" ></th>
                                          <td> 
                                            <a href="report_error.vw?" 
                                               role="button" class="btn btn-danger btn-xs">
                                                <i class="fa fa-phone">&nbsp;&nbsp;</i>
                                                <spring:message code="label.action.report.issue"/>
                                            </a>
                                          </td>
                                        </tr>
                                      </tbody>
                                    </table>
                                    </div>
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
