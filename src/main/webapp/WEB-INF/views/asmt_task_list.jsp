<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8" 
        errorPage="error.jsp" %>
<%@page import="model.identity.*,org.springframework.data.domain.Page,
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

<title><spring:message code="label.page.asmt.tasks.title" /></title>

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
<c:set var="tasksPage" value="${requestScope.tasksPage}"/>
<!-- ***************************** -->

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp">
                <jsp:param name="page" value="asmt_task_list.vw" />
            </jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp">
                <jsp:param name="page" value="asmt_task_list.vw" />
            </jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="row">
                        <div class="col-md-11 col-sm-11 col-xs-11">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2><spring:message code="label.page.asmt.tasks.title" /></h2>
                                    <div class="btn-group pull-right">
                                      <a href="asmt_task_register.vw" role="button" class="btn btn-success btn-xs">
                                        <i class="fa fa-plus"></i>&nbsp;
                                                <spring:message code="label.menu.task.register"/></a>
                                      <button type="button" class="btn btn-success btn-xs dropdown-toggle" 
                                        data-toggle="dropdown" aria-expanded="false">
                                        <span class="caret"></span>
                                        <span class="sr-only"></span>
                                      </button>
                                      <ul class="dropdown-menu" role="menu">
                                        
                                        <li><a href="#"><i class="fa fa-upload"></i>&nbsp;
                                                <spring:message code="label.asmt.task.upload"/></a>
                                        </li>
                                        <li class="divider"></li>
                                      </ul>
                                    </div>
                                  <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <form id="user_search" data-parsley-validate action="asmt_task_list.vw"
                                        class="form-horizontal form-label-left">
                                        <table>
                                            <tr>
                                                <td><label
                                                    class="control-label"
                                                    for="task-name"><spring:message code="label.asmt.task.name" />:&nbsp;</label>
                                                </td>
                                                <td><input type="text" id="task-name" name="taskItemName" value="${param.taskItemName}"
                                                    class="form-control input-sm">
                                                </td>
                                                <td>&nbsp;&nbsp; <label class="control-label"
                                                    for="category-name"><spring:message code="label.asmt.task.category" />:&nbsp;</label>
                                                </td>
                                                <td><input type="text" value="${param.categoryName}"
                                                    id="category-name" name="categoryName" class="form-control input-sm">
                                                </td>
                                                <td><label class="control-label"for="mode-type">
                                                    &nbsp;<spring:message code="label.asmt.task.mode.type" />:&nbsp;</label>
                                                </td>
                                                <td><select id="mode-type" class="form-control input-select-sm" name="taskModeType">
                                                        <option value="0"><spring:message code="label.data.all" /></option>
                                                        <c:forEach var="systemAttr" varStatus="loopCounter"
                                                            items="${SystemUtils.getAttributes('system.attrib.task.mode.type',locale)}"> 
                                                            <option ${param.taskModeType == (loopCounter.count) ? 'selected="selected"' : ''}
                                                            value="${loopCounter.count}">${systemAttr}</option>
                                                        </c:forEach>
                                                  </select>
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
                                                <th><spring:message code="label.asmt.task.item.name" /></th>
                                                <th><spring:message code="label.asmt.task.complexity" /></th>
                                                <th><spring:message code="label.asmt.task.mode" /></th>
                                                <th><spring:message code="label.asmt.task.mode.type" /></th>
                                                <th><spring:message code="label.asmt.task.category" /></th>
                                                <th><spring:message code="label.data.status" /></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <!-- *********Task list ************ -->
                                        <c:set var="index" value="${tasksPage.number * tasksPage.size}" />
                                        <c:forEach var="task" items="${tasksPage.content}" varStatus="loopCounter">
                                            <c:choose>
                                                <c:when test="${task[4] == 2}"><c:set var = "status_color" value="info"/></c:when>
                                                <c:when test="${task[4] == 5}"><c:set var = "status_color" value="warning"/></c:when>
                                                <c:otherwise><c:set var = "status_color" value=""/></c:otherwise>
                                            </c:choose>
                                            <tr class="${status_color}">
                                                <td class="col-md-1">${index + loopCounter.count }</td>
                                                <td><a href="asmt_task_details.vw?asmt_task_id=${task[0]}">
                                                    <c:out value="${task[1]}"/></a></td>
                                                <td>${SystemUtils.getAttribute('system.attrib.task.complexity',task[3],locale)}</td>
                                                <td>${SystemUtils.getAttribute('system.attrib.task.mode',task[3],locale)}</td>
                                                <td>${SystemUtils.getAttribute('system.attrib.task.mode.type',task[4],locale)}</td>
                                                <td><a href="asmt_category_details.vw?asmt_category_id=${task[6] }">
                                                    <c:out value="${task[7]}"/></a></td>
                                                <td>${SystemUtils.getAttribute('system.attrib.data.status',task[5],locale)}</td>
                                            </tr>
                                        </c:forEach>
                                        <!-- *********/Task list ************ -->
                                        </tbody>
                                    </table>
                                    
                                    <!------------- Pagination -------------->
                                    <c:if test="${tasksPage.totalPages > 1}">
                                        <jsp:include page="include/pagination.jsp">
                                             <jsp:param name="page" value="asmt_task_list.vw" />
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
        <!-- /page content -->

        <!-- footer content -->
        <jsp:include page="include/footer.jsp">
            <jsp:param name="page" value="asmt_task_list.vw" />
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
