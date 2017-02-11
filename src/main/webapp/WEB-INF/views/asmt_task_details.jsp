<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8" 
        errorPage="error.jsp" 
        import="model.assessment.*,common.utils.system.SystemUtils"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- ************************************* -->

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title><spring:message code="label.page.asmt.task_details.title" /></title>

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
<c:set var="task" value="${requestScope.taskDetails}"/>
<!-- ***************************** -->
<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp"><jsp:param name="page"
                    value="group_details.vw" /></jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp"><jsp:param name="page"
                    value="group_details.vw" /></jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="row">
                        <div class="col-md-10 col-sm-10 col-xs-10">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2><spring:message code="label.page.asmt.task_details.title" /></h2>
                                     <div style="text-align: right;">
                                        <button type="button" class="btn btn-info btn-xs">
                                            <i class="fa fa-pencil-square-o"></i>&nbsp;
                                                <spring:message code="label.action.edit"/>
                                        </button>
                                        <button type="button" class="btn btn-primary btn-xs" onclick="window.history.back();">
                                            <i class="fa fa-chevron-left"></i>&nbsp;
                                                <spring:message code="label.action.back"/>
                                        </button>
                                      </div>
                                      <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                <div class="" role="tabpanel" data-example-id="togglable-tabs">
                                    <ul id="taskDetailsTab" class="nav nav-tabs bar_tabs" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#tab_content1" id="task-tab" role="tab" data-toggle="tab" aria-expanded="true">
                                            <i class="fa fa-cube">&nbsp;</i>
                                            <spring:message code="label.asmt.task" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content2" id="task-details-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <spring:message code="label.asmt.task.details" /></a>
                                        </li>
                                    </ul>
                                    <div id="groupDetailsTabContent" class="tab-content">
                                        <div id="tab_content1" role="tabpanel" class="tab-pane col-md-8 fade active in" 
                                              aria-labelledby="groups-tab">
                                            <table class="table table-bordered dataTable">
                                              <thead>
                                                <tr>
                                                    <th colspan="2"></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <tr>
                                                  <th scope="row" class="col-md-3"><spring:message code="label.asmt.task.name" />:</th>
                                                  <td class="col-md-7"><c:out value="${task.itemName}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.asmt.task.item.content" />:</th>
                                                  <td style="height: 60px;">${task.itemContent}</td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.asmt.task.complexity" />:</th>
                                                    <td>
                                                    ${SystemUtils.getAttribute('system.attrib.task.complexity', task.complexity, locale)}
                                                    </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.asmt.task.mode" />:</th>
                                                    <td>
                                                    ${SystemUtils.getAttribute('system.attrib.task.mode', task.mode, locale)}
                                                    </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.asmt.task.mode.type" />:</th>
                                                    <td>
                                                    ${SystemUtils.getAttribute('system.attrib.task.mode.type', task.modeType, locale)}
                                                    </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.asmt.task.category.name" />:</th>
                                                  <td class="col-lg-3"><c:out value="${task.category.name}"/></td>
                                                </tr>
                                                
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.data.status" />:</th>
                                                    <td class="${task.status == 1 ? 'a' : 'danger'}">
                                                    ${SystemUtils.getAttribute('system.attrib.data.status', task.status, locale)}
                                                    </td>
                                                </tr>
                                              </tbody>
                                            </table>
                                        </div>
                                        
                                        <div role="tabpanel" class="tab-pane col-md-10 fade" 
                                             id="tab_content2" aria-labelledby="task-details-tab">
                                            
                                            <table id="" class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th>№</th>
                                                    <th><spring:message code="label.asmt.task.item.grade" />(%)</th>
                                                    <th><spring:message code="label.asmt.task.item.option" /></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <!-- *********Task Details list************ -->
                                                <c:forEach var="taskDetails" items="${task.details}" varStatus="loopCounter">
                                                    <tr>
                                                        <td class="col-md-1">${loopCounter.count }</td>
                                                        <td class="col-md-2"><c:out value="${taskDetails.itemGrade}"/></td>
                                                        <td><c:out value="${taskDetails.itemOption}"/></td>
                                                    </tr>
                                                </c:forEach>
                                                <!-- *********Task Details list ************ -->
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
            <jsp:param name="page" value="group_details.vw" />
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
            localStorage.setItem('taskDetailsactiveTab', $(e.target).attr('href'));
        });
        
        var activeTab = localStorage.getItem('taskDetailsactiveTab');
        
        if(activeTab)
        {
            $('#taskDetailsTab a[href="' + activeTab + '"]').tab('show');
        }
    });
    </script>
    

</body>
</html>
