<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8" 
        errorPage="error.jsp" 
        import="model.identity.*,model.assessment.*, common.utils.system.SystemUtils"%>

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

<title><spring:message code="label.page.asmt_details.title" /></title>

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
<c:set var="assessment" value="${requestScope.assessmentDetails}"/>
<c:set var="dateFormatShort" value="${SystemUtils.getSettings('system.app.date.format.short')}"/>
<jsp:useBean id="now" class="java.util.Date" />
<!-- ***************************** -->

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp"><jsp:param name="page"
                    value="asmt_details.vw" /></jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp"><jsp:param name="page"
                    value="asmt_details.vw" /></jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="row">
                        <div class="col-md-10 col-sm-10 col-xs-10">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2><spring:message code="label.page.asmt_details.title" /></h2>
                                     <div style="text-align: right;">
                                        <a href="asmt_user_details.vw?outputType=2&assessment_id=${param.assessment_id }" role="button" class="btn btn-info btn-xs">
                                            <i class="fa fa-file-excel-o"></i>&nbsp;
                                                <spring:message code="label.page.user_details.title"/>
                                        </a>
                                        <a href="asmt_test_edit.vw?assessment_id=${param.assessment_id }" role="button" class="btn btn-info btn-xs">
                                            <i class="fa fa-pencil-square-o"></i>&nbsp;
                                                <spring:message code="label.action.edit"/>
                                        </a>
                                        <button type="button" class="btn btn-primary btn-xs" onclick="window.history.back();">
                                            <i class="fa fa-chevron-left"></i>&nbsp;
                                                <spring:message code="label.action.back"/>
                                        </button>
                                      </div>
                                      <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                <div class="" role="tabpanel" data-example-id="togglable-tabs">
                                    <ul id="assessmentDetailsTab" class="nav nav-tabs bar_tabs" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#tab_content1" id="assessment-tab" role="tab" data-toggle="tab" aria-expanded="true">
                                            <i class="fa fa-graduation-cap">&nbsp;</i>
                                            <spring:message code="label.assessment" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content2" id="participants-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <i class="fa fa-users">&nbsp;</i>
                                            <spring:message code="label.assessment.participants" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content3" id="tasks-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <i class="fa fa-cube">&nbsp;</i>
                                            <spring:message code="label.asmt.task.list" /></a>
                                        </li>
                                    </ul>
                                    <div id="assessmentDetailsTabContent" class="tab-content">
                                        <div id="tab_content1" role="tabpanel" class="tab-pane col-md-8 fade active in" 
                                              aria-labelledby="assessment-tab">
                                            <table class="table table-bordered dataTable">
                                              <thead>
                                                <tr>
                                                    <th colspan="2">
                                                    <spring:message code="label.assessment" /></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <tr>
                                                  <th scope="row" class="col-md-3"><spring:message code="label.assessment.name" />:</th>
                                                  <td class="col-md-5"><c:out value="${assessment.name}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-3"><spring:message code="label.assessment.entry.code" />:</th>
                                                  <td class="col-md-5 warning"><c:out value="${assessment.entryCode}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-3"><spring:message code="label.assessment.author" />:</th>
                                                  <td class="col-md-5">
                                                    <a href="user_details.vw?user_id=${assessment.author.id}">
                                                    <c:out value="${assessment.author.userName}"/></a></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><spring:message code="label.assessment.type" />:</th>
                                                  <td>
                                                        ${SystemUtils.getAttribute('system.attrib.assessment.type',assessment.type)}
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-3"><spring:message code="label.asmt.task.count" />:</th>
                                                  <td class="col-md-5"><c:out value="${assessment.taskCount}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-3"><spring:message code="label.assessment.maxgrade" />:</th>
                                                  <td class="col-md-5"><c:out value="${assessment.maxGrade}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><spring:message code="label.assessment.eval.method" />:</th>
                                                  <td>
                                                        ${SystemUtils.getAttribute('system.attrib.assessment.eval.method',assessment.evaluationMethod)}
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-3"><spring:message code="label.date.time" />:</th>
                                                  <td class="col-md-5"><c:out value="${assessment.time}"/>&nbsp;&nbsp;
                                                  <spring:message code="label.date.time.minutes" />
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.date.start" />:</th>
                                                  <td><fmt:formatDate pattern="${dateFormatShort }" value="${assessment.startDate}" /></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.date.end" />:</th>
                                                  <td><fmt:formatDate pattern="${dateFormatShort }" value="${assessment.endDate}" /></td>
                                                </tr>
                                                <tr>
                                                  <c:set var="asmt_status" value="${assessment.status }"/>
                                                  <c:if test="${assessment.endDate < now }"><c:set var="asmt_status" value="2"/></c:if>
                                                  <c:choose>
                                                    <c:when test="${asmt_status == 2}"><c:set var="status_color" value="warning"/></c:when>
                                                    <c:when test="${asmt_status == 3}"><c:set var="status_color" value="danger"/></c:when>
                                                    <c:otherwise><c:set var="status_color" value=""/></c:otherwise>
                                                  </c:choose>
                                                  <th scope="row" ><spring:message code="label.data.status" />:</th>
                                                  <td  class="${status_color}">
                                                        ${SystemUtils.getAttribute('system.attrib.assessment.status',asmt_status)}
                                                  </td>
                                                </tr>
                                              </tbody>
                                            </table>
                                        </div>
                                        <div id="tab_content2" role="tabpanel" class="tab-pane fade col-md-8" aria-labelledby="participants-tab">
                                            <table id="" class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th>№</th>
                                                    <th><spring:message code="label.group.name" /></th>
                                                    <th><spring:message code="label.group.desc" /></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <!-- *********Group list ************ -->
                                                <c:forEach var="group" items="${assessment.participants}" varStatus="loopCounter">
                                                    <tr>
                                                        <td class="col-md-1">${loopCounter.count }</td>
                                                        <td><a href="group_details.vw?group_id=${group.id}">
                                                            <c:out value="${group.name}"/></a></td>
                                                        <td><c:out value="${group.details}"/></td>
                                                    </tr>
                                                </c:forEach>
                                                <!-- *********/Group list ************ -->
                                              </tbody>
                                            </table>                                              
                                        </div>
                                        <div role="tabpanel" class="tab-pane fade col-md-10" id="tab_content3"  aria-labelledby="tasks-tab">
                                            <table id="datatable" class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th>№</th>
                                                    <th class="col-md-7"><spring:message code="label.asmt.task.item.content" /></th>
                                                    <th class="col-md-2"><spring:message code="label.asmt.task.mode.type" /></th>
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
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <jsp:include page="include/footer.jsp">
            <jsp:param name="page" value="assessment_details.vw" />
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
            localStorage.setItem('assessmentDetailsActiveTab', $(e.target).attr('href'));
        });
        var activeTab = localStorage.getItem('assessmentDetailsActiveTab');
        if(activeTab){
            $('#assessmentDetailsTab a[href="' + activeTab + '"]').tab('show');
        }
    });
    </script>
    
    <script type="text/javascript">
   
    var table = null;
    //---------------------
    function getLang()
    {
        var lang = '${pageContext.response.locale }';
        if(lang == "" || lang == "en")
            return "";
        else
            return 'resources/lib/datatables.net/i18n/'+lang+'.json';
    }
    //---------------------
    
    
    $(document).ready(function() 
    {
        var taskId = 0;
        var indexRow = 0;
        
        table = $('#datatable').DataTable(
        {
            "autoWidth": false,
            "language": 
            {
                "url": getLang()
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
            {   "url": 'rest/assessment/test/task/list?assessment_id=${assessment.id}',
                "type": "GET"
            },
            "columns": [
                { "data": null , "width": "5%" , 'searchable': false,'orderable': false, 
                    'render': function (data, type, row, meta) 
                    {
                        return meta.row + meta.settings._iDisplayStart + 1;
                    }
                },
                { "data": null ,'orderable': false, 
                	'render': function (data, type, row, meta) 
                    {
                	    return '<a href="asmt_task_details.vw?asmt_task_id='+data['id']+'">'+data['itemContent']+'</a>';
                	}
                },
                { "data": "modeTypeName" ,'orderable': false }
            ],
            "createdRow": function( row, data, dataIndex ) 
            {
                if ( data["modeType"] == 2 ) 
                {
                  $(row).addClass( 'info' );
                }
                else if ( data["modeType"] == 4 ) 
                {
                  $(row).addClass( 'warning' );
                }
            },
        });
    });
    </script>

</body>
</html>
