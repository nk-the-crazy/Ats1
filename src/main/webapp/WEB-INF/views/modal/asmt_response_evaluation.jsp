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
    <div id="modalEvaluation" style="vertical-align: middle;" class="modal fade"  >
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title"><spring:message code="label.page.asmt_evaluation.title" /></h4>
                </div>
                <!-- /modal-header -->
                <div class="modal-body">
                  <form id="evaluationform" action="rest/assessment/response/evaluate" method="POST" role="form" class="form-horizontal" >
                      <input type="hidden" name="asmt_response_detail_id" value="${param.asmt_response_detail_id}">
                      <div class="row">
                        <div class="col-md-6">
                            <label class="control-label" for="evaluation-grade">
                               <spring:message code="label.asmt.task.grade" />:</label>
                            <input  type="text" id="evaluation-grade" name="grade" class="form-control input-sm">                
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-sm-12">
                            <label class="control-label" for="evaluation-desc">
                                <spring:message code="label.data.comment" />:</label>
                            <input type="text" id="evaluation-desc" name="comment" class="form-control input-sm">   
                         </div>
                      </div>
                  </form>   
                </div>
                <!-- /modal-body -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-default btn-xs" data-dismiss="modal">
                        <spring:message code="label.action.cancel" />
                    </button>
                    <button type="button" class="btn btn-primary btn-xs" onclick="submitForm();" data-dismiss="modal">
                        <i class="glyphicon glyphicon-ok"></i>&nbsp;&nbsp;<spring:message code="label.action.save" />
                    </button>
                </div>
                <!-- /modal-footer -->
            </div>
            <!-- /modal-content -->
        </div>
        <!-- /modal-dialog -->
    </div>
</body>
<script type="text/javascript">

    $("#evaluationform").submit(function(e)
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
                //data: return data from server
            },
            error: function(jqXHR, textStatus, errorThrown) 
            {
                //if fails      
            }
        });
        e.preventDefault(); //STOP default action
        e.unbind(); //unbind. to stop multiple form submit.
    });
    
    function submitForm()
    {
        $("#evaluationform").submit(); 
    }
    </script>
</html>
