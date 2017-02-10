<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8" 
        errorPage="error.jsp" %>
<%@page import="model.identity.*,
org.springframework.data.domain.Page,
common.utils.system.SystemUtils"%>

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

<title><spring:message code="label.page.roles.title" /></title>

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
<c:set var="role" value="${requestScope.roleDetails}"/>
<c:set var="usersPage" value="${requestScope.usersPage}"/>
<!-- ***************************** -->

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp">
                <jsp:param name="page" value="role_list.vw" />
            </jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp">
                <jsp:param name="page" value="role_list.vw" />
            </jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="clearfix"></div>
                    <div class="row">
                        <div class="col-md-8 col-sm-8 col-xs-8">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2><spring:message code="label.page.roles.title" /></h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <form id="user_search" data-parsley-validate action="role_list.vw"
                                        class="form-horizontal form-label-left">
                                        <table>
                                            <tr>
                                                <td><label
                                                    class="control-label"
                                                    for="organization-name"><spring:message code="label.role.name" />:&nbsp;</label>
                                                </td>
                                                <td><input type="text" name="roleName" value="${param.roleName}"
                                                    id="organization-name" class="form-control input-sm">
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
                                                <th><spring:message code="label.role.name" /></th>
                                                <th><spring:message code="label.role.desc" /></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <!-- *********Role list ************ -->
                                        
                                        <c:set var="index" value="${rolesPage.number * rolesPage.size}" />
                                        
                                        <c:forEach var="role" items="${rolesPage.content}" varStatus="loopCounter">
                                            <tr>
                                                <td class="col-md-1">${index + loopCounter.count }</td>
                                                <td class="col-md-3"><a href="role_details.vw?role_id=${role.id}">
                                                    <c:out value="${role.name}"/></a>
                                                </td>
                                                <td class="col-md-3"><c:out value="${role.details}"/></td>
                                            </tr>
                                        </c:forEach>
                                        <!-- *********/Role list ************ -->
                                        </tbody>
                                    </table>
                                    
                                    <!------------- Pagination -------------->
                                    <c:if test="${rolesPage.totalPages > 1}">
                                        <jsp:include page="include/pagination.jsp">
                                             <jsp:param name="page" value="role_list.vw" />
                                             <jsp:param name="totalPages" value="${rolesPage.totalPages}" />
                                             <jsp:param name="totalElements" value="${rolesPage.totalElements}" />
                                             <jsp:param name="currentIndex" value="${rolesPage.number}" />
                                             <jsp:param name="pageableSize" value="${rolesPage.size}" />
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
