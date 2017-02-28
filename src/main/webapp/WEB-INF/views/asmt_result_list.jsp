<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8" 
        errorPage="error.jsp" %>
<%@page import="model.identity.*,org.springframework.data.domain.Page,
common.utils.system.SystemUtils,common.utils.StringUtils"%>

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

<title><spring:message code="label.page.asmt_result_list.title" /></title>

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
<c:set var="resultsPage" value="${requestScope.resultsPage}"/>
<c:set var="dateFormatShort" value="${SystemUtils.getSettings('system.app.date.format.short')}"/>
<jsp:useBean id="now" class="java.util.Date" />
<!-- ***************************** -->

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp">
                <jsp:param name="page" value="asmt_result_list.vw" />
            </jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp">
                <jsp:param name="page" value="asmt_results_user.vw" />
            </jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="row">
                        <div class="col-md-12 col-sm-12 col-xs-12">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2><spring:message code="label.page.asmt_result_list.title" /></h2>
                                    <div class="btn-group pull-right">
                                         <button data-toggle="dropdown" class="btn btn-success dropdown-toggle btn-xs" 
                                          type="button" aria-expanded="false"><i class="fa fa-files-o"></i>&nbsp;
                                            <spring:message code="label.action.export"/>&nbsp;&nbsp;<span class="caret"></span>
                                         </button>
                                         <ul role="menu" class="dropdown-menu">
                                          <li><a href="#"><i class="fa fa-file-excel-o"></i>&nbsp;&nbsp;<spring:message code="label.action.export.xls"/></a>
                                          </li>
                                          <li class="divider"></li>
                                          <li><a href="#"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;<spring:message code="label.action.export.xls"/></a>
                                          </li>
                                          <li class="divider"></li>
                                          <li><a href="#"><i class="fa fa-file-word-o"></i>&nbsp;&nbsp;<spring:message code="label.action.export.doc"/></a>
                                          </li>
                                          <li class="divider"></li>
                                         </ul>
                                    </div>
                                  <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <form id="user_search" data-parsley-validate action="asmt_result_list.vw"
                                        class="form-horizontal form-label-left">
                                       <div class="form-group">
                                            <div class="col-md-2 col-sm-2 col-xs-2">
                                                <label class="control-label" for="user-lastname">
                                                    <spring:message code="label.user.last_name" />:</label>
                                                <input type="text" id="user-lastname" name="lastName" value="${param.lastName}"
                                                       class="form-control input-sm">
                                            </div>
                                            <div class="col-md-2 col-sm-2 col-xs-2">
                                                <label class="control-label" for="process-date">
                                                    <spring:message code="label.date.start" /></label>
                                                <input id="process-date" type="text" class="date-picker form-control input-sm" 
                                                name="startDateFrom" value="${param.startDateFrom}">
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
                                                <th></th>
                                                <th><spring:message code="label.user.full_name" /></th>
                                                <th><spring:message code="label.assessment.name" /></th>
                                                <th><spring:message code="label.date.start" /></th>
                                                <th><spring:message code="label.data.status" /></th>
                                                <th><spring:message code="label.assessment.score" /></th>
                                                <th><spring:message code="label.asmt.task.respond" /></th>
                                                <th><spring:message code="label.asmt.result.item.count.all"/></th>
                                                
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <!-- *********Process list ************ -->
                                        <c:set var="index" value="${resultsPage.number * resultsPage.size}" />
                                        <c:forEach var="results" items="${resultsPage.content}" varStatus="loopCounter">
                                            <c:set var="process" value="${results[0]}"/>
                                            <c:set var="assessment" value="${process.assessment}"/>
                                            <c:set var="user" value="${process.user}"/>
                                            <c:set var="taskCount" value="${results[4]}"/>
                                            <c:set var="responseCount" value="${results[5]}"/>
                                            <c:set var="rightResponseCount" value="${results[6]}"/>
                                            <c:set var="score" value="${results[7]}"/>
                                            <c:set var="process_state" value="${process.state}"/>
                                            <c:choose>
                                                <c:when test="${process_state == 2}">
                                                    <c:set var="overall_status" value="5"/>
                                                    <c:set var="status_color" value="danger"/>
                                                </c:when>
                                                <c:when test="${process_state == 3}">
                                                    <c:set var="overall_status" value="4"/>
                                                    <c:set var="status_color" value=""/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="overall_status" value="1"/>
                                                    <c:set var="status_color" value=""/>
                                                </c:otherwise>
                                            </c:choose>
                                            <tr class="${status_color}">
                                                <td>&nbsp;${index + loopCounter.count }</td>
                                                <td><a class="btn btn-primary btn-xs btn-td" 
                                                        href="asmt_result_details.vw?asmt_process_id=${process.id }" role="button" aria-expanded="false">
                                                        <i class="fa fa-folder-open"></i>&nbsp;
                                                 </a></td>
                                                <td><a href="user_details.vw?user_id=${user.id}">
                                                    <c:out value="${user.person.lastName}"/>&nbsp;
                                                    <c:out value="${user.person.firstName}"/></a></td>
                                                <td><a href="asmt_test_details.vw?assessment_id=${assessment.id}">
                                                    <c:out value="${assessment.name}"/></a></td>
                                                <td><fmt:formatDate pattern="${dateFormatShort}" value="${process.startDate}" /></td>
                                                <td>${SystemUtils.getAttribute('system.attrib.assessment.status',overall_status,locale)}</td>
                                                <td><c:out value="${score}"/></td>
                                                <td><c:out value="${responseCount}"/></td>
                                                <td class="col-md-2">
                                                 <c:out value="${rightResponseCount}"/>&nbsp;-&nbsp;<c:out value="${responseCount - rightResponseCount}"/>
                                                </td>
                                                
                                            </tr>
                                        </c:forEach>
                                        <!-- *********/Process list ************ -->
                                        </tbody>
                                    </table>
                                    
                                    <!------------- Pagination -------------->
                                    <c:if test="${resultsPage.totalPages > 1}">
                                        <jsp:include page="include/pagination.jsp">
                                             <jsp:param name="page" value="asmt_result_list.vw" />
                                             <jsp:param name="totalPages" value="${resultsPage.totalPages}" />
                                             <jsp:param name="totalElements" value="${resultsPage.totalElements}" />
                                             <jsp:param name="currentIndex" value="${resultsPage.number}" />
                                             <jsp:param name="pageableSize" value="${resultsPage.size}" />
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
            <jsp:param name="page" value="asmt_results_user.vw" />
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
				"info" : false,
				"language": 
                {
                    "emptyTable": '<spring:message code="message.info.records.notfound" />'
                },
				'columnDefs': 
				 [
		            { orderable: false, "width": "1%",  targets: [0] },
		            { orderable: false, "width": "1%",  targets: [1] }
		         ]

			});
		});
	</script>
</body>
</html>
