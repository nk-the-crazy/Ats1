<!-- ************************************* -->
<%@ page contentType="text/html" 
         pageEncoding="UTF-8"
	     errorPage="../error.jsp"
	     import="com.google.common.base.Strings" %>
	    
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!-- ************************************* -->
<c:set var="systemLocale" value="${pageContext.response.locale }"/>

<c:choose>
  <c:when test="${systemLocale  == 'en'}">
    <c:set var="localeStr" value="English"/>
  </c:when>
  <c:when test="${systemLocale  == 'ru'}">
    <c:set var="localeStr" value="Русский"/>
  </c:when>
  <c:when test="${systemLocale  == 'ky'}">
    <c:set var="localeStr" value="Кыргызча"/>
  </c:when>
  <c:otherwise>
    <c:set var="localeStr" value="Русский"/>
  </c:otherwise>
</c:choose>

<!-- ************************************* -->

<!-- top navigation -->
<div class="top_nav navbar-fixed-top">
	<div class="nav_menu">
		<nav>
			<div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
			</div>
            <form id="formHeaderMenu" action="logout.do" method="POST">
			<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
            <ul class="nav navbar-nav navbar-right">
				<li class=""><a href="javascript:;" data-toggle="dropdown"
					aria-expanded="false"> <i class="fa fa-user"></i>
                    <span>&nbsp;&nbsp;<b><sec:authentication property="principal.username" /></b></span>
						<span class=" fa fa-angle-down"></span></a>
					<ul class="dropdown-menu dropdown-usermenu pull-right">
                        <li><a href="edit_password.vw"><i class="fa fa-unlock pull-left"></i>
                             <spring:message code="label.menu.change_password"/></a></li>
						<li><a href="resources/manual/index.html"><i class="fa fa-question-circle pull-left">
                              </i><spring:message code="label.menu.help"/></a></li>
						<li><a href="#" onclick="submitLogoutForm();" id="logoutLink"><i class="fa fa-sign-out pull-left"></i>
                              <spring:message code="label.menu.logout"/></a></li>
					</ul></li>
				<li class=""><a href="javascript:;" data-toggle="dropdown"
					aria-expanded="false"> <i class="fa fa-globe"></i><span>&nbsp;&nbsp;<b>${localeStr}</b></span>
						<span class=" fa fa-angle-down"></span></a>
					<ul class="dropdown-menu dropdown-usermenu pull-right">
						<li><a href="changelocale.do?locale=en">English</a></li>
						<li><a href="changelocale.do?locale=ru">Русский</a></li>
						<li><a href="changelocale.do?locale=ky">Кыргызча</a></li>
					</ul></li>
			</ul>
            </form>
		</nav>
	</div>
</div>
<script> 
function submitLogoutForm()
{ 
	$('#formHeaderMenu').submit();
}

</script>
<!-- /top navigation -->
