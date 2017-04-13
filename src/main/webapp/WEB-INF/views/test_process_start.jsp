<!-- ************************************* -->
<%@ page contentType="text/html" 
        pageEncoding="UTF-8"
	    errorPage="error.jsp"%>

<%@ page import="common.utils.StringUtils, 
common.utils.system.SystemUtils,
java.util.Locale, 
model.assessment.*,
model.assessment.task.*,
model.assessment.process.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

<title><spring:message code="label.page.asmt_process_start.title"/></title>

<!-- Bootstrap -->
<link href="resources/lib/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Font Awesome -->
<link href="resources/lib/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">

<!-- NProgress -->
<link href="resources/lib/nprogress/nprogress.css" rel="stylesheet">

<!-- Custom Theme Style -->
<link href="resources/css/custom.css" rel="stylesheet">
<link href="resources/css/assessment.css" rel="stylesheet">

</head>

<!-- ***************************** -->
<c:set var="processSession" value="${sessionScope.processSession}"/>
<c:set var="processResponse" value="${processSession.processResponse}"/>
<c:set var="taskCount" value="${processSession.taskIds.size()}"/>
<c:set var="taskIndex" value="${(param.taskIndex + 1) >= taskCount ? taskCount - 1 : param.taskIndex}"/>
<c:set var="task" value="${processSession.processResponse.task }"/>
<c:set var="taskDetails" value="${task.detailsRandom}"/>
<c:set var="processTime" value="${processSession.assessmentTime}"/>
<c:set var="dateFormatShort" value="${SystemUtils.getSettings('system.app.date.format.short')}"/>

<!-- ***************************** -->

