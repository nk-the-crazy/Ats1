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

<title><spring:message code="label.page.organizations.title" /></title>

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
<c:set var="organizationsPage" value="${requestScope.organizationsPage}"/>
<!-- ***************************** -->

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp">
                <jsp:param name="page" value="organizations_list.vw" />
            </jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp">
                <jsp:param name="page" value="uorganizations_list.vw" />
            </jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="clearfix"></div>
                    <div class="row">
                        <div class="col-md-10 col-sm-10 col-xs-10">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2><spring:message code="label.page.organizations.title" /></h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <form id="user_search" data-parsley-validate action="organization_list.vw"
                                        class="form-horizontal form-label-left">
                                        <table>
                                            <tr>
                                                <td><label
                                                    class="control-label"
                                                    for="organization-name"><spring:message code="label.organization.name" />:&nbsp;</label>
                                                </td>
                                                <td><input type="text" name="organizationName" value="${param.organizationName}"
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
                                                <th><spring:message code="label.organization.name" /></th>
                                                <th><spring:message code="label.organization.type" /></th>
                                                <th><spring:message code="label.data.status" /></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <!-- *********User list ************ -->
                                        
                                        <c:set var="index" value="${organizationsPage.number * organizationsPage.size}" />
                                        
                                        <c:forEach var="organization" items="${organizationsPage.content}" varStatus="loopCounter">
                                            <tr class="${organization.status == 1 ? 'a' : 'danger'}">
                                                <td class="col-md-1">${index + loopCounter.count }</td>
                                                <td><a href="organization_details.vw?organization_id=${organization.id}">
                                                    <c:out value="${organization.name}"/></a>
                                                </td>
                                                <td class="col-md-2">
                                                   ${SystemUtils.getAttribute('system.attrib.organization.type',organization.type, locale)}
                                                </td>
                                                <td class="col-md-2">
                                                   ${SystemUtils.getAttribute('system.attrib.data.status',organization.status, locale)}
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <!-- *********User list ************ -->
                                        </tbody>
                                    </table>
                                    
                                    <!------------- Pagination -------------->
                                    <c:if test="${organizationsPage.totalPages > 1}">
                                        <jsp:include page="include/pagination.jsp">
                                             <jsp:param name="page" value="user_list.vw" />
                                             <jsp:param name="totalPages" value="${organizationsPage.totalPages}" />
                                             <jsp:param name="totalElements" value="${organizationsPage.totalElements}" />
                                             <jsp:param name="currentIndex" value="${organizationsPage.number}" />
                                             <jsp:param name="pageableSize" value="${organizationsPage.size}" />
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
				"info" : false,
				"language": 
                {
                    "emptyTable": '<spring:message code="message.info.records.notfound" />'
                },

			});
		});
	</script>
</body>
</html>
