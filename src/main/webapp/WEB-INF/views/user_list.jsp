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

<title><spring:message code="label.page.users.title" /></title>

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
<c:set var="usersPage" value="${requestScope.usersPage}"/>
<c:set var="dateTimeFormatShort" value="${SystemUtils.getSettings('system.app.date.time.format.short')}"/>
<!-- ***************************** -->

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp">
                <jsp:param name="page" value="user_list.vw" />
            </jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp">
                <jsp:param name="page" value="user_list.vw" />
            </jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="row">
                        <div class="col-md-10 col-sm-10 col-xs-10">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2><spring:message code="label.page.users.title" /></h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <form id="user_search" data-parsley-validate action="user_list.vw"
                                        class="form-horizontal form-label-left ">
                                        <table>
                                            <tr>
                                                <td><label
                                                    class="control-label"
                                                    for="login-name"><spring:message code="label.user.login" />:&nbsp;</label>
                                                </td>
                                                <td><input type="text" name="userName" value="${param.userName}"
                                                    id="first-name" class="form-control input-sm">
                                                </td>
                                                <td>&nbsp;&nbsp; <label class="control-label"
                                                    for="last-name"><spring:message code="label.user.last_name" />:&nbsp;</label>
                                                </td>
                                                <td><input type="text" value="${param.lastName}"
                                                    id="last-name" name="lastName" class="form-control input-sm">
                                                </td>
                                                <td>&nbsp;&nbsp;
                                                    <button type="submit"
                                                        class="btn btn-primary btn-xs">
                                                        <i class="fa fa-search"></i>
                                                        <spring:message code="label.action.search" />
                                                    </button>
                                                </td>
                                            </tr>
                                        </table>
                                    </form>

                                    <table id="datatable"
                                        class="table table-striped table-bordered">
                                        <thead>
                                            <tr>
                                                <th>№</th>
                                                <th><spring:message code="label.user.login" /></th>
                                                <th><spring:message code="label.user.full_name" /></th>
                                                <th><spring:message code="label.user.last_login" /></th>
                                                <th><spring:message code="label.data.status" /></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <!-- *********User list ************ -->
                                        <c:set var="index" value="${usersPage.number * usersPage.size}" />
                                        <c:forEach var="user" items="${usersPage.content}" varStatus="loopCounter">
                                            <tr class="${user[3] == 1 ? 'a' : 'danger'}">
                                                <td class="col-md-1">${index + loopCounter.count }</td>
                                                <td><a href="user_details.vw?user_id=${user[0]}">
                                                    <c:out value="${user[1]}"/></a></td>
                                                <td><c:out value="${user[5]}"/>&nbsp;<c:out value="${user[4]}"/></td>
                                                <td class="col-md-3">
                                                <fmt:formatDate pattern="${dateTimeFormatShort }" value="${user[2]}" />
                                                </td>
                                                <td class="col-md-2">
                                                   ${SystemUtils.getAttribute('system.attrib.data.status',user[3],locale)}
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <!-- *********User list ************ -->
                                        </tbody>
                                    </table>
                                    
                                    <!------------- Pagination -------------->
                                    <c:if test="${usersPage.totalPages > 1}">
                                        <jsp:include page="include/pagination.jsp">
                                             <jsp:param name="page" value="user_list.vw" />
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
        <!-- /page content -->

        <!-- footer content -->
        <jsp:include page="include/footer.jsp">
            <jsp:param name="page" value="user_list" />
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
