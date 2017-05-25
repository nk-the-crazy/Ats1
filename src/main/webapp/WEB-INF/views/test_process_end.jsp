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

<title><spring:message code="label.page.asmt_process_end.title"/></title>

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

<!-- Data Table -->
<link href="resources/lib/datatables.net-bs/css/dataTables.bootstrap.css"
    rel="stylesheet">
    
</head>
<!-- ***************************** -->
<c:set var="process" value="${assessmentResult.process}"/>
<c:set var="assessment" value="${process.assessment}"/>
<c:set var="assessmentResult" value="${requestScope.assessmentResult}"/>
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
									<h2><spring:message code="label.page.asmt_process_end.title"/></h2>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
                                  <div class="" role="tabpanel" data-example-id="togglable-tabs">
                                    <ul id="asmtResultsTab" class="nav nav-tabs bar_tabs" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#tab_content1" id="result-tab" role="tab" data-toggle="tab" aria-expanded="true">
                                            <spring:message code="label.assessment.result" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content2" id="result-details-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <spring:message code="label.data.details" /></a>
                                        </li>
                                        <li role="presentation" class="" style="display:none" id="tabHeader">
                                            <a href="#itemResponseTabContent" id="item-response-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <spring:message code="label.asmt.task.response.content" /></a>
                                        </li>
                                    </ul>
                                    <div id="asmtResultsTabContent" class="tab-content">
                                        <div id="tab_content1" role="tabpanel" class="tab-pane col-md-10 fade active in" 
                                              aria-labelledby="result-tab">
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
                                                      <td colspan="3"><c:out value="${assessment.name}"/></td>
                                                    </tr>
                                                    <thead><tr><th colspan="4"></th></tr></thead>
                                                    <tr>
                                                      <th scope="row" ><spring:message code="label.date.start" /></th>
                                                      <td><fmt:formatDate pattern="${dateTimeFormatShort }" value="${process.startDate}" /></td>
                                                      <th scope="row" ><spring:message code="label.date.end" /></th>
                                                      <td><fmt:formatDate pattern="${dateTimeFormatShort }" value="${process.endDate}" /></td>
                                                    </tr>
                                                    <tr>
                                                      <th scope="row" class="col-md-3"><spring:message code="label.date.time" />:</th>
                                                      <td class="col-md-4"><c:out value="${assessment.time}"/>&nbsp;&nbsp;
                                                        <spring:message code="label.date.time.minutes" />
                                                      </td>
                                                      <th scope="row" class="col-md-2"><spring:message code="label.date.time.elapsed" />:</th>
                                                      <td class="col-md-4"><c:out value="${StringUtils.millisToDetails(process.timeElapsed)}"/>&nbsp;&nbsp;
                                                        <spring:message code="label.date.time.minutes" />
                                                      </td>
                                                    </tr>
                                                     <thead><tr><th colspan="4"></th></tr></thead>
                                                    <tr>
                                                      <th scope="row" class="col-md-2 success"><spring:message code="label.asmt.task.count"/> :</th>
                                                      <td>${assessmentResult.taskCount }</td>
                                                      <th scope="row" class="col-md-2 success"><spring:message code="label.asmt.task.respond" />:</th>
                                                      <td>${assessmentResult.responseCount }</td>
                                                    </tr>
                                                    <tr>
                                                      <th scope="row" class="col-md-2 success"><spring:message code="label.assessment.maxgrade" />:</th>
                                                      <td>${assessment.maxGrade}</td>
                                                      <th scope="row" class="col-md-2 success"><spring:message code="label.assessment.score" />:</th>
                                                      <td><c:out value="${assessmentResult.score}"/></td>
                                                    </tr>
                                                    <tr>
                                                      <th scope="row" class="col-md-2 success"><spring:message code="label.asmt.result.item.count.true"/>:</th>
                                                      <td>${assessmentResult.rightResponseCount }</td>
                                                      <th scope="row" class="col-md-2 success"><spring:message code="label.asmt.result.item.count.false" />:</th>
                                                      <td>${assessmentResult.responseCount - assessmentResult.rightResponseCount}</td>
                                                    </tr>
                                                  </tbody>
                                               </table>
                                        </div>
                                        <div role="tabpanel" class="tab-pane col-md-12 fade" 
                                             id="tab_content2" aria-labelledby="result-details-tab">
                                            <table id="datatable" class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th>№</th>
                                                    <th class="col-md-4"><spring:message code="label.asmt.result.sfalse" /></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                              </tbody>
                                            </table>
                                        </div>
                                     </div>
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
					value="test_process_end.vw" /></jsp:include>
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
    
    <!-- Data Tables -->
    <script src="resources/lib/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="resources/lib/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script type="text/javascript">
    
    var table = null;
    
    $(document).ready(function() 
    {
        var taskId = 0;
        var indexRow = 0;
        
        table = $('#datatable').DataTable(
        {
            "autoWidth": false,
            "language": 
            {
                "url": "resources/lib/datatables.net/i18n/ru.json"
            },
            "iDisplayLength": 12,
            "processing": true,
            "serverSide": true,
            "searching" : false,
            "pagingType" : "full_numbers",
            "paging" : true,
            "lengthChange": true,
            "info" : true,
           
            "ajax": 
            {   "url": 'rest/assessment/response/wlist?asmt_process_id=${process.id}',
                "type": "GET"
            },
            "columns": [
                { "data": null , "width": "5%" , 'searchable': false,'orderable': false, 
                    'render': function (data, type, row, meta) 
                    {
                        return meta.row + meta.settings._iDisplayStart + 1;
                    }
                },
                { "data": null ,'searchable': false, 'orderable': false, 
                    'render': function (data, type, row, meta) 
                    {
                        if(data['taskId'] != taskId)
                            return data['taskItemContent'];
                        else
                            return '';
                    }
                }
               
            ],
            "createdRow": function( row, data, dataIndex ) 
            {
                if ( data['grade'] <= 0 ) 
                {
                  $(row).addClass( 'warning' );
                }
            },
    
        });
      
    });
 
    </script>
    
</body>
</html>
