<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8"
	    errorPage="error.jsp"%>

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

<title><spring:message code="label.page.assessment_init.title"/></title>

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
<c:set var="sessionData" value="${sessionScope.sessionData}"/>
<c:set var="dateTimeFormatShort" value="${SystemUtils.getSettings('system.app.date.time.format.short')}"/>
<!-- ***************************** -->

<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<!-- sidebar -->
			<jsp:include page="include/sidebar.jsp"><jsp:param name="page"
					value="main" /></jsp:include>
			<!-- /sidebar -->

			<!-- top navigation -->
			<jsp:include page="include/header.jsp"><jsp:param name="page"
					value="main" /></jsp:include>
			<!-- /top navigation -->

			<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<div class="row">
						<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="x_panel">
								<div class="x_title">
									<h2><spring:message code="label.page.assessment_init.title"/></h2>
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
                                            <th colspan="2"><i class="fa fa-user"></i>&nbsp;&nbsp;
                                            <spring:message code="label.account" /></th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <tr>
                                          <th scope="row" class="col-md-3"><spring:message code="label.assessment.name" />:</th>
                                          <td class="col-md-5"><c:out value="${sessionData.user.userName}"/></td>
                                        </tr>
                                        <tr>
                                          <th scope="row"><spring:message code="label.assessment.type" />:</th>
                                          <td><c:out value="${sessionData.user.email}"/></td>
                                        </tr>
                                        <tr>
                                          <th scope="row"><spring:message code="label.date.time" />:</th>
                                          <td><c:out value="${sessionData.user.email}"/></td>
                                        </tr>
                                        <tr>
                                          <th scope="row"><spring:message code="label.assessment.maxgrade" />:</th>
                                          <td><c:out value="${sessionData.user.email}"/></td>
                                        </tr>
                                        <tr>
                                          <th scope="row" ><spring:message code="label.date.start" />-<spring:message code="label.date.end" /></th>
                                          <td><fmt:formatDate pattern="${dateTimeFormatShort }" value="${sessionData.user.lastLogin}" />-
                                          <fmt:formatDate pattern="${dateTimeFormatShort }" value="${sessionData.user.lastLogin}" />
                                          </td>
                                        </tr>
                                        <tr>
                                          <th scope="row" ></th>
                                          <td><button type="button" class="btn-td btn btn-primary btn-xs">
                                                        <i class="fa fa-clock-o"></i>&nbsp;
                                                            <spring:message code="label.assessment.start"/>
                                               </button> 
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
