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
<title><spring:message code="label.page.asmt_evaluation.title" /></title>
</head>
<body>
    <div id="modalUserExport" style="vertical-align: middle;" class="modal fade"  >
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
            <form id="userExportForm" name="userExportParams" action="user_details_export.do" 
                method="POST" role="form" class="form-horizontal" >
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="userId" value="${empty param.user_id? 0 : param.user_id}">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title"><spring:message code="label.page.export.title" /></h4>
                </div>
                <!-- /modal-header -->
                <div class="modal-body col-md-12">
                    <div class="col-md-12">
                     <table class="table table-bordered dataTable">
                      <thead>
                        <tr><th colspan="4"><spring:message code="label.data.include" /></th></tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th class="col-md-3" scope="row"><label class="control-label" for="role-name">
                            <spring:message code="label.contacts" />:</label></th>
                          <td class="col-md-1"><input type="checkbox" id="cxIncludeContacts" name="contacts"></td>
                          <th class="col-md-3" scope="row"><label class="control-label" for="role-name">
                            <spring:message code="label.user.passport" />:</label></th>
                          <td class="col-md-1"><input type="checkbox" id="cxIncludePassport" name="passport"></td>
                        </tr>
                        <tr>
                          <th scope="row"><label class="control-label" for="role-name">
                            <spring:message code="label.organization" />:</label></th>
                          <td><input type="checkbox" id="cxIncludeContacts" name="organization"></td>
                          <th scope="row"><label class="control-label" for="role-name">
                            <spring:message code="label.education" />:</label></th>
                          <td><input type="checkbox" id="cxIncludePassport" name="education"></td>
                        </tr>
                        <thead>
                             <tr><th colspan="4"><spring:message code="label.data.format" /></th></tr>
                        </thead>
                        <tr><th class="col-md-7" scope="row"><label class="control-label" for="">
                                <i class="fa fa-file-excel-o"></i>&nbsp;&nbsp;<spring:message code="label.action.export.xls"/>:</label></th>
                            <td><input type="radio" name="outputType" value="2" checked="checked"/> </td>
                            <th></th>
                            <td></td>
                        </tr>
                        <tr><th scope="row"><label class="control-label" for="">
                                <i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;<spring:message code="label.action.export.pdf"/>:</label></th>
                            <td><input type="radio" name="outputType" value="4"/></td>
                            <th></th>
                            <td></td>
                        </tr>
                      </tbody>
                     </table>
                    </div>
                </div>
                <!-- /modal-body -->
                <div class="modal-footer">
                    <span id="spProgress" style="display:none">
                        <img width="23" src="resources/images/ajax-loader.gif">&nbsp;&nbsp;&nbsp;&nbsp;
                    </span>
                    <button type="button" class="btn btn-default btn-xs" data-dismiss="modal">
                        <spring:message code="label.action.close" />
                    </button>
                    <button type="button" class="btn btn-primary btn-xs" onclick="submitForm();" id="btnSubmit">
                        <i class="glyphicon glyphicon-ok"></i>&nbsp;&nbsp;<spring:message code="label.action.export" />
                    </button>
                </div>
                <!-- /modal-footer -->
                </form>  
            </div>
            <!-- /modal-content -->
        </div>
        <!-- /modal-dialog -->
    </div>
</body>
<script type="text/javascript">

    $("#userExportFormNew").submit(function(e)
    {
        $('#spProgress').show();
    	var postData = $(this).serializeArray();
        var formURL = $(this).attr("action");
        
        $.ajax(
        {
            url : formURL,
            type: "POST",
            data : postData,
            success:function(data, textStatus, jqXHR) 
            {
            },
            error: function(jqXHR, textStatus, errorThrown) 
            {
                $('#spProgress').hide();
            },
            complete: function()
            {
                $('#spProgress').hide();
            }
        });
        e.preventDefault(); //STOP default action
    });
    
    function submitForm()
    {
    	$('#spProgress').show();
    	$('#btnSubmit').hide();
    	$("#userExportForm").submit(); 
    	$('#spProgress').hide();
    }
    </script>
</html>
