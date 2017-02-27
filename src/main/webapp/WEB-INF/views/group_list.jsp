<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8" 
        errorPage="error.jsp"%>
<%@page import="model.identity.*,
org.springframework.data.domain.Page,
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

<title><spring:message code="label.page.groups.title" /></title>

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
<c:set var="groupsPage" value="${requestScope.groupsPage}"/>
<c:set var="dateTimeFormatShort" value="${SystemUtils.getSettings('system.app.date.time.format.short')}"/>
<!-- ***************************** -->

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp">
                <jsp:param name="page" value="group_list.vw" />
            </jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp">
                <jsp:param name="page" value="group_list.vw" />
            </jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <!--  
					<div class="page-title">
						<div class="title_left">
							<h3>Plain Page</h3>
						</div>
					</div>
					-->

                    <div class="clearfix"></div>

                    <div class="row">
                        <div class="col-md-10 col-sm-10 col-xs-10">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2><spring:message code="label.page.groups.title" /></h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <form id="group_search" data-parsley-validate action="group_list.vw"
                                        class="form-horizontal form-label-left">
                                        <table>
                                            <tr>
                                                <td><label
                                                    class="control-label"
                                                    for="group-name"><spring:message code="label.group.name" />:&nbsp;</label>
                                                </td>
                                                <td><input type="text" name="groupName" value="${param.groupName}"
                                                    id="group-name" class="form-control input-sm">
                                                </td>
                                                <td>&nbsp;&nbsp; <label class="control-label"
                                                    for="last-name"><spring:message code="label.group.member_lastname" />:&nbsp;</label>
                                                </td>
                                                <td><input type="text" value="${param.memberlastName}"
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
                                                <th><spring:message code="label.group.name" /></th>
                                                <th><spring:message code="label.group.date" /></th>
                                                <th><spring:message code="label.group.member_count" /></th>
                                                <th><spring:message code="label.group.desc" /></th>
                                                <th><spring:message code="label.data.status" /></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <!-- *********Group list ************ -->
                                        
                                        <c:set var="index" value="${groupsPage.number * usersPage.size}" />
                                        
                                        <c:forEach var="group" items="${groupsPage.content}" varStatus="loopCounter">
                                            <tr class="${group[2] == 1 ? 'a' : 'danger'}">
                                                <td class="col-md-1">${index + loopCounter.count }</td>
                                                <td><a href="group_details.vw?group_id=${group[0]}">
                                                    <c:out value="${group[1]}"/></a></td>
                                                <td class="col-md-2">
                                                    <fmt:formatDate pattern="${dateTimeFormatShort }" value="${group[4]}" />
                                                </td>
                                                <td class="col-md-1"><c:out value="${group[6]}"/></td>
                                                <td><c:out value="${group[5]}"/></td>
                                                <td class="col-md-1">
                                                    ${SystemUtils.getAttribute('system.attrib.data.status',group[2],locale)}
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <!-- *********Group list ************ -->
                                        </tbody>
                                    </table>
                                    
                                    <!------------- Pagination -------------->
                                    <c:if test="${groupsPage.totalPages > 1}">
                                        <jsp:include page="include/pagination.jsp">
                                             <jsp:param name="page" value="user_list.vw" />
                                             <jsp:param name="totalPages" value="${groupsPage.totalPages}" />
                                             <jsp:param name="totalElements" value="${groupsPage.totalElements}" />
                                             <jsp:param name="currentIndex" value="${groupsPage.number}" />
                                             <jsp:param name="pageableSize" value="${groupsPage.size}" />
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
