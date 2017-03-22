<!-- ************************************* -->
<%@page contentType="text/html charset=UTF-8"
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

<title><spring:message code="label.page.group_edit.title" /></title>

<!-- Bootstrap -->
<link href="resources/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Font Awesome -->
<link href="resources/lib/font-awesome/css/font-awesome.min.css" rel="stylesheet">

<!-- NProgress -->
<link href="resources/lib/nprogress/nprogress.css" rel="stylesheet">

<!-- iCheck -->
<link href="resources/lib/iCheck/skins/flat/green.css" rel="stylesheet">

<!-- Custom Theme Style -->
<link href="resources/css/custom.css" rel="stylesheet">

<!-- Data Table -->
<link href="resources/lib/datatables.net-bs/css/dataTables.bootstrap.css" rel="stylesheet">

</head>
<!-- ***************************** -->
<c:set var="group" value="${requestScope.groupDetails}"/>
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
                        <div class="col-md-8 col-sm-8 col-xs-8">
                            <div class="x_panel">
                            <form id="groupFrom" data-parsley-validate action="group_edit.do" 
                                  class="form-horizontal form-label-left" method="POST">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="id" value="${group.id}"/>
                                <div class="x_title">
                                    <h2><spring:message code="label.page.group_edit.title" /></h2>
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
                                    <ul id="groupEditTab" class="nav nav-tabs bar_tabs" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#tab_content1" id="groups-tab" role="tab" data-toggle="tab" aria-expanded="true">
                                            <spring:message code="label.page.group_details.title" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content2" id="group-users" role="tab" data-toggle="tab" aria-expanded="false">
                                            <spring:message code="label.group.members" /></a>
                                        </li>
                                    </ul>
                                    <div id="groupEditTabContent" class="tab-content">
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
                                                  <th scope="row" class="col-md-3">
                                                    <label class="control-label-required" for="group-name">
                                                    <spring:message code="label.group.name" />:</label></th>
                                                  <td class="col-md-5"><input type="text" id="group-name" name="name" value="${group.name}"
                                                        class="form-control input-sm" required="required">
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-3">
                                                    <label class="control-label" for="group-desc">
                                                    <spring:message code="label.group.desc" />:</label></th>
                                                  <td class="col-md-5"><input type="text" id="group-desc" name="details" value="${group.details}"
                                                        class="form-control input-sm">
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="group-status">
                                                    <spring:message code="label.data.status" />:</label></th>
                                                  <td>
                                                    <select id="group-status" class="form-control input-select-sm" name="status">
                                                        <c:forEach var="systemAttr" varStatus="loopCounter"
                                                            items="${SystemUtils.getAttributes('system.attrib.data.status')}"> 
                                                            <option ${group.status == (loopCounter.count) ? 'selected="selected"' : ''}
                                                            value="${loopCounter.count}">${systemAttr}</option>
                                                        </c:forEach>
                                                    </select>
                                                  </td>
                                                </tr>
                                              </tbody>
                                            </table>
                                        </div>
                                        <div id="tab_content2" role="tabpanel" class="tab-pane col-md-9 fade" 
                                              aria-labelledby="group-users">
                                            <a role="button" class="btn btn-success btn-xs" href="user_list.mvw?submitUrl=rest&#47;group&#47;user&#47;add&#63;group_id=${group.id }"
                                                    id="btnManageGroup" rel="modal">
                                                    <i class="fa fa-plus"></i>&nbsp;
                                                    <spring:message code="label.menu.register_user"/>
                                            </a>
                                            <div class="modal-container"></div>
                                            <table id="datatable" class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th class="col-md-1">№</th>
                                                    <th class="col-md-3"><spring:message code="label.user.name" /></th>
                                                    <th class="col-md-4"><spring:message code="label.user.full_name" /></th>
                                                    <th class="col-md-1"></th>
                                                </tr>
                                              </thead>
                                              <tbody>
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
            <jsp:param name="page" value="group_edit.vw" />
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
    
    <!-- iCheck -->
    <script src="resources/lib/iCheck/icheck.min.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="resources/js/custom.min.js"></script>

    <!-- Dat Tables -->
    <script src="resources/lib/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="resources/lib/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script type="text/javascript">
    
   //-----------------------------

    function getLang()
    {
        var lang = '${pageContext.response.locale }';
        if( lang == "" || lang == "en")
            return "";
        else
            return 'resources/lib/datatables.net/i18n/'+lang+'.json';
    }   
    //----------------------------
    
    var table;
    
    $(document).ready(function()
    {
        $('a[data-toggle="tab"]').on('show.bs.tab', function(e) {
            localStorage.setItem('groupEditActiveTab', $(e.target).attr('href'));
        });
        var activeTab = localStorage.getItem('groupEditActiveTab');
        if(activeTab){
            $('#groupEditTab a[href="' + activeTab + '"]').tab('show');
        }
    });
    
    
    $('a[rel=modal]').on('click', function(evt) 
    {
        evt.preventDefault();
        
        $('.modal-container').load($(this).attr('href'), function (responseText, textStatus) 
        {
            if ( textStatus === 'success' || textStatus === 'notmodified') 
            {
                $("#modalUserList").on("hidden.bs.modal", function (e) 
        	    {   
                	$('#modalUserList').unbind();
                	table.ajax.reload();    
        	    });
                
                $('#modalUserList').modal().show();
            }
        });
    });
    
    
    $(document).on('click', '.btn-remove', function(e) 
    {
    	groupId = ${group.id};
    	userId  = $(this).val();
    	removeUserFromGroup(this,groupId,userId);	
    });
    
    
   //----------------
    function removeUserFromGroup(element , groupId,userId)
    {
    
	    $(element).text(' ... ');
        var sURL = 'rest/group/user/remove?group_id='+groupId+'&user_id='+userId;
        
        $.ajax(
        {
            url:sURL,
            type: 'GET',
            success: function (response) 
            {
            	$(element).parents('tr:first').remove();
            },
            error: function () 
            {
            	$(element).text(' Error !');     
            },
        });
 
    }
    </script>
    <script type="text/javascript">

    $(document).ready(function() 
    {
        var indexRow = 1;
        
    	table = $('#datatable').DataTable(
        {
        	"autoWidth": false,
        	"language": 
            {
                "url": getLang()
            },
            "iDisplayLength": 12,
            "processing": true,
            "serverSide": true,
            "searching" : false,
            "pagingType" : "full_numbers",
            "paging" : true,
            "lengthChange": false,
            "info" : true,
           
            "ajax": 
            {   "url": 'rest/group/user/list?group_id=${group.id}',
                "type": "GET"
            },
            
            'columnDefs': [{
                'targets': 0,
                "width": "5%" ,
                'searchable': false,
                'orderable': false,
                'render': function (data, type, row, meta) {
                    return meta.row + meta.settings._iDisplayStart + 1;
                }
             }],
           
            "columns": [
                { "data": null },
                { "data": "1" ,'orderable': false },
                { "data": null,'orderable': false,
                  "render" : function ( data, type, full ) 
                  { 
                    return full['3']+' '+full['2'];
                  }
                },
                { "data": null,'orderable': false,
                    "render" : function ( data, type, full ) 
                    { 
                      return '<button class="btn btn-danger btn-remove btn-xs btn-td" value="'+data[0]+'"' +
                             'type="button"><i class="fa fa-close"></i></button>';
                    }
                },
            ]
        });
    });
 
    </script>
    

</body>
</html>
