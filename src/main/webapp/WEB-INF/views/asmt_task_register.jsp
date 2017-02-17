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

<title><spring:message code="label.page.asmt.task_register.title" /></title>

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
                             <form id="task" data-parsley-validate action="asmt_task_register.do" 
                                  class="form-vertical form-label-left" method="POST">
                                <div class="x_title">
                                    <h2><spring:message code="label.page.asmt.task_register.title" /></h2>
                                     <div style="text-align: right;">
                                        <button type="submit" class="btn btn-success btn-xs">
                                            <i class="fa fa-floppy-o"></i>&nbsp;
                                                <spring:message code="label.action.save"/>
                                        </button>
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
                                    <ul id="taskRegisterTab" class="nav nav-tabs bar_tabs" role="tablist">
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
                                    <div id="taskRegisterTabContent" class="tab-content">
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
                                                  <th scope="row" class="col-md-3"><label class="control-label" for="item-name">
                                                    <spring:message code="label.asmt.task.name" />:</label></th>
                                                  <td class="col-md-7"><input type="text" id="item-name" name="itemName" value="${task.itemName}"
                                                        class="form-control input-sm" required="required"></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="grade">
                                                    <spring:message code="label.asmt.task.item.grade" />:</label></th>
                                                  <td><input type="text" id="item-name" name="itemGrade" value="${task.itemGrade}"
                                                        class="form-control input-sm"></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="item-content">
                                                    <spring:message code="label.asmt.task.item.content" />:</label></th>
                                                  <td><textarea id="item-content" name="itemContent" rows="4" 
                                                       class="resizable_textarea form-control">${task.itemContent}</textarea></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="item-complexity">
                                                    <spring:message code="label.asmt.task.complexity" />:</label></th>
                                                  <td>
                                                    <select id="item-complexity" class="form-control input-select-sm" name="complexity">
                                                        <c:forEach var="systemAttr" varStatus="loopCounter"
                                                            items="${SystemUtils.getAttributes('system.attrib.task.complexity',locale)}"> 
                                                            <option ${task.complexity == (loopCounter.count) ? 'selected="selected"' : ''}
                                                            value="${loopCounter.count}">${systemAttr}</option>
                                                        </c:forEach>
                                                    </select>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="item-mode">
                                                    <spring:message code="label.asmt.task.mode" />:</label></th>
                                                  <td>
                                                    <select id="item-mode" class="form-control input-select-sm" name="mode">
                                                        <c:forEach var="systemAttr" varStatus="loopCounter"
                                                            items="${SystemUtils.getAttributes('system.attrib.task.mode',locale)}"> 
                                                            <option ${task.mode == (loopCounter.count) ? 'selected="selected"' : ''}
                                                            value="${loopCounter.count}">${systemAttr}</option>
                                                        </c:forEach>
                                                    </select>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="item-mode-type">
                                                    <spring:message code="label.asmt.task.mode.type" />:</label></th>
                                                  <td>
                                                    <select id="item-mode-type" class="form-control input-select-sm" name="modeType">
                                                        <c:forEach var="systemAttr" varStatus="loopCounter"
                                                            items="${SystemUtils.getAttributes('system.attrib.task.mode.type',locale)}"> 
                                                            <option ${task.modeType == (loopCounter.count) ? 'selected="selected"' : ''}
                                                            value="${loopCounter.count}">${systemAttr}</option>
                                                        </c:forEach>
                                                    </select>
                                                  </td>
                                                </tr>
                                                <%-- 
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.asmt.task.category.name" />:</th>
                                                  <td class="col-lg-3"><a href="asmt_category_details.vw?asmt_category_id=${task.category.id }">
                                                  <c:out value="${task.category.name}"/></a></td>
                                                </tr>
                                                --%>
                                              </tbody>
                                            </table>
                                        </div>
                                        
                                        <div role="tabpanel" class="tab-pane col-md-10 fade" 
                                             id="tab_content2" aria-labelledby="task-details-tab">
                                            <button id="btnAddRow" class="btn btn-success btn-xs" type="button">
                                                    <i class="fa fa-plus">&nbsp;<spring:message code="label.asmt.task.item.option.register" /></i>
                                            </button>
                                            <table id="tbTaskOptions" class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th>№</th>
                                                    <th><spring:message code="label.asmt.task.item.grade" />(%)</th>
                                                    <th><spring:message code="label.asmt.task.item.option" /></th>
                                                    <th></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <tr>
                                                    <td id="dvIndex" class="col-md-1">1</td>
                                                    <td class="col-md-2">
                                                        <input class="form-control input-sm" type="text" id="txItemOptionGrade" name="options[0].itemOptionGrade"></td>
                                                    <td><input class="form-control input-sm" type="text" id="txItemOption" name="options[0].itemOption"></td>
                                                    <td><button class="btn btn-danger btn-remove btn-xs" type="button">
                                                                <i class="fa fa-close"></i>
                                                        </button>
                                                    </td>
                                                </tr>
                                              </tbody>
                                            </table>
                                        </div>
                                    </div>
                                  </div>
                               </div>
                               </form>
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
            localStorage.setItem('taskRegisterActiveTab', $(e.target).attr('href'));
        });
        
        var activeTab = localStorage.getItem('taskRegisterActiveTab');
        
        if(activeTab)
        {
            $('#taskRegisterTab a[href="' + activeTab + '"]').tab('show');
        }
    });
    </script>
        <script type="text/javascript">
    
    $(document).ready(function() 
    {
        //Disable the Remove Button
        var rowCount = $('#tbTaskOptions > tbody:last > tr').length;
        if(rowCount == 1) 
        {
            document.getElementsByClassName('btn-remove')[0].disabled = true;
        }
        
        $(document).on('click', '#btnAddRow', function(e) 
        {
            e.preventDefault();
            
            var controlForm = $('#tbTaskOptions');
            var currentEntry = $('#tbTaskOptions > tbody > tr:last');
            var newEntry = $(currentEntry.clone()).appendTo(controlForm);
            //newEntry.find('input').val('');                                         
            //Remove the Data - as it is cloned from the above
            
            //Add the button  
            var rowCount = $('#tbTaskOptions > tbody:last > tr').length;
            if(rowCount > 1) 
            {
                var removeButtons = document.getElementsByClassName('btn-remove');
                for(var i = 0; i < removeButtons.length; i++) 
                {
                    removeButtons.item(i).disabled = false;
                }
            }
            
            updateRowIndex();
             
        }).on('click', '.btn-remove', function(e) 
        {
            $(this).parents('tr:first').remove();
            
            //Disable the Remove Button
            var rowCount = $('#tbTaskOptions >tbody:last >tr').length;
            if(rowCount == 1) 
            {
                document.getElementsByClassName('btn-remove')[0].disabled = true;
            }
    
            e.preventDefault();
            
            updateRowIndex();
            
            return false;
        });
        
        
        function updateRowIndex()
        {
            $('#tbTaskOptions > tbody  > tr').each(function(rowIndex) 
            {
            	$(this).find("#dvIndex").html(rowIndex + 1);
            	$(this).find("#txItemOptionGrade").attr('name','options['+rowIndex+'].itemOptionGrade');
                $(this).find("#txItemOption").attr('name','options['+rowIndex+'].itemOption');
            }); 
        }

    });    
    </script>

</body>
</html>