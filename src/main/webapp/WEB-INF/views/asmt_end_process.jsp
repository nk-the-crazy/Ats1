<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8"
	    errorPage="error.jsp"%>

<%@page import="common.utils.StringUtils, common.utils.system.SystemUtils,java.util.Locale" %>

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

<title><spring:message code="label.page.assessment_end.title"/></title>

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
<c:set var="process" value="${requestScope.assessmentProcess}"/>
<c:set var="tasks" value="${process.tasks}"/>
<c:set var="dateTimeFormatShort" value="${SystemUtils.getSettings('system.app.date.time.format.short')}"/>
<!-- ***************************** -->

<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<!-- sidebar -->
			<jsp:include page="include/sidebar.jsp"><jsp:param name="page"
					value="assessment_init.vw" /></jsp:include>
			<!-- /sidebar -->

			<!-- top navigation -->
			<jsp:include page="include/header.jsp"><jsp:param name="page"
					value="assessment_init.vw" /></jsp:include>
			<!-- /top navigation -->

			<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<div class="row">
						<div class="col-md-8 col-sm-8 col-xs-8">
							<div class="x_panel">
								<div class="x_title">
									<h2><spring:message code="label.page.assessment_end.title"/></h2>
                                    
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
                                    <div class="col-md-12">
                                    <table class="table table-bordered dataTable">
                                      <thead>
                                        <tr>
                                            <th colspan="4"><i class="fa fa-graduation-cap"></i>&nbsp;&nbsp;
                                            <spring:message code="label.assessment" /></th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <tr>
                                          <th scope="row" class="col-md-2"><spring:message code="label.assessment.name" />:</th>
                                          <td colspan="3"><c:out value="${process.assessment.name}"/></td>
                                        </tr>
                                        <tr>
                                          <th scope="row" class="col-md-2 success"><spring:message code="label.date.time" />:</th>
                                          <td class="col-md-4"><c:out value="${process.assessment.time}"/>&nbsp;&nbsp;
                                            <spring:message code="label.date.time.minutes" />
                                          </td>
                                          <th scope="row" class="col-md-2 success"><spring:message code="label.date.time.elapsed" />:</th>
                                          <td class="col-md-4"><c:out value="${StringUtils.millisToDetails(process.timeElapsed)}"/>&nbsp;&nbsp;
                                            <spring:message code="label.date.time.minutes" />
                                          </td>
                                        </tr>
                                        <tr>
                                          <th scope="row"><spring:message code="label.asmt.task.count" />:</th>
                                          <td><c:out value="${tasks.size()}"/></td>
                                          <th scope="row"><spring:message code="label.asmt.task.respond" />:</th>
                                          <td><c:out value="${tasks.size()}"/></td>
                                        </tr>
                                        <tr>
                                          <th scope="row" ><spring:message code="label.date.start" /></th>
                                          <td><fmt:formatDate pattern="${dateTimeFormatShort }" value="${process.startDate}" /></td>
                                          <th scope="row" ><spring:message code="label.date.end" /></th>
                                          <td><fmt:formatDate pattern="${dateTimeFormatShort }" value="${process.endDate}" /></td>
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
					value="assessment_init.vw" /></jsp:include>
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
