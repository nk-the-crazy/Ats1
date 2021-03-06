<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8" 
        errorPage="error.jsp" 
        import="model.identity.*,model.assessment.*, common.utils.system.SystemUtils"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- ************************************* -->

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title><spring:message code="label.page.asmt_edit.title" /></title>

<!-- Bootstrap -->
<link href="resources/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Font Awesome -->
<link href="resources/lib/font-awesome/css/font-awesome.min.css" rel="stylesheet">

<!-- NProgress -->
<link href="resources/lib/nprogress/nprogress.css" rel="stylesheet">

<!-- bootstrap-duallistbox -->
<link href="resources/lib/bootstrap-duallistbox/dist/duallistbox.css" rel="stylesheet">

<!-- Custom Theme Style -->
<link href="resources/css/custom.css" rel="stylesheet">

<!-- Data Table -->
<link href="resources/lib/datatables.net-bs/css/dataTables.bootstrap.css"
    rel="stylesheet">

</head>
<!-- ***************************** -->
<c:set var="assessment" value="${requestScope.assessmentDetails}"/>
<c:set var="groups" value="${requestScope.groupShortList}"/>
<c:set var="dateFormatShort" value="${SystemUtils.getSettings('system.app.date.format.short')}"/>
<!-- ***************************** -->

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp"><jsp:param name="page"
                    value="asmt_test_edit.vw" /></jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp"><jsp:param name="page"
                    value="asmt_test_edit.vw" /></jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="row">
                        <div class="col-md-11 col-sm-11 col-xs-11">
                            <div class="x_panel">
                             <form id="formAssessment" name="assessment" data-parsley-validate action="asmt_test_edit.do" 
                                  class="form-horizontal form-label-left" method="POST">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="id" value="${assessment.id}"/>
                                <div class="x_title">
                                    <h2><spring:message code="label.page.asmt_edit.title" /></h2>
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
                                    <ul id="assessmentEditTab" class="nav nav-tabs bar_tabs" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#tab_content1" id="assessment-tab" role="tab" data-toggle="tab" aria-expanded="true">
                                            <i class="fa fa-graduation-cap">&nbsp;</i>
                                            <spring:message code="label.assessment" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content2" id="participants-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <i class="fa fa-users">&nbsp;</i>
                                            <spring:message code="label.assessment.participants" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content3" id="tasks-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <i class="fa fa-cube">&nbsp;</i>
                                            <spring:message code="label.asmt.task.list" /></a>
                                        </li>
                                    </ul>
                                    <div id="assessmentRegisterTabContent" class="tab-content">
                                        <div id="tab_content1" role="tabpanel" class="tab-pane col-md-8 fade active in" 
                                              aria-labelledby="assessment-tab">
                                            <table class="table table-bordered dataTable">
                                              <thead>
                                                <tr>
                                                    <th colspan="2">
                                                    <spring:message code="label.assessment" /></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <tr>
                                                  <th scope="row" class="col-md-3">
                                                    <label class="control-label-required" for="assessment-name">
                                                    <spring:message code="label.assessment.name" />:</label></th>
                                                  <td class="col-md-5"><input type="text" id="assessment-name" name="name" value="${assessment.name}"
                                                        class="form-control input-sm" required="required">
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="assessment-type">
                                                  <spring:message code="label.assessment.type" />:</label></th>
                                                  <td><div class="col-md-10 col-sm-10 col-xs-10">
                                                    <select id="assessment-type" class="form-control input-select-sm" name="type">
                                                        <c:forEach var="systemAttr" varStatus="loopCounter"
                                                            items="${SystemUtils.getAttributes('system.attrib.assessment.type')}"> 
                                                            <option ${assessment.type == (loopCounter.count) ? 'selected="selected"' : ''}
                                                            value="${loopCounter.count}">${systemAttr}</option>
                                                        </c:forEach>
                                                    </select></div>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-3">
                                                    <label class="control-label" for="assessment-taskcount">
                                                    <spring:message code="label.asmt.task.count" />:</label></th>
                                                  <td class="col-md-5"><input type="text" id="assessment-taskcount" name="taskCount" 
                                                      value="${empty assessment.taskCount ? '100' : assessment.taskCount}"
                                                      class="form-control input-sm" required="required">
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-3">
                                                    <label class="control-label" for="assessment-maxgrade">
                                                    <spring:message code="label.assessment.maxgrade" />:</label></th>
                                                  <td class="col-md-5"><input type="text" id="assessment-maxgrade" name="maxgrade" 
                                                      value="${empty assessment.maxGrade ? '100' : assessment.maxGrade}"
                                                      class="form-control input-sm" required="required">
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="assessment-type">
                                                  <spring:message code="label.assessment.eval.method" />:</label></th>
                                                  <td><div class="col-md-10 col-sm-10 col-xs-10">
                                                    <select id="assessment-type" class="form-control input-select-sm" name="type">
                                                        <c:forEach var="systemAttr" varStatus="loopCounter"
                                                            items="${SystemUtils.getAttributes('system.attrib.assessment.eval.method')}"> 
                                                            <option ${assessment.evaluationMethod == (loopCounter.count) ? 'selected="selected"' : ''}
                                                            value="${loopCounter.count}">${systemAttr}</option>
                                                        </c:forEach>
                                                    </select></div>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-3">
                                                    <label class="control-label-required" for="assessment-time">
                                                    <spring:message code="label.date.time" /> (<spring:message code="label.date.time.minutes" />):
                                                    </label></th>
                                                  <td class="col-md-5"><input type="text" id="assessment-time" name="time" 
                                                      value="${empty assessment.time ? '10' : assessment.time}"
                                                        class="form-control input-sm" required="required">
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="start-date">
                                                  <spring:message code="label.date.start" />:</label></th>
                                                  <td>
                                                    <div class="col-md-6 col-sm-6 col-xs-6">
                                                        <fmt:formatDate pattern="${dateFormatShort }" value="${assessment.startDate}" var="sDate"/>
                                                        <input id="start-date" type="text" class="date-picker form-control input-sm" 
                                                               name="startDate" value="${sDate}" />
                                                    </div>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="end-date">
                                                  <spring:message code="label.date.end" />:</label></th>
                                                  <td>
                                                    <div class="col-md-6 col-sm-6 col-xs-6">
                                                        <fmt:formatDate pattern="${dateFormatShort }" value="${assessment.endDate}" var="eDate"/>
                                                        <input id="end-date" type="text" class="date-picker form-control input-sm" 
                                                               name="endDate" value="${eDate}" />
                                                    </div>
                                                  </td>
                                                </tr>
                                              </tbody>
                                            </table>
                                        </div>
                                        <div id="tab_content2" role="tabpanel" class="tab-pane fade col-md-8" aria-labelledby="participants-tab">
                                             <c:set var="selectedItemCount" value="${fn:length(assessment.participants)}"/>
                                             <c:set var="selectedItemIndex" value="0"/>
                                             
                                             <div class="bootstrap-duallistbox-container">
                                                <select id="mselParticipants" multiple="multiple" class="groupDualBox" size="8" name="">
                                                <c:forEach var="group" items="${groups}" varStatus="loopCounter">
                                                    <c:set var="selected" value=""/>
                                                    <c:if test="${selectedItemIndex < selectedItemCount }">
                                                        <c:forEach var="selectedItem" items="${assessment.participants}">
                                                            <c:if test="${selectedItem.id == group[0]}">
                                                                <c:set var="selected" value="selected"/>
                                                                <c:set var="selectedItemIndex" value="${selectedItemIndex + 1 }"/>
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:if>
                                                    <option ${selected} value="${group[0]}">${group[1]}</option>
                                                  </c:forEach>
                                                </select>
                                               </div>                                         
                                        </div>
                                        <div role="tabpanel" class="tab-pane fade col-md-10" id="tab_content3"  aria-labelledby="tasks-tab">
                                            <a role="button" class="btn btn-success btn-xs" href="asmt_task_list.mvw?submitUrl=rest&#47;assessment&#47;test&#47;task&#47;add&#63;assessment_id=${assessment.id }"
                                                    id="btnAddTasks" rel="modal">
                                                    <i class="fa fa-plus"></i>&nbsp;
                                                    <spring:message code="label.menu.task.register"/>
                                            </a>
                                            <div class="modal-container"></div><br>
                                            <table id="datatable" class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th>№</th>
                                                    <th><spring:message code="label.asmt.task.item.content" /></th>
                                                    <th><spring:message code="label.asmt.task.mode.type" /></th>
                                                    <th></th>
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
            <jsp:param name="page" value="asmt_test_edit.vw" />
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

    <!-- Bootbox-->
    <script src="resources/lib/bootbox/js/bootbox.min.js"></script>
    
    <!-- Custom Theme Scripts -->
    <script src="resources/js/custom.min.js"></script>

    <!-- Dat Tables -->
    <script src="resources/lib/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="resources/lib/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <!-- bootstrap-duallistbox -->
    <script src="resources/lib/bootstrap-duallistbox/dist/duallistbox.min.js"></script>
    <script type="text/javascript">
    //-----------------------------
    $(document).ready(function()
    {
        $('#formAssessment').on('submit', function(e)
        {
        	e.preventDefault();
            createHiddenElements(this);
            this.submit();
        });
    });
    
    function createHiddenElements(form)
    {
         $("#mselParticipants option:selected").each(function (rowIndex) 
         {
               var $this = $(this);
               $(form).append("<input type='hidden' name='participants["+rowIndex+"].id' value='"+$this.val()+"' /> ");
         });
    
    }  
    
    //-------------------------
    
    var groupsDualListBox = $('.groupDualBox').bootstrapDualListbox({
        bootstrap2compatible    : false,
        nonSelectedListLabel: '<spring:message code="label.group.list" />',
        selectedListLabel: '<spring:message code="label.group.selected" />',
        preserveSelectionOnMove: 'moved',
        moveOnSelect: false,
        showFilterInputs :true,
        infoText: false
        
      });

    </script>
    <!-- /bootstrap-duallistbox -->
    
    
    <script type="text/javascript">
    $(document).ready(function()
    {
        $('a[data-toggle="tab"]').on('show.bs.tab', function(e) {
            localStorage.setItem('assessmentEditActiveTab', $(e.target).attr('href'));
        });
        var activeTab = localStorage.getItem('assessmentEditActiveTab');
        if(activeTab){
            $('#assessmentEditTab a[href="' + activeTab + '"]').tab('show');
        }
    });
    </script>
    
    <!-- bootstrap-daterangepicker -->
    <script src="resources/lib/moment/min/moment.min.js"></script>
    <script src="resources/lib/moment/locale/ru.js"></script>
    <script src="resources/lib/moment/locale/ky.js"></script>
    <script src="resources/lib/bootstrap-daterangepicker/daterangepicker.js"></script>
    <script>
      $(document).ready(function() 
      { 
          moment.locale('${locale}');
          
          $('.date-picker').daterangepicker(
          {
              locale: { 
                  format: "${dateFormatShort.toUpperCase()}"
                },
              singleDatePicker: true,
              //startDate: '01.01.2016',
              calender_style: "picker_4"
                  }, function(start, end, label) {
                      console.log(start.toISOString(), end.toISOString(), label);
          });
      });
    </script>
    <!-- /bootstrap-daterangepicker --> 
    <script type="text/javascript">
    
    $('a[rel=modal]').on('click', function(evt) 
    {
        evt.preventDefault();
        
        $('.modal-container').load($(this).attr('href'), function (responseText, textStatus) 
        {
            if ( textStatus === 'success' || textStatus === 'notmodified') 
            {
                $("#modalTaskList").on("hidden.bs.modal", function (e) 
                {   
                    $('#modalTaskList').unbind();
                    table.ajax.reload();    
                });
                
                $('#modalTaskList').modal().show();
            }
        });
    });
    
   
    
    //---------------------------------------
    $(document).on('click', '.btn-remove', function(e) 
    {
    	btn = this;
    	
    	bootbox.confirm(
        {
            title: '<i class="fa fa-exclamation"></i>&nbsp;&nbsp;<spring:message code="label.attention"/>',
            message: "<spring:message code="label.action.remove"/>?",
            size:"small",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i>&nbsp;<spring:message code="label.action.cancel"/>',
                    className: 'btn-default btn-xs'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i>&nbsp;<spring:message code="label.action.submit"/>',
                    className: 'btn-primary btn-xs'
                }
            },
            callback: function (result) 
            {
            	if(result)
            	{
            	  assessmentId = ${assessment.id};
                  taskId = $(btn).val();
                  removeTaskFromAssessment(btn,assessmentId,taskId);   
                }
            }
        });
    });
    	    
   //----------------
    function removeTaskFromAssessment(element, assessmentId, taskId)
    {
    
        $(element).text(' ... ');
        var sURL = 'rest/assessment/test/task/remove?asmt_task_id='+taskId+'&assessment_id='+assessmentId;
        
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
    //---------------------
    function getLang()
    {
    	var lang = '${pageContext.response.locale }';
    	if(lang == "" || lang == "en")
    		return "";
    	else
    		return 'resources/lib/datatables.net/i18n/'+lang+'.json';
    }
    //---------------------
    
    var table = null;
    
    $(document).ready(function() 
    {
        var taskId = 0;
        var indexRow = 0;
        
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
            "lengthChange": true,
            "info" : true,
            
            "ajax": 
            {   "url": 'rest/assessment/test/task/list?assessment_id=${assessment.id}',
                "type": "GET"
            },
            "columns": [
                { "data": null , "width": "5%" , 'searchable': false,'orderable': false, 
                    'render': function (data, type, row, meta) 
                    {
                        return meta.row + meta.settings._iDisplayStart + 1;
                    }
                },
                { "data": null ,'orderable': false, 
                    'render': function (data, type, row, meta) 
                    {
                        return '<a href="asmt_task_details.vw?asmt_task_id='+data['id']+'">'+data['itemContent']+'</a>';
                    }
                },
                { "data": "modeTypeName" ,'orderable': false },
                { "data": null,'orderable': false,
                    "render" : function ( data, type, full ) 
                    { 
                      return '<button class="btn btn-danger btn-remove btn-xs btn-td" value="'+data['id']+'"' +
                             'type="button"><i class="fa fa-close"></i></button>';
                    }
                }
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
    });
    </script>
</body>
</html>
