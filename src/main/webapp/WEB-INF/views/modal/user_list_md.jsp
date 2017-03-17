<!-- ************************************* -->
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"
    import="model.identity.*,common.utils.system.SystemUtils"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ************************************* -->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title><spring:message code="label.user.list" /></title>
<!-- Data Table -->
<link href="resources/lib/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div id="modalUserList" style="vertical-align: middle;" class="modal fade"  >
        <div class="modal-dialog">
            <div class="modal-content col-md-8">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title"><spring:message code="label.user.list" /></h4>
                </div>
                <!-- /modal-header -->
                <div class="modal-body">
                  <form id="userDataForm" action="${param.submitUrl}" method="POST" role="form" class="form-horizontal" >
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <table id="datatable"
                        class="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>№</th>
                                <th><input type="checkbox" id="check-all" class="flat"></th>
                                <th><spring:message code="label.user.login" /></th>
                                <th><spring:message code="label.user.full_name" /></th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>                      
                  </form>   
                </div>
                <!-- /modal-body -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-default btn-xs" data-dismiss="modal">
                        <spring:message code="label.action.cancel" />
                    </button>
                    <button type="button" class="btn btn-success btn-xs" onclick="submitForm();">
                        <i class="fa fa-plus"></i>&nbsp;&nbsp;<spring:message code="label.action.add" />
                    </button>
                </div>
                <!-- /modal-footer -->
            </div>
            <!-- /modal-content -->
        </div>
        <!-- /modal-dialog -->
    </div>
</body>
<script src="resources/lib/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
<script type="text/javascript">

    $("#userDataForm").submit(function(e)
    {
        var postData = $(this).serializeArray();
        var formURL = $(this).attr("action");
        
        $.ajax(
        {
            url : formURL,
            type: "POST",
            data : postData,
            success:function(data, textStatus, jqXHR) 
            {
            	$('#modalUserList').modal('toggle');
            },
            error: function(jqXHR, textStatus, errorThrown) 
            {
                //if fails      
            }
        });
        
        e.preventDefault(); //STOP default action
    });
    // ----------------------------
    
    function submitForm()
    {
        $("#userDataForm").submit(); 
    }
    // ----------------------------
    
    $(document).ready(function() 
    {
        $('#datatable').dataTable(
        {
        	"language": 
        	{
                "url": "resources/lib/datatables.net/i18n/ru.json"
            },
            "fixedHeader": true,
            "order": [],
            "iDisplayLength": 20,
            "scrollY": "260px",
        	"processing": true,
        	"serverSide": true,
        	"searching" : false,
        	"pagingType" : "full_numbers",
            "paging" : true,
            "lengthChange": true,
            "info" : false,
           
            "ajax": 
            {   "url": "rest/identity/user/list",
            	"type": "GET"
            },
            
            'columnDefs': [{
                'targets': 1,
                "width": "5%" ,
                'searchable': false,
                'orderable': false,
                'render': function (data, type, full, meta)
                {
                    return '<input type="checkbox" name="userIds" value="'+data[0]+'" class="flat">';
                }
             }],
            
            "columns": [
            	{ "data": null , "width": "5%" , 'searchable': false,'orderable': false, 
                    'render': function (data, type, row, meta) 
                    {
                    	return meta.row + meta.settings._iDisplayStart + 1;
                    }
                },
            	{ "data": null},
                { "data": "1", 'orderable': false ,"width": "35%"},
                { "data": null,'orderable': false,
                  "render" : function ( data, type, full ) 
                  { 
                    return full['4']+' '+full['5'];
                  }
                },
              
            ]
    
        });
    });
 
    </script>

</html>
