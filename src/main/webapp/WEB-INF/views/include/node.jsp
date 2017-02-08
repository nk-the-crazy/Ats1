<!-- ************************************* -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- ************************************* -->
<c:forEach var="node" items="${node.children}"  varStatus="loopCounter">
    
    <tr class="treegrid-${node.id} treegrid-parent-${node.parent.id}">
        <td></td>
        <td><a href="asmt_category_details.vw?asmt_category_id=${node.id}">
        ${node.name}</a></td>
        <td>No Data</td>
        <td>${node.details}</td>
    </tr>
    
    <c:set var="node" value="${node}" scope="request"/>
    <jsp:include page="node.jsp"/>
</c:forEach>


    