<!-- ************************************* -->
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="error.jsp"
    import="model.assessment.task.*,common.utils.system.SystemUtils"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ************************************* -->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title><spring:message code="label.asmt.task.list" /></title>
<!-- Data Table -->
<link href="resources/lib/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
<style type="text/css" class="init">
.toolbar {
    float: left;
}
</style>
</head>
<body>
    <div id="modalTaskList" style="vertical-align: middle;" class="modal fade"  >
        <div class="modal-dialog">
            <div class="modal-content col-md-12">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title"><spring:message code="label.asmt.task.list" /></h4>
                </div>
                <!-- /modal-header -->
                <div class="modal-body">
                  <form id="taskDataForm" action="${param.submitUrl}" method="POST" role="form" class="form-horizontal" >
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="toolbar"><spring:message code="label.action.search" />:
                     <input type="text" id="inpSearch" value=""/>
                     <button id="btnSearch" type="button" class="btn btn-primary btn-xs">
                        <i class="fa fa-search"></i></button></div>
                     <table id="datatable" class="table table-striped table-bordered">
                         <thead>
                            <tr>
                                <th>№</th>
                                <th><input type="checkbox" id="cxSelectAll" class=""></th>
                                <th><spring:message code="label.asmt.task.item.content" /></th>
                                <th><spring:message code="label.asmt.task.mode.type" /></th>
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
                    <a role="button" class="btn btn-success btn-xs" onclick="submitForm();">
                        <i class="fa fa-plus"></i>&nbsp;&nbsp;<spring:message code="label.action.add" />
                    </a>
                </div>
                <!-- /modal-footer -->
            </div>
            <!-- /modal-content -->
        </div>
        <!-- /modal-dialog -->
    </div>
</body>
<style>
 div.dataTables_filter { display: none !important; }
</style>
<script src="resources/lib/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
<script type="text/javascript">

    //----------------------------
    function getLang()
    {
        var lang = '${pageContext.response.locale }';
        if( lang == "" || lang == "en")
            return "";
        else
            return 'resources/lib/datatables.net/i18n/'+lang+'.json';
    }   
    //----------------------------

    $("#taskDataForm").submit(function(e)
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
            	$('#modalTaskList').modal('toggle');
            },
            error: function(jqXHR, textStatus, errorThrown) 
            {
                //if fails      
            }
        });
        
        
        e.preventDefault(); //STOP default action
        
        return false;
       
    });
    // ----------------------------
    
    function submitForm()
    {
        $("#taskDataForm").submit(); 
    }
    // ----------------------------
    
    $(document).ready(function() 
    {
        var dataTable = $('#datatable').DataTable(
        {
        	"language": 
        	{
                "url": getLang()
            },
            "fixedHeader": true,
            "order": [],
            "iDisplayLength": 12,
            "scrollY": "260px",
        	"processing": true,
        	"serverSide": true,
        	"searching" : true,
        	"pagingType" : "full_numbers",
            "paging" : true,
            "lengthChange": true,
            "info" : false,
            "dom": '<"toolbar"f>rt<"bottom"lp>',
          
           
            "ajax": 
            {   "url": "rest/assessment/task/list",
            	"type": "GET"
            },
            
            
            "columns": [
                { "data": null , "width": "5%" , 'searchable': false,'orderable': false, 
                    'render': function (data, type, row, meta) 
                    {
                        return meta.row + meta.settings._iDisplayStart + 1;
                    }
                },
                { "data": null, "width": "5%" , 'searchable': false,'orderable': false, 
                	'render': function (data, type, full, meta)
                    {
                        return '<input type="checkbox" name="taskIds" value="'+data['id']+'" class="cxinput">';
                    }
                },
                { "data": null ,"width": "70%", 'orderable': false, 'searchable': false,
                    'render': function (data, type, row, meta) 
                    {
                        return '<a href="asmt_task_details.vw?asmt_task_id='+data['id']+'">'+data['itemContent']+'</a>';
                    }
                },
                { "data": "modeTypeName", 'orderable': false ,"width": "20%" ,'searchable': false}
            ],
            "createdRow": function( row, data, dataIndex ) 
            {
                if ( data["modeType"] == 2 ) 
                {
                	$(row).addClass( 'info' );
                }
                else if ( data["modeType"] == 4 ) 
                {
                  $(row).addClass( 'warning' );
                }
            },
        });
        
        $('.dataTables_filter').hide();
        $('#btnSearch').click(function()
        {
        	dataTable.search( $('#inpSearch').val() ).draw();
        });

       
    });
    
    </script>
    <script src="resources/js/checkbox.js"></script>

</html>
