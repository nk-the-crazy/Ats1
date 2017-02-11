<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8" 
        errorPage="error.jsp" 
        import="model.identity.*,common.utils.system.SystemUtils"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- ************************************* -->

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title><spring:message code="label.page.organization_details.title" /></title>

<!-- Bootstrap -->
<link href="resources/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Font Awesome -->
<link href="resources/lib/font-awesome/css/font-awesome.min.css" rel="stylesheet">

<!-- NProgress -->
<link href="resources/lib/nprogress/nprogress.css" rel="stylesheet">

<!-- Custom Theme Style -->
<link href="resources/css/custom.css" rel="stylesheet">

<!-- Data Table -->
<link href="resources/lib/datatables.net-bs/css/dataTables.bootstrap.min.css"
    rel="stylesheet">

</head>
<!-- ***************************** -->
<c:set var="organization" value="${requestScope.organizationDetails}"/>
<c:set var="personListPage" value="${requestScope.personListPage}"/>
<!-- ***************************** -->

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp"><jsp:param name="page"
                    value="organization_details.vw" /></jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp"><jsp:param name="page"
                    value="organization_details.vw" /></jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="row">
                        <div class="col-md-8 col-sm-8 col-xs-8">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2><spring:message code="label.page.organization_details.title" /></h2>
                                     <div style="text-align: right;">
                                        <button type="button" class="btn btn-info btn-xs">
                                            <i class="fa fa-pencil-square-o"></i>&nbsp;
                                                <spring:message code="label.action.edit"/>
                                        </button>
                                        <button type="button" class="btn btn-primary btn-xs" onclick="window.history.back();">
                                            <i class="fa fa-chevron-left"></i>&nbsp;
                                                <spring:message code="label.action.back"/>
                                        </button>
                                      </div>
                                      <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                <div class="" role="tabpanel" data-example-id="togglable-tabs">
                                    <ul id="organizationDetailsTab" class="nav nav-tabs bar_tabs" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#tab_content1" id="organization-tab" role="tab" data-toggle="tab" aria-expanded="true">
                                            <spring:message code="label.page.organization_details.title" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content3" id="contacts-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <i class="fa fa-phone"></i>
                                            <spring:message code="label.contacts" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content2" id="users-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <spring:message code="label.user.list" /></a>
                                        </li>
                                    </ul>
                                    <div id="organizationDetailsTabContent" class="tab-content">
                                        <div id="tab_content1" role="tabpanel" class="tab-pane col-md-8 fade active in" 
                                              aria-labelledby="groups-tab">
                                            <table class="table table-bordered dataTable">
                                              <thead>
                                                <tr>
                                                    <th colspan="2"></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <tr>
                                                  <th scope="row" class="col-md-3"><spring:message code="label.group.name" />:</th>
                                                  <td class="col-md-5"><c:out value="${organization.name}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.data.status" />:</th>
                                                  <td  class="${organization.status == 1 ? '' : 'warning'}">
                                                      ${SystemUtils.getAttribute('system.attrib.data.status',organization.status,locale)}
                                                   </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.organization.tax_payer_number" />:</th>
                                                  <td><c:out value="${organization.details.taxPayerNumber}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.organization.sector" />:</th>
                                                  <td>
                                                      ${SystemUtils.getAttribute('system.attrib.organization.sector',organization.details.sector,locale)}
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.organization.activity" />:</th>
                                                  <td>
                                                      ${SystemUtils.getAttribute('system.attrib.organization.activity',organization.details.activity,locale)}
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.organization.desc" />:</th>
                                                  <td><c:out value="${organization.details.details}"/></td>
                                                </tr>
                                              </tbody>
                                            </table>
                                        </div>
                                        <div id="tab_content3" role="tabpanel" class="tab-pane fade col-md-12" aria-labelledby="contacts-tab">
                                            <table id="" class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th colspan="2"><i class="fa fa-map-marker"></i>&nbsp;<spring:message code="label.address" /></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <tr>
                                                  <th scope="row" class="col-md-3"><spring:message code="label.address.country" />:</th>
                                                  <td class="col-md-8">
                                                    ${SystemUtils.getAttribute('system.attrib.address.country',organization.address.countryId,locale)}
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><spring:message code="label.address.region" />:</th>
                                                  <td>
                                                    ${SystemUtils.getAttribute('system.attrib.address.region.2',organization.address.regionId,locale)}
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><spring:message code="label.address.city" />:</th>
                                                  <td><c:out value="${organization.address.city}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><spring:message code="label.address.primary" />:</th>
                                                  <td><c:out value="${organization.address.addressLine}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><spring:message code="label.address.secondary" />:</th>
                                                  <td><c:out value="${organization.address.secondaryAddress}"/></td>
                                                </tr>
                                                <thead>
                                                <tr>
                                                    <th colspan="2"><i class="fa fa-phone"></i>&nbsp;<spring:message code="label.contacts" /></th>
                                                </tr>
                                                </thead>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.contacts.phone" />:</th>
                                                  <td><c:out value="${organization.contact.phone}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.contacts.email" />:</th>
                                                  <td><c:out value="${organization.contact.email}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.contacts.secondary" />:</th>
                                                  <td><c:out value="${organization.contact.secondaryContacts}"/></td>
                                                </tr>
                                              </tbody>
                                            </table>                                        
                                        </div>
                                        <div role="tabpanel" class="tab-pane col-md-10 fade" 
                                             id="tab_content2" aria-labelledby="users-tab">
                                            <table id="" class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th>№</th>
                                                    <th><spring:message code="label.user.name" /></th>
                                                    <th><spring:message code="label.user.full_name" /></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <!-- *********Person list ************ -->
                                                <c:set var="index" value="${personListPage.number * personListPage.size}" />
                                                <c:forEach var="person" items="${personListPage.content}" varStatus="loopCounter">
                                                    <tr>
                                                        <td class="col-md-1">${index + loopCounter.count }</td>
                                                        <td><a href="user_details.vw?user_id=${person[1]}">
                                                            <c:out value="${person[2]}"/></a></td>
                                                        <td><c:out value="${person[5]}"/>&nbsp;<c:out value="${person[4]}"/></td>
                                                    </tr>
                                                </c:forEach>
                                                <!-- *********/Person list ************ -->
                                                
                                                <!------------- Pagination -------------->
                                                <c:if test="${personListPage.totalPages > 1}">
                                                    <jsp:include page="include/pagination.jsp">
                                                         <jsp:param name="page" value="user_details.vw" />
                                                         <jsp:param name="addParam" value="organization_id=${param.organization_id}" />
                                                         <jsp:param name="totalPages" value="${personListPage.totalPages}" />
                                                         <jsp:param name="totalElements" value="${personListPage.totalElements}" />
                                                         <jsp:param name="currentIndex" value="${personListPage.number}" />
                                                         <jsp:param name="pageableSize" value="${personListPage.size}" />
                                                     </jsp:include>
                                                 </c:if>
                                                <!--------------------------------------->
                                                
                                              </tbody>
                                            </table>                                              
                                        </div>
                                    </div>
                                  </div>
                               </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <jsp:include page="include/footer.jsp">
            <jsp:param name="page" value="organization_details.vw" />
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

    <!-- Custom Theme Scripts -->
    <script src="resources/js/custom.min.js"></script>

    <!-- Dat Tables -->
    <script src="resources/lib/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="resources/lib/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script type="text/javascript">
    $(document).ready(function()
    {
        $('a[data-toggle="tab"]').on('show.bs.tab', function(e) {
            localStorage.setItem('ognzDetailsactiveTab', $(e.target).attr('href'));
        });
        
        var activeTab = localStorage.getItem('ognzDetailsactiveTab');
        
        if(activeTab)
        {
            $('#organizationDetailsTab a[href="' + activeTab + '"]').tab('show');
        }
    });
    </script>

</body>
</html>