<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<!-- sidebar -->
			<jsp:include page="include/sidebar.jsp"><jsp:param name="page"
					value="test_process_start.vw" /></jsp:include>
			<!-- /sidebar -->

			<!-- top navigation -->
			<jsp:include page="include/header.jsp"><jsp:param name="page"
					value="test_process_start.vw" /></jsp:include>
			<!-- /top navigation -->

			<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<div class="row">
						<div class="col-md-10 col-sm-10 col-xs-10">
							<div class="x_panel">
								<div class="x_title">
									<h2><spring:message code="label.page.asmt_process_start.title"/>
                                     &nbsp;&nbsp;-&nbsp;&nbsp;${processSession.assessmentName}</h2>
                                     <div class="btn-group pull-right">
                                      <button type="button" class="btn btn-primary btn-xs btn-asmt-end">
                                        <i class="fa fa-check-square-o"></i>&nbsp;                  
                                        <spring:message code="label.assessment.end"/></button>
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
                                   <div class="col-md-12">
                                   <form method="POST" id="processForm" name="processResponse" action="rest/assessment/process/task">
                                    
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" id="inpTaskId" name="task.id" value="${task.id}">
                                    <input type="hidden" id="inpResponseId" name="id" value="${processSession.processResponse.id}">
                                    
                                    <table class="table table-bordered dataTable">
                                      <thead>
                                        <tr>
                                            <th colspan="8"></th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <tr>
                                          <th class="col-md-1 success" scope="row"><spring:message code="label.asmt.task.number" />:</th>
                                          <td class="col-md-1 info">
                                              <div id="dvTaskIndexPane" class="timing-panel">${taskIndex + 1}</div>
                                          </td>
                                          <th class="col-md-1 success" scope="row"><spring:message code="label.asmt.task.count" />:</th>
                                          <td class="col-md-1">
                                              <div id="dvTaskCount" class="timing-panel">${taskCount }</div>
                                          </td>
                                          <th class="success col-md-2" scope="row"><spring:message code="label.date.time" />:</th>
                                          <td class="col-md-2"><div class="timing-panel"><c:out value="${StringUtils.minutesToDetails(processTime)}"/></div>
                                          </td>
                                          <th class="success col-md-2" scope="row">
                                            <spring:message code="label.date.time.remaining" />:</th>
                                          <td class="col-md-2" id="timerCountDownTd"><div class="timing-panel" id="timerCountDown">
                                          </div></td>
                                        </tr>
                                        <thead>
                                        <tr>
                                            <th colspan="8">
                                            <div>
                                                <c:forEach begin="0" end="${taskCount == 0? 0: taskCount - 1}" var="item">
                                                    <c:set var = "btnClass" value="btn-default"/>
                                                    <c:set var = "activeClass" value=""/>
                                                    <c:if test="${item == taskIndex}">
                                                        <c:set var = "activeClass" value="btn-task-index-current"/>
                                                    </c:if>
                                                    <c:choose>
                                                        <c:when test="${processSession.taskStatus[item] == 2}">
                                                            <c:set var = "activeClass" value="btn-success"/></c:when>
                                                        <c:when test="${processSession.taskStatus[item] == 3}">
                                                            <c:set var = "activeClass" value="btn-primary"/></c:when>                                                        
                                                    </c:choose>
                                                    <button type="button" id="btnTaskIndex${item}" class="btn ${btnClass} ${activeClass} btn-sm btn-task-index" data-index="${item}">${item + 1}</button>
                                                </c:forEach>
                                                </div>
                                            </th>
                                        </tr>
                                        </thead>
                                        <tr>
                                          <th class="col-md-3 success" colspan="2"><spring:message code="label.asmt.task.item.content" /></th>
                                          <td class="col-md-9 tasks-panel" colspan="6">
                                            <div id="dvItemContentPane"><c:out value="${task.itemContent}"/></div>
                                          </td>
                                        </tr>
                                        <tr>
                                          <th class="success" colspan="2"><spring:message code="label.asmt.task.item.options" /></th>
                                          <td class="col-md-9 tasks-panel" colspan="6">
                                           <div id="dvItemDetailsPane" class="form-group">
                                             <c:forEach var="taskDetail" items="${taskDetails}" varStatus="loopCounter">
                                               <c:choose>
                                                  <%-- Single Choice --%>
                                                  <c:when test="${task.modeType == 1}">
                                                  <c:if test="${(not empty processResponse.details[0].id) && loopCounter.index == 0}">
                                                    <input type="hidden" name="details[0].id" value="${processResponse.details[0].id}">
                                                  </c:if>
                                                  <div class="input-item-detail">
                                                     <div class="radio">
                                                        <label><input ${processResponse.details[0].taskDetail.id == taskDetail.id ? 'checked=checked' : ''} 
                                                                type="radio" name="details[0].taskDetail.id" value="${taskDetail.id}"/>
                                                          &#${loopCounter.index + 65}; ) ${taskDetail.itemDetail }</label>
                                                      </div>
                                                      </div>
                                                  </c:when>                                                  
                                                  <%-- Multiple Choice --%>
                                                  <c:when test="${task.modeType == 2}">
                                                     <div class="input-item-detail">
                                                         <div class="checkbox">
                                                            <label>
                                                              <c:set var="isChecked" value=""/>
                                                              <c:forEach var="responseDetail" items="${processResponse.details}" varStatus="counter">
                                                                <c:if test="${taskDetail.id == responseDetail.taskDetail.id}">
                                                                    <c:set var="isChecked" value="checked"/>
                                                                    <input type="hidden" name="details[${loopCounter.index}].id" value="${responseDetail.id}">
                                                                </c:if>  
                                                              </c:forEach>
                                                              <input type="checkbox" ${isChecked} name="details[${loopCounter.index}].taskDetail.id" 
                                                               value="${taskDetail.id}" class="checkbox-multi-choice">
                                                              &#${loopCounter.index + 65}; ) ${taskDetail.itemDetail }</label>
                                                         </div>
                                                     </div>
                                                  </c:when>                                                  
                                                  <%-- Short Text Choice --%>
                                                  <c:when test="${task.modeType == 3}">
                                                     <div class="input-item-detail">
                                                         <div class="text">
                                                               <label>&#${loopCounter.index + 65}; )</label>
                                                               <input type="hidden" name="details[${loopCounter.index}].taskDetail.id" value="${taskDetail.id}">
                                                               <c:if test="${(not empty processResponse.details[loopCounter.index].id)}">
                                                                    <input type="hidden" name="details[0].id" value="${processResponse.details[0].id}">
                                                                </c:if>
                                                               <input type="text" size="35" name="details[${loopCounter.index}].itemResponse" value="${processResponse.details[loopCounter.index].itemResponse}">
                                                         </div>
                                                     </div>
                                                  </c:when>
                                                  <%-- Esse ---------------%>
                                                  <c:when test="${task.modeType == 4}">
                                                     <c:if test="${loopCounter.index == 0 }">
                                                         <input type="hidden" name="details[0].taskDetail.id" value="${taskDetail.id}">
                                                         <c:if test="${(not empty processResponse.details[0].id) && loopCounter.index == 0}">
                                                            <input type="hidden" name="details[0].id" value="${processResponse.details[0].id}">
                                                         </c:if>
                                                         <div class="col-md-12 col-sm-12 col-xs-12">
                                                            <textarea rows="14" name="details[0].itemResponse" class="resizable_textarea form-control">${processResponse.details[0].itemResponse}</textarea>
                                                         </div>
                                                     </c:if>
                                                  </c:when>
                                                  <c:otherwise>
                                                  </c:otherwise>
                                               </c:choose>
                                              </c:forEach>
                                            </div>
                                           </td>
                                        </tr>
                                        <tr id="btnGroupRow">
                                          <th class="success" scope="row" colspan="2"></th>
                                          <td class="success" colspan="2">
                                               <button type="button" id="btnSaveResponse" class="btn btn-success btn-xs">
                                                <i class="fa fa-hourglass-end"></i>&nbsp;
                                                <spring:message code="label.asmt.task.response.save.next"/>
                                               </button> 
                                          </td>
                                          <td class="success" colspan="1">
                                            <button type="button" id="btnSkipResponse" class="btn btn-primary btn-xs">
                                                <i class="fa fa-share"></i>&nbsp;<spring:message code="label.asmt.task.response.skip"/>
                                            </button>
                                          </td>
                                          <td class="success" colspan="3">
                                            <div id="dvProgressPane" class="progress-asmt">
                                                <img class="img-progress-asmt" alt="Loading ..." src="resources/images/ajax-loader-pg.gif">
                                            </div>
                                            <div id="dvErrorPane">...</div>
                                          </td>
                                        </tr>
                                      </tbody>
                                    </table>
                                   </form>
                                   </div>
                                </div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /page content -->

			<!-- footer content -->
			<jsp:include page="include/footer.jsp"><jsp:param name="page"
					value="test_process_start.vw" /></jsp:include>
			<!-- /footer content -->
		</div>
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
    
    <!-- Bootbox-->
    <script src="resources/lib/bootbox/js/bootbox.min.js"></script>
    <script type="text/javascript">
    
    //---------------------------------------
    $(document).on('click', '.btn-asmt-end', function(e) 
    {
        bootbox.confirm(
        {
            title: '<i class="fa fa-exclamation"></i>&nbsp;&nbsp;<spring:message code="label.attention"/>',
            message: "<spring:message code="message.confirm.assessment.end"/>",
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
                    endAssessmentTest();
                }
            }
        });
    });
    </script>
    
    <!-- Timer -->
    <script type="text/javascript" src="resources/lib/jquery.countdown-2.2.0/jquery.countdown.js"></script>
    <script type="text/javascript">
    var processTime =  ${processTime};
    var elapsedTime =  ${processSession.timeElapsed};
    
    processTime = (processTime * 60 * 1000) - elapsedTime;

    function getCountDownTime() 
    {
    	return new Date(new Date().valueOf() + processTime);
    }

    var $clock = $('#timerCountDown');

    $clock.countdown(getCountDownTime(), function(event) 
    {
      $(this).html(event.strftime('%H:%M:%S'));
      
    });

    $clock.on('finish.countdown', function() 
    {
    	$("#timerCountDownTd").addClass('danger');
        $("#btnGroupRow").hide();
        $('#timerCountDown').html('<spring:message code="label.date.time.timeup"/>');
    });
    
    </script>
    <!-- /Timer -->
    
    <!-- Custom Theme Scripts -->
    <script src="resources/js/assessment.js"></script>
    <script type="text/javascript">
    
    taskCount = ${taskCount};
    taskIndex = ${taskIndex};
    
    </script>
    
</body>
</html>
