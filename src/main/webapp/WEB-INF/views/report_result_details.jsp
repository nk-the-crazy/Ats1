<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8" 
        errorPage="error.jsp" 
        import="model.assessment.*,common.utils.system.SystemUtils, common.utils.StringUtils"%>

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

<title><spring:message code="label.page.report_result_details.title" /></title>

<!-- Bootstrap -->
<link href="resources/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Font Awesome -->
<link href="resources/lib/font-awesome/css/font-awesome.min.css" rel="stylesheet">

<!-- NProgress -->
<link href="resources/lib/nprogress/nprogress.css" rel="stylesheet">

<!-- Custom Theme Style -->
<link href="resources/css/custom.css" rel="stylesheet">

<!-- Data Table -->
<link href="resources/lib/datatables.net-bs/css/dataTables.bootstrap.css"
    rel="stylesheet">

</head>
<!-- ***************************** -->
<c:set var="assessmentResult" value="${requestScope.assessmentResult}"/>
<c:set var="process" value="${assessmentResult.process}"/>
<c:set var="assessment" value="${process.assessment}"/>
<c:set var="user" value="${userDetails}"/>
<c:set var="dateTimeFormatShort" value="${SystemUtils.getSettings('system.app.date.time.format.short')}"/>
<!-- ***************************** -->
<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp"><jsp:param name="page"
                    value="report_result_details.vw" /></jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp"><jsp:param name="page"
                    value="report_result_details.vw" /></jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="row">
                        <div class="col-md-10 col-sm-10 col-xs-10">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2><spring:message code="label.page.report_result_details.title" /></h2>
                                    <div class="pull-right">

                                        <div class="btn-group">
                                        <button data-toggle="dropdown" class="btn btn-success dropdown-toggle btn-xs" 
                                          type="button" aria-expanded="false"><i class="fa fa-files-o"></i>&nbsp;
                                            <spring:message code="label.action.export"/>&nbsp;&nbsp;<span class="caret"></span>
                                        </button>
                                        <ul role="menu" class="dropdown-menu">
                                          <li><a href="report_result_details.vw?outputType=2&asmt_process_id=${param.asmt_process_id}"><i class="fa fa-file-excel-o"></i>&nbsp;&nbsp;<spring:message code="label.action.export.xls"/></a>
                                          </li>
                                          <li class="divider"></li>
                                          <li><a href="#"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;<spring:message code="label.action.export.pdf"/></a>
                                          </li>
                                          <li class="divider"></li>
                                          <li><a href="#"><i class="fa fa-file-word-o"></i>&nbsp;&nbsp;<spring:message code="label.action.export.doc"/></a>
                                          </li>
                                          <li class="divider"></li>
                                        </ul>
                                        </div>
                                        <button type="button" class="btn btn-primary btn-xs" onclick="window.history.back();">
                                            <i class="fa fa-chevron-left"></i>&nbsp;
                                                <spring:message code="label.action.back"/>
                                        </button>
                                        </div>
                                      <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                <div class="" role="tabpanel" data-example-id="togglable-tabs">
                                    <ul id="assessmentResultDetailsTab" class="nav nav-tabs bar_tabs" role="tablist">
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
                                    <div id="asmtResultDetailsTabContent" class="tab-content">
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
                                                  <th scope="row" class="col-md-2"><spring:message code="label.user.full_name" />:</th>
                                                  <td colspan="3">
                                                    <a href="user_details.vw?user_id=${user.id}">
                                                    <c:out value="${user.person.lastName}"/>&nbsp;<c:out value="${user.person.firstName}"/></a>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-2"><spring:message code="label.assessment.name" />:</th>
                                                  <td colspan="3"><c:out value="${assessment.name}"/></td>
                                                </tr>
                                                <thead>
                                                    <tr><th colspan="4"></th></tr>
                                                </thead>
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
                                                 <thead>
                                                    <tr><th colspan="4"></th></tr>
                                                </thead>
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
                                                    <th class="col-md-4"><spring:message code="label.asmt.task.item.content" /></th>
                                                    <th><spring:message code="label.asmt.task.mode.type" /></th>
                                                    <th><spring:message code="label.asmt.task.grade" /></th>
                                                    <th class="col-md-5"><spring:message code="label.asmt.task.response" /></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                              </tbody>
                                            </table>
                                        </div>
                                        <div id="itemResponseTabContent" role="tabpanel" class="tab-pane col-md-12 fade in" 
                                              aria-labelledby="item-response-tab">
                                                  <div id="imgProgress"><img src="resources/images/ajax-loader.gif" style="width:40px; margin-top:10px; display:none"></div>
                                                  <div id="dvItemContent" class="item-response-content" style="display:none">
                                              </div>
                                        </div>
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
        <jsp:include page="include/footer.jsp">
            <jsp:param name="page" value="asmt_result_details.vw" />
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
    <script type="text/javascript">
    $(document).ready(function()
    {
        $('a[data-toggle="tab"]').on('show.bs.tab', function(e) {
            localStorage.setItem('reportResultDetailsTActiveTab', $(e.target).attr('href'));
        });
        
        var activeTab = localStorage.getItem('reportResultDetailsTActiveTab');
        
        if(activeTab)
        {
            $('#assessmentResultDetailsTab a[href="' + activeTab + '"]').tab('show');
        }
    });
    </script>   
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
            {   "url": 'rest/assessment/response/list?asmt_process_id=${process.id}',
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
                            return '<a href="asmt_task_details.vw?asmt_task_id='+data['taskId']+'">'+data['taskItemContent']+'</a>';
                        else
                            return '';
                    }
                },
                { "data": null ,'searchable': false, 'orderable': false, 
                    'render': function (data, type, row, meta) 
                    {
                        if(data['taskId'] != taskId)
                            return data['taskModeTypeName'];
                        else
                            return '';
                    }
                },
                { "data": null ,'searchable': false, 'orderable': false, 
                    'render': function (data, type, row, meta) 
                    {
                        if(data['taskId'] != taskId)
                            return data['grade'];
                        else
                            return '';
                    }
                },
                { "data": null,'orderable': false,
                    "render" : function ( data, type, full, meta ) 
                    { 
                        taskId = data['taskId'];

                        if(data['taskModeType'] == 4)
                        {
                        	return ' <button class="btn btn-primary btn-xs btn-td"'+ 
                        	       ' onclick ="loadResponseContent('+data['responseDetailId']+');"' +
                                   ' type="button" aria-expanded="false"><i class="fa fa-file-o"></i>&nbsp;' +
                                   ' <spring:message code="label.asmt.task.response" /></button>';
                        }
                        else if(data['taskModeType'] == 3)
                        {
                           return data['itemResponse'];
                        }
                        else
                        {
                           return data['itemDetail'];
                        }
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
    <script>
    //*************************
    function loadResponseContent(responseDetailsId)
    {
        $("#dvItemContent").hide();
        $("#imgProgress").show();
        $('#tabHeader').show();
        $('#assessmentResultDetailsTab a[href="#itemResponseTabContent"]').tab('show')
        var sURL = "rest/assessment/response/content?asmt_response_detail_id="+responseDetailsId;
        
        $.ajax(
        {
            url:sURL,
            success: function (response) 
            {
                $("#imgProgress").hide();
                $('#dvItemContent').html(response);
                $("#dvItemContent").show();
            },
            error: function () 
            {
                $("#imgProgress").hide();
                $('dvItemContent').html('Error loading Page!');
                $("#dvItemContent").show();
            },
        });
 
    }
    </script>
 
</body>
</html>
