<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8" 
        errorPage="error.jsp" %>
<%@page import="model.identity.*,org.springframework.data.domain.Page,
common.utils.system.SystemUtils"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<title><spring:message code="label.page.asmt_list_user.title" /></title>

<!-- Bootstrap -->
<link href="resources/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Font Awesome -->
<link href="resources/lib/font-awesome/css/font-awesome.min.css" rel="stylesheet">

<!-- NProgress -->
<link href="resources/lib/nprogress/nprogress.css" rel="stylesheet">

<!-- Custom Theme Style -->
<link href="resources/css/custom.css" rel="stylesheet">

<!-- Data Table -->
<link href="resources/lib/datatables.net-bs/css/dataTables.bootstrap.min.css"
    rel="stylesheet">

</head>
<!-- ***************************** -->
<c:set var="assessmentsPage" value="${requestScope.assessmentsPage}"/>
<c:set var="dateFormatShort" value="${SystemUtils.getSettings('system.app.date.format.short')}"/>
<jsp:useBean id="now" class="java.util.Date" />
<!-- ***************************** -->

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp">
                <jsp:param name="page" value="asmt_list_user.vw" />
            </jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp">
                <jsp:param name="page" value="asmt_list_user.vw" />
            </jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="row">
                        <div class="col-md-10 col-sm-10 col-xs-10">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2><spring:message code="label.page.asmt_list_user.title" /></h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <table id="datatable"
                                        class="table table-striped table-bordered">
                                        <thead>
                                            <tr>
                                                <th>№</th>
                                                <th><spring:message code="label.assessment.name" /></th>
                                                <th><spring:message code="label.assessment.type" /></th>
                                                <th><spring:message code="label.date.start" /></th>
                                                <th><spring:message code="label.date.end" /></th>
                                                <th><spring:message code="label.data.status" /></th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <!-- *********Assessment list ************ -->
                                        <c:set var="index" value="${assessmentsPage.number * assessmentsPage.size}" />
                                        <c:forEach var="object" items="${assessmentsPage.content}" varStatus="loopCounter">
                                            <c:set var="assessment" value="${object[0]}"/>
                                            <c:set var="process" value="${object[1]}"/>
                                            
                                            <c:set var="process_state" value="${empty process  ? '1':process.state}"/>
                                            <c:set var="asmt_status" value="${assessment.status }"/>
                                            <c:set var="overall_status" value="${assessment.status }"/>
                                            <c:if test="${assessment.endDate < now }"><c:set var="asmt_status" value="2"/></c:if>
                                            <c:choose>
                                                <c:when test="${asmt_status == 3}"><c:set var="status_color" value="danger"/></c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${process_state == 2 || process_state == 4}">
                                                            <c:set var="overall_status" value="5"/>
                                                            <c:set var="status_color" value="danger"/>
                                                        </c:when>
                                                        <c:when test="${process_state == 3}">
                                                            <c:set var="overall_status" value="4"/>
                                                            <c:set var="status_color" value="success"/>
                                                        </c:when>
                                                        <c:otherwise><c:set var="status_color" value=""/></c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                            <tr class="${status_color}">
                                                <td class="col-md-1">${index + loopCounter.count }</td>
                                                <td><c:out value="${assessment.name}"/></td>
                                                <td>${SystemUtils.getAttribute('system.attrib.assessment.type', assessment.type )}</td>
                                                <td><fmt:formatDate pattern="${dateFormatShort}" value="${assessment.startDate}" /></td>
                                                <td><fmt:formatDate pattern="${dateFormatShort}" value="${assessment.endDate}" /></td>
                                                 <td>${SystemUtils.getAttribute('system.attrib.assessment.status',overall_status)}</td>
                                                <td class="col-md-1">
                                                    <c:if test="${asmt_status != 2 }">
                                                        <c:choose>
                                                            <c:when test="${asmt_status == 1 && process_state == 1 }">
                                                                <a href="test_process_init.do?assessment_id=${assessment.id}" 
                                                                     class="btn btn-primary btn-xs" role="button">
                                                                    <i class="fa fa-clock-o"></i>&nbsp;
                                                                        <spring:message code="label.assessment.take"/>
                                                                </a>
                                                            </c:when>
                                                            <c:when test="${asmt_status == 1 && (process_state == 2 || process_state == 4) }">
                                                                <a href="test_process_init.do?assessment_id=${assessment.id}" 
                                                                     class="btn btn-primary btn-xs" role="button">
                                                                    <i class="fa fa-clock-o"></i>&nbsp;
                                                                        <spring:message code="label.action.resume"/>
                                                                </a>
                                                            </c:when>
                                                            <c:when test="${ process_state == 3 }">
                                                                <a href="test_process_end.vw?asmt_process_id=${process.id}" 
                                                                     class="btn btn-primary btn-xs" role="button">
                                                                    <i class="fa fa-line-chart"></i>&nbsp;
                                                                        <spring:message code="label.assessment.result"/>
                                                                </a>
                                                            </c:when>
                                                        </c:choose>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <!-- *********/Assessment list ************ -->
                                        </tbody>
                                    </table>
                                    
                                    <!------------- Pagination -------------->
                                    <c:if test="${assessmentsPage.totalPages > 1}">
                                        <jsp:include page="include/pagination.jsp">
                                             <jsp:param name="page" value="assessment_list.vw" />
                                             <jsp:param name="totalPages" value="${assessmentsPage.totalPages}" />
                                             <jsp:param name="totalElements" value="${assessmentsPage.totalElements}" />
                                             <jsp:param name="currentIndex" value="${assessmentsPage.number}" />
                                             <jsp:param name="pageableSize" value="${assessmentsPage.size}" />
                                         </jsp:include>
                                     </c:if>
                                    <!--------------------------------------->
                                    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <jsp:include page="include/footer.jsp">
            <jsp:param name="page" value="assessment_private_list.vw" />
        </jsp:include>
        <!-- /footer content -->
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

    <!-- Dat Tables -->
    <script src="resources/lib/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="resources/lib/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    
    
    <script>
		$(document).ready(function() 
		{
			$('#datatable').dataTable(
			{
				"searching" : false,
				"pagingType" : "full_numbers",
				"paging" : false,
				"info" : false

			});
		});
	</script>
</body>
</html>
