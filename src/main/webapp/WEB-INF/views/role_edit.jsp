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

<title><spring:message code="label.page.role_edit.title" /></title>

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
<!-- ***************************** -->
<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp"><jsp:param name="page"
                    value="role_details.vw" /></jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp"><jsp:param name="page"
                    value="role_details.vw" /></jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="row">
                        <div class="col-md-9 col-sm-9 col-xs-9">
                            <div class="x_panel">
                            <form id="role" data-parsley-validate action="role_edit.do" 
                                  class="form-horizontal form-label-left" method="POST">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="id" value="${role.id}"/>
                                <div class="x_title">
                                    <h2><spring:message code="label.page.role_edit.title" /></h2>
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
                                    <ul id="roleEditTab" class="nav nav-tabs bar_tabs" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#tab_content1" id="groups-tab" role="tab" data-toggle="tab" aria-expanded="true">
                                            <spring:message code="label.page.role_details.title" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content2" id="permissions-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <i class="fa fa-key">&nbsp;</i>
                                            <spring:message code="label.role.permissions" /></a>
                                        </li>
                                    </ul>
                                    <div id="roleEditTabContent" class="tab-content">
                                        <div id="tab_content1" role="tabpanel" class="tab-pane col-md-9 fade active in" 
                                              aria-labelledby="roles-tab">
                                            <table class="table table-bordered dataTable">
                                              <thead>
                                                <tr>
                                                    <th colspan="2"></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <tr>
                                                  <th scope="row" class="col-md-3">
                                                    <label class="control-label-required" for="role-name">
                                                        <spring:message code="label.role.name" />:</label></th>
                                                  <td class="col-md-6">
                                                    <input type="text" id="role-name" name="name" value="${role.name}"
                                                        class="form-control input-sm" required="required">
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-3">
                                                    <label class="control-label" for="role-desc">
                                                        <spring:message code="label.role.desc" />:</label></th>
                                                  <td class="col-md-6">
                                                    <input type="text" id="role-desc" name="details" value="${role.details}"
                                                        class="form-control input-sm">
                                                  </td>
                                                </tr>
                                              </tbody>
                                            </table>
                                        </div>
                                        <div role="tabpanel" class="tab-pane col-md-10 fade" 
                                             id="tab_content2" aria-labelledby="permissions-tab">
                                            <button id="btnAddRow" class="btn btn-success btn-xs" type="button">
                                                    <i class="fa fa-plus">&nbsp;<spring:message code="label.permission.add" /></i>
                                            </button>
                                            <table id="tbPermissions" class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th><spring:message code="label.permission" /></th>
                                                    <th><spring:message code="label.permission.read" /></th>
                                                    <th><spring:message code="label.permission.write" /></th>
                                                    <th><spring:message code="label.permission.update" /></th>
                                                    <th><spring:message code="label.permission.delete" /></th>
                                                    <th></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <!-- *********Permission list ************ -->
                                                <c:forEach var="permission" items="${role.permissions}" varStatus="mainCounter">
                                                    <tr>
                                                        <td class="col-md-4">
                                                            <select id="selPermObj" class="form-control input-select-sm" 
                                                                name="permissions[${mainCounter.index}].item">
                                                                <c:forEach var="systemAttr" varStatus="loopCounter"
                                                                    items="${SystemUtils.getAttributes('system.attrib.permission')}"> 
                                                                    <option ${permission.item == (loopCounter.index + 1) ? 'selected' : '' } value="${loopCounter.count}">${systemAttr}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td class="col-md-1">
                                                            <input ${permission.read ?'checked':''} type="checkbox" id="chxPermRead" name="permissions[${mainCounter.index}].read">
                                                        </td>
                                                        <td class="col-md-1">
                                                            <input ${permission.write ?'checked':''} type="checkbox" id="chxPermWrite" name="permissions[${mainCounter.index}].write">
                                                        </td>
                                                        <td class="col-md-1">
                                                            <input ${permission.update ?'checked':''} type="checkbox" id="chxPermUpdate" name="permissions[${mainCounter.index}].update">
                                                        </td>
                                                        <td class="col-md-1">
                                                            <input ${permission.delete ?'checked':''} type="checkbox" id="chxPermDelete" name="permissions[${mainCounter.index}].delete">
                                                        </td>
                                                        <td class="col-md-1">
                                                            <button class="btn btn-danger btn-remove btn-xs" type="button">
                                                                <i class="fa fa-close"></i>
                                                            </button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                <!-- *********User list ************ -->
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
            localStorage.setItem('roleEditActiveTab', $(e.target).attr('href'));
        });
        
        var activeTab = localStorage.getItem('roleEditActiveTab');
        
        if(activeTab)
        {
            $('#roleEditTab a[href="' + activeTab + '"]').tab('show');
        }
    });
    </script>
    
    <script type="text/javascript">
    
    $(document).ready(function() 
    {
        //Disable the Remove Button
        var rowCount = $('#tbPermissions >tbody:last >tr').length;
        if(rowCount == 1) 
        {
            document.getElementsByClassName('btn-remove')[0].disabled = true;
        }
        
        $(document).on('click', '#btnAddRow', function(e) 
        {
            e.preventDefault();
            
            var controlForm = $('#tbPermissions');
            var currentEntry = $('#tbPermissions>tbody>tr:last');
            var newEntry = $(currentEntry.clone()).appendTo(controlForm);
            //newEntry.find('input').val('');                                         
            //Remove the Data - as it is cloned from the above
            
            //Add the button  
            var rowCount = $('#tbPermissions >tbody:last >tr').length;
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
            var rowCount = $('#tbPermissions >tbody:last >tr').length;
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
            $('#tbPermissions > tbody  > tr').each(function(rowIndex) 
            {
                $(this).find("#selPermObj").attr('name','permissions['+rowIndex+'].item');
                $(this).find("#chxPermRead").attr('name','permissions['+rowIndex+'].read');
                $(this).find("#chxPermWrite").attr('name','permissions['+rowIndex+'].write');
                $(this).find("#chxPermUpdate").attr('name','permissions['+rowIndex+'].update');
                $(this).find("#chxPermDelete").attr('name','permissions['+rowIndex+'].delete');
            }); 
        }
    
    });    
    </script>

</body>
</html>
