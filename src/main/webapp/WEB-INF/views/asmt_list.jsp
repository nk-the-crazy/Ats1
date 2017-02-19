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

<title><spring:message code="label.page.asmt_list.title" /></title>

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
    
<!-- bootstrap-daterangepicker -->
<link href="resources/lib/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">

</head>
<!-- ***************************** -->
<c:set var="assessmentsPage" value="${requestScope.assessmentsPage}"/>
<c:set var="dateFormatShort" value="${SystemUtils.getSettings('system.app.date.format.short')}"/>
<!-- ***************************** -->

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp">
                <jsp:param name="page" value="asmt_list.vw" />
            </jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp">
                <jsp:param name="page" value="asmt_list.vw" />
            </jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="row">
                        <div class="col-md-11 col-sm-11 col-xs-11">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2><spring:message code="label.page.asmt_list.title" /></h2>
                                    <div class="btn-group pull-right">
                                      <button type="button" class="btn btn-success btn-xs">
                                        <i class="fa fa-plus"></i>&nbsp;
                                                <spring:message code="label.menu.assessment.register"/></button>
                                    </div>
                                  <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <form id="user_search" data-parsley-validate action="asmt_list.vw"
                                        class="form-horizontal form-label-left">
                                       <div class="form-group">
                                            <div class="col-md-2 col-sm-2 col-xs-2">
                                                <label class="control-label" for="assessment-name">
                                                    <spring:message code="label.assessment.name" />:</label>
                                                <input type="text" id="assessment-name" name="assessmentName" value="${param.assessmentName}"
                                                       class="form-control input-sm">
                                            </div>
                                            <div class="col-md-2 col-sm-2 col-xs-2">
                                                <label class="control-label" for="assessment-date">
                                                    <spring:message code="label.date.start" /></label>
                                                <input id="assessment-date" type="text" class="date-picker form-control input-sm" 
                                                name="startDateFrom" value="${param.startDateFrom}">
                                             </div>
                                             <div class="col-md-3 col-sm-3 col-xs-3">
                                                 <label class="control-label" for="assessment-type">
                                                       <spring:message code="label.asmt.task.mode.type" />:</label>
                                                 <select id="assessment-type" class="form-control input-select-sm" name="assessmentType">
                                                    <option value="0"><spring:message code="label.data.all" /></option>
                                                    <c:forEach var="systemAttr" varStatus="loopCounter"
                                                        items="${SystemUtils.getAttributes('system.attrib.assessment.type',locale)}"> 
                                                        <option ${param.assessmentType == (loopCounter.count) ? 'selected="selected"' : ''}
                                                        value="${loopCounter.count}">${systemAttr}</option>
                                                    </c:forEach>
                                                 </select>
                                             </div>
                                             <div>
                                                <div>&nbsp;</div>
                                                <button type="submit"
                                                    class="btn btn-primary btn-xs">
                                                    <i class="fa fa-search"></i>
                                                    <spring:message code="label.action.search" />
                                                </button>
                                            </div>
                                         </div>
                                    </form>

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
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <!-- *********Assessment list ************ -->
                                        <c:set var="index" value="${assessmentsPage.number * assessmentsPage.size}" />
                                        <c:forEach var="assessment" items="${assessmentsPage.content}" varStatus="loopCounter">
                                            <tr class="${assessment.status == 1 ? '' : 'danger'}">
                                                <td class="col-md-1">${index + loopCounter.count }</td>
                                                <td><a href="asmt_details.vw?assessment_id=${assessment.id}">
                                                    <c:out value="${assessment.name}"/></a></td>
                                                <td>${SystemUtils.getAttribute('system.attrib.assessment.type', assessment.type ,locale)}</td>
                                                <td><fmt:formatDate pattern="${dateFormatShort}" value="${assessment.startDate}" /></td>
                                                <td><fmt:formatDate pattern="${dateFormatShort}" value="${assessment.endDate}" /></td>
                                                <td>${SystemUtils.getAttribute('system.attrib.assessment.status',assessment.status,locale)}</td>
                                            </tr>
                                        </c:forEach>
                                        <!-- *********/Assessment list ************ -->
                                        </tbody>
                                    </table>
                                    
                                    <!------------- Pagination -------------->
                                    <c:if test="${assessmentsPage.totalPages > 1}">
                                        <jsp:include page="include/pagination.jsp">
                                             <jsp:param name="page" value="asmt_list.vw" />
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
            <jsp:param name="page" value="assessment_list.vw" />
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
    
    <!-- bootstrap-daterangepicker -->
    <script src="resources/lib/moment/min/moment.min.js"></script>
    <script src="resources/lib/moment/locale/ru.js"></script>
    <script src="resources/lib/moment/locale/ky.js"></script>
    <script src="resources/lib/bootstrap-daterangepicker/daterangepicker.js"></script>
    <script>
      $(document).ready(function() 
      { 
          moment.locale('${locale}');
          
          $('#assessment-date').daterangepicker(
          {
              locale: { 
                  format: "${dateFormatShort.toUpperCase()}"
                },
              singleDatePicker: true,
              //startDate: '01.01.2016',
              calender_style: "picker_4"
                  }, function(start, end, label) {
                      console.log(start.toISOString(), end.toISOString(), label);
          });
      });
    </script>
    <!-- /bootstrap-daterangepicker -->    
    
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
