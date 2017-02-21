<!-- ************************************* -->
<%@ page contentType="text/html" 
        pageEncoding="UTF-8"
	    errorPage="error.jsp"%>

<%@ page import="common.utils.StringUtils, 
common.utils.system.SystemUtils,
java.util.Locale, 
model.assessment.*,
model.assessment.task.*,
model.assessment.process.*,

model.common.session.SessionData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

<title><spring:message code="label.page.assessment_start.title"/></title>

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

</head>

<!-- ***************************** -->
<c:set var="process" value="${sessionScope.sessionData.assessmentProcess}"/>
<c:set var="taskCount" value="${process.taskIds.size()}"/>
<c:set var="taskIndex" value="${(param.taskIndex + 1) >= taskCount ? taskCount - 1 : param.taskIndex}"/>
<c:set var="task" value="${processResponse.task}"/>
<c:set var="taskDetails" value="${task.details}"/>
<c:set var="processTime" value="${process.assessment.time}"/>

<c:set var="dateFormatShort" value="${SystemUtils.getSettings('system.app.date.format.short')}"/>


<!-- ***************************** -->

<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<!-- sidebar -->
			<jsp:include page="include/sidebar.jsp"><jsp:param name="page"
					value="asmt_start_process.vw" /></jsp:include>
			<!-- /sidebar -->

			<!-- top navigation -->
			<jsp:include page="include/header.jsp"><jsp:param name="page"
					value="asmt_start_process.vw" /></jsp:include>
			<!-- /top navigation -->

			<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<div class="row">
						<div class="col-md-10 col-sm-10 col-xs-10">
							<div class="x_panel">
								<div class="x_title">
									<h2><spring:message code="label.page.assessment_start.title"/>
                                     &nbsp;&nbsp;-&nbsp;&nbsp;${process.assessment.name}</h2>
                                     <div class="btn-group pull-right">
                                      <a href="asmt_end_process.do" role="button" class="btn btn-primary btn-xs">
                                        <i class="fa fa-check-square-o"></i>&nbsp;
                                        <spring:message code="label.assessment.end"/></a>
                                    </div>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
                                   <div class="col-md-12">
                                   <form action="asmt_process_start.do" method="post">
                                    <input type="hidden" name="taskIndex" value="${taskIndex+1}">
                                    <table class="table table-bordered dataTable">
                                      <thead>
                                        <tr>
                                            <th colspan="6"><i class="fa fa-cube"></i>&nbsp;&nbsp;
                                            <spring:message code="label.asmt.task" /></th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <tr>
                                          <th class="success col-md-2" scope="row"><spring:message code="label.asmt.task.number" />:</th>
                                          <td class="col-md-2">
                                              <div class="timing-panel">
                                                    <spring:message code="label.asmt.task.number.overall" arguments="${taskIndex + 1},${taskCount}" />
                                              </div>
                                              </td>
                                          <th class="success col-md-2" scope="row"><spring:message code="label.date.time" />:</th>
                                          <td class="col-md-2"><div class="timing-panel"><c:out value="${StringUtils.minutesToDetails(processTime)}"/></div>
                                          </td>
                                          <th class="success col-md-2" scope="row">
                                            <spring:message code="label.date.time.remaining" />:</th>
                                          <td class="col-md-4" id="timerCountDownTd"><div class="timing-panel" id="timerCountDown">
                                          </div></td>
                                        </tr>
                                        <thead>
                                        <tr>
                                            <th colspan="6"></th>
                                        </tr>
                                        </thead> 
                                        <tr>
                                          <th class="col-md-2" colspan="1"><spring:message code="label.asmt.task.item.content" /></th>
                                          <td class="col-md-10 tasks-panel" colspan="5">
                                            <c:out value="${task.itemContent}"/>
                                          </td>
                                        </tr>
                                        <tr>
                                          <th class="col-md-2" colspan="1"><spring:message code="label.asmt.task.item.options" /></th>
                                          <td class="col-md-10 tasks-panel" colspan="5">
                                           <div class="form-group">
                                             <c:forEach var="taskDetail" items="${taskDetails}" varStatus="loopCounter">
                                               <c:choose>
                                                  <%-- Single Choice --%>
                                                  <c:when test="${task.modeType == 1}">
                                                     <div class="radio">
                                                        <label><input type="radio" name="details[0].taskDetail.id" value="${taskDetail.id}"/>
                                                          &#${loopCounter.index + 65}; ) ${taskDetail.itemDetail }</label>
                                                      </div>
                                                  </c:when>
                                                  <%-- Multiple Choice --%>
                                                  <c:when test="${task.modeType == 2}">
                                                     <div class="checkbox">
                                                        <label>
                                                          <input type="checkbox"  name="optionsRadios" value="">
                                                          &#${loopCounter.index + 65}; ) ${taskOption.itemOption }</label>
                                                     </div>
                                                  </c:when>
                                                  <%-- Multiple Choice --%>
                                                  <c:when test="${tasks.modeType == 4}">
                                                     <div class="text">
                                                           <label>&#${loopCounter.index + 65}; )</label>
                                                           <input type="text" size="35" name="optionsRadios" value="">
                                                     </div>
                                                  </c:when>
                                                  <%-- Esse ---------------%>
                                                  <c:when test="${task.modeType == 5}">
                                                     <c:if test="${loopCounter.index == 0 }">
                                                         <div class="col-md-12 col-sm-12 col-xs-12">
                                                            <textarea rows="14" class="resizable_textarea form-control"></textarea>
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
                                          <th scope="row" ></th>
                                          <td><button type="submit" id="btnNextStep" class="btn btn-success btn-xs">
                                                <i class="fa fa-share"></i>&nbsp;
                                                <spring:message code="label.asmt.task.response.save"/>
                                               </button> 
                                          </td>
                                          <td >
                                          </td>
                                          <td colspan="3">
                                            <div class="pull-right" style="display:inline;">
                                                 <a href="asmt_start_process.do?taskIndex=${taskIndex + 1 }" 
                                                    role="button" id="btnNextStep" class="btn btn-primary btn-xs">
                                                    <spring:message code="label.action.jump"/>
                                                </a>
                                                 <select id="assessment-type" class="input-select-sm" name="assessmentType" style="border: 1px solid grey;">
                                                    <c:forEach begin="0" end="${taskCount - 1}"  var="item"> 
                                                        <option ${item == taskIndex ? 'selected="selected"' : ''}
                                                        value="${item}">${item + 1}</option>
                                                    </c:forEach>
                                                 </select>
                                             </div>
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
					value="asmt_start_process.vw" /></jsp:include>
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
    
    <!-- Timer -->
    <script type="text/javascript" src="resources/lib/jquery.countdown-2.2.0/jquery.countdown.js"></script>
    <script type="text/javascript">
    var processTime =  ${processTime};
    var elapsedTime =  ${process.timeElapsed};
    
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
    
</body>
</html>
