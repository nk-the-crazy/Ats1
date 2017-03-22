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

<title><spring:message code="label.page.asmt_process_init.title"/></title>

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
<c:set var="process" value="${sessionScope.activeProcess}"/>
<c:set var="taskCount" value="${process.taskIds.size()}"/>
<c:set var="dateFormatShort" value="${SystemUtils.getSettings('system.app.date.format.short')}"/>
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
						<div class="col-md-6 col-sm-6 col-xs-6">
							<div class="x_panel">
								<div class="x_title">
									<h2><spring:message code="label.page.asmt_process_init.title"/></h2>
                                    
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
                                   <!-- ---------------------- -->
                                  <c:if test="${requestScope.errorMessage != null}">
                                      <div class="alert alert-danger alert-dismissible fade in" role="alert">
                                           <spring:message code="${requestScope.errorMessage}"/>
                                      </div>
                                  </c:if>
                                  <!-- ---------------------- -->
                                   <div class="col-md-12">
                                   <form action="test_process_start.do" method="POST">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="taskIndex" value="0"/>
                                    <table class="table table-bordered dataTable">
                                      <thead>
                                        <tr>
                                            <th colspan="3"><i class="fa fa-graduation-cap"></i>&nbsp;&nbsp;
                                            <spring:message code="label.assessment" /></th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <tr>
                                          <th scope="row" class="col-md-3"><spring:message code="label.assessment.name" />:</th>
                                          <td class="col-md-5" colspan="2"><c:out value="${process.assessment.name}"/></td>
                                        </tr>
                                        <tr>
                                          <th scope="row"><spring:message code="label.assessment.type" />:</th>
                                          <td colspan="2">
                                                ${SystemUtils.getAttribute('system.attrib.assessment.type',process.assessment.type)}
                                          </td>
                                        </tr>
                                        <tr>
                                          <th scope="row"><spring:message code="label.date.time" />:</th>
                                          <td class="col-md-5" colspan="2"><c:out value="${process.assessment.time}"/>&nbsp;&nbsp;
                                            <spring:message code="label.date.time.minutes" />
                                          </td>
                                        </tr>
                                        <tr>
                                          <th scope="row"><spring:message code="label.asmt.task.count" />:</th>
                                          <td colspan="2"><c:out value="${taskCount}"/></td>
                                        </tr>
                                        <tr>
                                          <th scope="row"><spring:message code="label.assessment.maxgrade" />:</th>
                                          <td colspan="2"><c:out value="${process.assessment.maxGrade}"/></td>
                                        </tr>
                                        <tr>
                                          <th scope="row" ><spring:message code="label.date.start" />-<spring:message code="label.date.end" /></th>
                                          <td colspan="2"><fmt:formatDate pattern="${dateFormatShort }" value="${process.assessment.startDate}" />-
                                          <fmt:formatDate pattern="${dateFormatShort }" value="${process.assessment.endDate}" />
                                          </td>
                                        </tr>
                                        <tr>
                                          <c:set var="asmt_status" value="${process.assessment.status }"/>
                                          <c:set var="process_state" value="${process.state }"/>
                                          <c:if test="${process.assessment.endDate < now }"><c:set var="asmt_status" value="2"/></c:if>
                                          <c:choose>
                                            <c:when test="${asmt_status == 2}"><c:set var="status_color" value="warning"/></c:when>
                                            <c:when test="${asmt_status == 3}"><c:set var="status_color" value="danger"/></c:when>
                                            <c:otherwise>
                                              <c:choose>
                                                <c:when test="${process_state == 2}">
                                                    <c:set var="asmt_status" value="5"/>
                                                    <c:set var="status_color" value="danger"/>
                                                </c:when>
                                                <c:when test="${process_state == 3}">
                                                    <c:set var="asmt_status" value="4"/>
                                                    <c:set var="status_color" value="success"/>
                                                </c:when>
                                                <c:otherwise><c:set var="status_color" value=""/></c:otherwise>
                                              </c:choose>
                                            </c:otherwise>
                                          </c:choose>
                                          <th scope="row" ><spring:message code="label.data.status" />:</th>
                                          <td class="${status_color}" colspan="2">
                                                ${SystemUtils.getAttribute('system.attrib.assessment.status',asmt_status)}
                                          </td>
                                        </tr>
                                        <tr>
                                          <th scope="row" ><spring:message code="label.assessment.entry.code" />:</th>
                                           <td  class="col-md-2">
                                                <input type="text" id="inpEntryCode" name="entryCode" class="form-control input-sm" required="required">
                                           </td>
                                          <td>
                                            <c:if test="${asmt_status == 1 }">
                                              <button type="submit" class="btn btn-danger btn-xs">
                                                <i class="fa fa-clock-o"></i>&nbsp;
                                                <spring:message code="label.assessment.start"/>
                                               </button> 
                                             </c:if>
                                            <c:if test="${asmt_status == 5 }">
                                              <button type="submit" class="btn btn-danger btn-xs">
                                                <i class="fa fa-clock-o"></i>&nbsp;
                                                <spring:message code="label.action.resume"/>
                                               </button> 
                                             </c:if>
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
