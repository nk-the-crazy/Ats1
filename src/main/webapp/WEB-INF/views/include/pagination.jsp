<!-- ************************************* -->
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="../error.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ************************************* -->
<c:set var="beginPage" value="${param.currentIndex-2}"/>
<c:set var="endPage"   value="${param.currentIndex+2}"/>
<c:set var="addParam"  value="${param.addParam}"/>

<c:if test="${beginPage <= 0}">
    <c:set var="beginPage" value="0"/>
</c:if>

<c:if test="${endPage  >= param.totalPages}">
    <c:set var="endPage" value="${param.totalPages-1}"/>
</c:if>

<c:url var="firstUrl" value="?page=0&size=${param.pageableSize}&${addParam}" />
<c:url var="lastUrl" value="?page=${param.totalPages-1}&size=${param.pageableSize}&${addParam}" />
<c:url var="prevUrl" value="?page=${param.currentIndex - 1}&size=${param.pageableSize}&${addParam}" />
<c:url var="nextUrl" value="?page=${param.currentIndex + 1}&size=${param.pageableSize}&${addParam}" />

<table class="col-md-10">
    <tr>
        <td><spring:message code="label.records_found" />&nbsp;:&nbsp;<b>
            <c:out value="${param.totalElements}"/></b></td>
        <td>&nbsp;&nbsp;</td>
    
        <td>
        <ul class="pagination pagination-sm">
            <c:choose>
                <c:when test="${param.currentIndex <= 0}">
                    <li class="page-item disabled"><a href="#">
                        <spring:message code="label.action.page_first" /></a></li>
                    <li class="page-item disabled"><a href="#">
                        <spring:message code="label.action.page_previous" /></a></li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a href="${firstUrl}">
                        <spring:message code="label.action.page_first" /></a></li>
                    <li class="page-item"><a href="${prevUrl}">
                        <spring:message code="label.action.page_previous" /></a></li>
                </c:otherwise>
            </c:choose>
            <c:forEach var="i" begin="${beginPage}" end="${endPage}">
                <c:url var="pageUrl" value="?page=${i}&size=${param.pageableSize}&${addParam}" />
                <c:choose>
                    <c:when test="${i == param.currentIndex}">
                        <li class="page-item active"><a href="${pageUrl}">
                            <c:out value="${i+1}" /></a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item page-item"><a href="${pageUrl}"><c:out value="${i+1}" /></a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:choose>
                <c:when test="${param.currentIndex == (param.totalPages -1 )}">
                    <li class="page-item disabled"><a href="#">
                        <spring:message code="label.action.page_next" /></a></li>
                    <li class="page-item disabled"><a href="#">
                        <spring:message code="label.action.page_last" /></a></li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a href="${nextUrl}"><spring:message
                                code="label.action.page_next" /></a></li>
                    <li class="page-item"><a href="${lastUrl}"><spring:message
                                code="label.action.page_last" /></a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </td></tr>
</table>


