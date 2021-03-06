<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8" 
        errorPage="error.jsp" 
        
        import="model.identity.*,common.utils.system.SystemUtils"%>

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

<title><spring:message code="label.page.group_details.title" /></title>

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
<c:set var="group" value="${requestScope.groupDetails}"/>
<c:set var="usersPage" value="${requestScope.usersPage}"/>
<c:set var="dateTimeFormatShort" value="${SystemUtils.getSettings('system.app.date.time.format.short')}"/>
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
                        <div class="col-md-9 col-sm-9 col-xs-9">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2><spring:message code="label.page.group_details.title" /></h2>
                                     <div class="pull-right">
                                        <a href="group_edit.vw?group_id=${param.group_id}" role="button" class="btn btn-info btn-xs">
                                            <i class="fa fa-pencil-square-o"></i>&nbsp;
                                                <spring:message code="label.action.edit"/>
                                        </a>
                                        <a href="group_remove.do?group_id=${param.group_id}"  role="button" class="btn btn-danger btn-xs">
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
                                    <ul id="groupDetailsTab" class="nav nav-tabs bar_tabs" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#tab_content1" id="groups-tab" role="tab" data-toggle="tab" aria-expanded="true">
                                            <spring:message code="label.page.group_details.title" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content2" id="users-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <i class="fa fa-group">&nbsp;</i>
                                            <spring:message code="label.group.members" /></a>
                                        </li>
                                    </ul>
                                    <div id="groupDetailsTabContent" class="tab-content">
                                        <div id="tab_content1" role="tabpanel" class="tab-pane col-md-10 fade active in" 
                                              aria-labelledby="groups-tab">
                                            <table class="table table-bordered dataTable">
                                              <thead>
                                                <tr>
                                                    <th colspan="2"></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <tr>
                                                  <th scope="row" class="col-md-3"><spring:message code="label.group.name" />:</th>
                                                  <td class="col-md-5"><c:out value="${group.name}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.group.date" />:</th>
                                                  <td><fmt:formatDate pattern="${dateTimeFormatShort }" value="${group.created}" />
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.group.desc" />:</th>
                                                  <td><c:out value="${group.details}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.data.status" />:</th>
                                                    <td class="${group.status == 1 ? 'a' : 'danger'}">
                                                    ${SystemUtils.getAttribute('system.attrib.data.status', group.status)}
                                                    </td>
                                                </tr>
                                              </tbody>
                                            </table>
                                        </div>
                                        
                                        <div id="tab_content2" role="tabpanel" class="tab-pane col-md-10 fade" 
                                              aria-labelledby="users-tab">
                                           <table id="" class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th>№</th>
                                                    <th><spring:message code="label.user.name" /></th>
                                                    <th><spring:message code="label.user.full_name" /></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <!-- *********User list ************ -->
                                                <c:set var="index" value="${usersPage.number * usersPage.size}" />
                                                <c:forEach var="user" items="${usersPage.content}" varStatus="loopCounter">
                                                    <tr>
                                                        <td class="col-md-1">${index + loopCounter.count }</td>
                                                        <td><a href="user_details.vw?user_id=${user[0]}">
                                                            <c:out value="${user[1]}"/></a></td>
                                                        <td><c:out value="${user[3]}"/>&nbsp;<c:out value="${user[2]}"/></td>
                                                    </tr>
                                                </c:forEach>
                                                <!-- *********User list ************ -->
                                              </tbody>
                                            </table>
                                             <!------------- Pagination -------------->
                                            <c:if test="${usersPage.totalPages > 1}">
                                                <jsp:include page="include/pagination.jsp">
                                                     <jsp:param name="page" value="user_details.vw" />
                                                     <jsp:param name="addParam" value="group_id=${param.group_id}" />
                                                     <jsp:param name="totalPages" value="${usersPage.totalPages}" />
                                                     <jsp:param name="totalElements" value="${usersPage.totalElements}" />
                                                     <jsp:param name="currentIndex" value="${usersPage.number}" />
                                                     <jsp:param name="pageableSize" value="${usersPage.size}" />
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
            localStorage.setItem('groupDetailsactiveTab', $(e.target).attr('href'));
        });
        var activeTab = localStorage.getItem('groupDetailsactiveTab');
        if(activeTab){
            $('#groupDetailsTab a[href="' + activeTab + '"]').tab('show');
        }
    });
    </script>
    

</body>
</html>
