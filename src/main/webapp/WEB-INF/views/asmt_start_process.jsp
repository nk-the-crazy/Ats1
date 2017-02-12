<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8"
	    errorPage="error.jsp"%>

<%@page import="common.utils.StringUtils, common.utils.system.SystemUtils,java.util.Locale, model.assessment.*" %>

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

<title><spring:message code="label.page.assessment_start.title"/></title>

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
<c:set var="process" value="${sessionScope.sessionData.assessmentProcess}"/>
<c:set var="currentTask" value="${requestScope.currentTask}"/>
<c:set var="dateFormatShort" value="${SystemUtils.getSettings('system.app.date.format.short')}"/>
<!-- ***************************** -->

<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<!-- sidebar -->
			<jsp:include page="include/sidebar.jsp"><jsp:param name="page"
					value="asmt_start_process.vw" /></jsp:include>
			<!-- /sidebar -->

			<!-- top navigation -->
			<jsp:include page="include/header.jsp"><jsp:param name="page"
					value="asmt_start_process.vw" /></jsp:include>
			<!-- /top navigation -->

			<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<div class="row">
						<div class="col-md-10 col-sm-10 col-xs-10">
							<div class="x_panel">
								<div class="x_title">
									<h2><spring:message code="label.page.assessment_start.title"/>&nbsp;&nbsp;-&nbsp;&nbsp;${process.name}</h2>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
                                    <div class="col-md-12">
                                    <table class="table table-bordered dataTable">
                                      <thead>
                                        <tr>
                                            <th colspan="4"><i class="fa fa-cube"></i>&nbsp;&nbsp;
                                            <spring:message code="label.asmt.task" /></th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <!--  
                                        <tr>
                                          <th class="col-md-3" scope="row" class="col-md-3"><spring:message code="label.asmt.task.item.name" />:</th>
                                          <td class="col-md-9" colspan="3"><c:out value="${process.name}"/></td>
                                        </tr>
                                        --> 
                                        <tr>
                                          <th class="col-md-2" scope="row"><spring:message code="label.date.time" />:</th>
                                          <td class="col-md-2"><div class="countdown"><c:out value="${StringUtils.minutesToDetails(process.time)}"/></div>
                                          </td>
                                          <th class="col-md-2" scope="row">
                                            <spring:message code="label.date.time.remaining" />:</th>
                                          <td class="col-md-6"><div class="countdown" id="timerCountDown"></div></td>
                                        </tr>
                                        <thead>
                                        <tr>
                                            <th colspan="4"></th>
                                        </tr>
                                        </thead> 
                                        <tr>
                                          <th class="col-md-2" colspan="1"><spring:message code="label.asmt.task.item.content" /></th>
                                          <td class="col-md-10" colspan="3"></td>
                                        </tr>
                                        <tr>
                                          <th class="col-md-2" colspan="1"><spring:message code="label.asmt.task.item.options" /></th>
                                          <td class="col-md-10" colspan="3"></td>
                                        </tr>
                                      
                                        <tr>
                                          <th scope="row" ></th>
                                          <td><a href="assessment_start.vw?" role="button" class="btn btn-primary btn-xs">
                                                <i class="fa fa-share"></i>&nbsp;
                                                <spring:message code="label.action.continue"/>
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
					value="asmt_start_process.vw" /></jsp:include>
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
    
    <!-- Timer -->
    <script type="text/javascript" src="resources/lib/jquery.countdown-2.2.0/jquery.countdown.js"></script>
    <script type="text/javascript">

    function getCountDownTime() 
    {
      return new Date(new Date().valueOf() + 1 * 1 * ${process.time} * 60 * 1000);
    }

    var $clock = $('#timerCountDown');

    $clock.countdown(getCountDownTime(), function(event) 
    {
      $(this).html(event.strftime('%H:%M:%S'));
    });

    </script>
    <!-- /Timer -->
    
</body>
</html>
