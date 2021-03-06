<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8" 
        errorPage="error.jsp" 
        import="model.identity.*,common.utils.system.SystemUtils"%>

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

<title><spring:message code="label.page.asmt.category_details.title" /></title>

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
<c:set var="category" value="${requestScope.categoryDetails}"/>
<c:set var="tasksPage" value="${requestScope.categoryTasks}"/>
<!-- ***************************** -->
<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp"><jsp:param name="page"
                    value="asmt_category_details.vw" /></jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp"><jsp:param name="page"
                    value="asmt_category_details.vw" /></jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="row">
                        <div class="col-md-8 col-sm-8 col-xs-8">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2><spring:message code="label.page.asmt.category_details.title" /></h2>
                                     <div class="pull-right">
                                        <a href="asmt_category_register.vw?asmt_category_id=${category.id}" 
                                            role="button" class="btn btn-success btn-xs">
                                            <i class="fa fa-plus"></i>&nbsp;
                                                <spring:message code="label.menu.task.category.register"/>
                                        </a>
                                        <a href="asmt_category_edit.vw?asmt_category_id=${category.id}" role="button" class="btn btn-info btn-xs">
                                            <i class="fa fa-pencil-square-o"></i>&nbsp;
                                                <spring:message code="label.action.edit"/>
                                        </a>
                                        <a href="asmt_category_remove.do?asmt_category_id=${category.id}"  role="button" class="btn btn-danger btn-xs">
                                            <i class="fa fa-close"></i>&nbsp;
                                                <spring:message code="label.action.remove"/>
                                        </a>
                                        <button type="button" class="btn btn-primary btn-xs" onclick="window.history.back();">
                                            <i class="fa fa-chevron-left"></i>&nbsp;
                                                <spring:message code="label.action.back"/>
                                        </button>
                                      </div>
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
                                <div class="" role="tabpanel" data-example-id="togglable-tabs">
                                    <ul id="categoryDetailsTab" class="nav nav-tabs bar_tabs" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#tab_content1" id="category-tab" role="tab" data-toggle="tab" aria-expanded="true">
                                            <spring:message code="label.page.asmt.category_details.title" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content2" id="tasks-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <i class="fa fa-cube">&nbsp;</i>
                                            <spring:message code="label.asmt.task.list" /></a>
                                        </li>
                                    </ul>
                                    <div id="categoryDetailsTabContent" class="tab-content">
                                        <div id="tab_content1" role="tabpanel" class="tab-pane col-md-8 fade active in" 
                                              aria-labelledby="category-tab">
                                            <table class="table table-bordered dataTable">
                                              <thead>
                                                <tr>
                                                    <th colspan="2"></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <tr>
                                                  <th scope="row" class="col-md-3"><spring:message code="label.asmt.task.category.name" />:</th>
                                                  <td class="col-md-5"><c:out value="${category.name}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.asmt.task.category.desc" />:</th>
                                                  <td><c:out value="${category.details}"/></td>
                                                </tr>
                                              </tbody>
                                            </table>
                                        </div>
                                        
                                        <div role="tabpanel" class="tab-pane col-md-12 fade" 
                                             id="tab_content2" aria-labelledby="tasks-tab">
                                            
                                            <table id="" class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th>№</th>
                                                    <th><spring:message code="label.asmt.task.item.name" /></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <!-- *********Task list ************ -->
                                                <c:set var="index" value="${tasksPage.number * tasksPage.size}" />
                                                <c:forEach var="task" items="${tasksPage.content}" varStatus="loopCounter">
                                                    <tr>
                                                        <td class="col-md-1">${index + loopCounter.count }</td>
                                                        <td><a href="asmt_task_details.vw?asmt_task_id=${task[1]}">
                                                            <c:out value="${task[2]}"/></a></td>
                                                    </tr>
                                                </c:forEach>
                                                <!-- *********/Task list ************ -->
                                              </tbody>
                                            </table>
                                             <!------------- Pagination -------------->
                                            <c:if test="${tasksPage.totalPages > 1}">
                                                <jsp:include page="include/pagination.jsp">
                                                     <jsp:param name="page" value="asmt_category_details.vw" />
                                                     <jsp:param name="addParam" value="asmt_category_id=${param.asmt_category_id}" />
                                                     <jsp:param name="totalPages" value="${tasksPage.totalPages}" />
                                                     <jsp:param name="totalElements" value="${tasksPage.totalElements}" />
                                                     <jsp:param name="currentIndex" value="${tasksPage.number}" />
                                                     <jsp:param name="pageableSize" value="${tasksPage.size}" />
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
                </div>
            </div>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <jsp:include page="include/footer.jsp">
            <jsp:param name="page" value="asmt_category_details.vw" />
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
            localStorage.setItem('categoryDetailsactiveTab', $(e.target).attr('href'));
        });
        
        var activeTab = localStorage.getItem('categoryDetailsactiveTab');
        
        if(activeTab)
        {
            $('#categoryDetailsTab a[href="' + activeTab + '"]').tab('show');
        }
    });
    </script>
    

</body>
</html>
