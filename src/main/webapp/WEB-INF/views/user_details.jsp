<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8" 
        errorPage="error.jsp" 
        import="model.identity.*,common.utils.system.SystemUtils"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<title><spring:message code="label.page.user_details.title" /></title>

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
<c:set var="user" value="${requestScope.userDetails}"/>
<c:set var="groupsPage" value="${requestScope.userGroups}"/>
<c:set var="dateFormatShort" value="${SystemUtils.getSettings('system.app.date.format.short')}"/>
<c:set var="dateTimeFormatShort" value="${SystemUtils.getSettings('system.app.date.time.format.short')}"/>
<!-- ***************************** -->

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp"><jsp:param name="page"
                    value="user_details.vw" /></jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp"><jsp:param name="page"
                    value="user_details.vw" /></jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="row">
                        <div class="col-md-8 col-sm-8 col-xs-8">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2><spring:message code="label.page.user_details.title" /></h2>
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
                                    <ul id="userDetailsTab" class="nav nav-tabs bar_tabs" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#tab_content1" id="account-tab" role="tab" data-toggle="tab" aria-expanded="true">
                                            <spring:message code="label.account" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content2" id="profile-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <spring:message code="label.profile" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content3" id="contacts-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <i class="fa fa-phone"></i>
                                            <spring:message code="label.contacts" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content4" id="roles-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <i class="fa fa-key">&nbsp;</i>
                                            <spring:message code="label.user.roles" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content5" id="groups-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <i class="fa fa-group">&nbsp;</i>
                                            <spring:message code="label.group.member" /></a>
                                        </li>
                                        
                                    </ul>
                                    <div id="userDetailsTabContent" class="tab-content">
                                        <div id="tab_content1" role="tabpanel" class="tab-pane col-md-8 fade active in" 
                                              aria-labelledby="account-tab">
                                            <table class="table table-bordered dataTable">
                                              <thead>
                                                <tr>
                                                    <th colspan="2"><i class="fa fa-user"></i>&nbsp;&nbsp;
                                                    <spring:message code="label.account" /></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <tr>
                                                  <th scope="row" class="col-md-3"><spring:message code="label.user.login" />:</th>
                                                  <td class="col-md-5"><c:out value="${user.userName}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><spring:message code="label.account_email" />:</th>
                                                  <td><c:out value="${user.email}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.user.last_login_date" />:</th>
                                                  <td><fmt:formatDate pattern="${dateTimeFormatShort}" value="${user.lastLogin}" /></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.data.status" />:</th>
                                                  <td  class="${user.status == 1 ? 'a' : 'warning'}">
                                                        ${SystemUtils.getAttribute('system.attrib.data.status',user.status, locale)}
                                                  </td>
                                                </tr>
                                              </tbody>
                                            </table>
                                        </div>
                                        
                                        <div id="tab_content2" role="tabpanel" class="tab-pane fade col-md-12" 
                                             aria-labelledby="profile-tab">
                                            <table class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th colspan="2"><spring:message code="label.profile" /></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <tr>
                                                  <th scope="row" class="col-md-3"><spring:message code="label.user.full_name" />:</th>
                                                  <td  class="col-md-8">
                                                  <c:out value="${user.person.lastName}"/>&nbsp;
                                                  <c:out value="${user.person.firstName}"/>&nbsp;
                                                  <c:out value="${user.person.middleName}"/>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.user.gender" />:</th>
                                                  <td>
                                                    ${SystemUtils.getAttribute('system.attrib.user.gender',user.person.personDetails.gender,locale)}
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.user.birth_date" />:</th>
                                                  <td><fmt:formatDate pattern="${dateFormatShort}" value="${user.person.personDetails.birthDate}" />
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.user.tax_payer_number" />:</th>
                                                  <td><c:out value="${user.person.personDetails.taxPayerNumber}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.user.marital_status" />:</th>
                                                  <td>
                                                    ${SystemUtils.getAttribute('system.attrib.user.marital',user.person.personDetails.maritalStatus,locale)}
                                                  </td>
                                                </tr>
                                                <thead>
                                                    <tr>
                                                        <th colspan="2"><i class="fa fa-bank"></i>&nbsp;
                                                        <spring:message code="label.organization" /></th>
                                                    </tr>
                                                </thead>
                                                <tr>
                                                  <th scope="row" >
                                                  <spring:message code="label.organization" />:</th>
                                                  <td><a href="organization_details.vw?organization_id=${user.person.organization.id}">
                                                    <c:out value="${user.person.organization.name}"/></a></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" >
                                                  <spring:message code="label.user.activity" />:</th>
                                                  <td><c:out value="${user.person.personDetails.activity}"/></td>
                                                </tr>
                                                <thead>
                                                    <tr>
                                                        <th colspan="2"><spring:message code="label.user.passport" /></th>
                                                    </tr>
                                                </thead>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.user.passport.number" />:</th>
                                                  <td>
                                                  <c:out value="${user.person.personDetails.passportSerial}"/>
                                                  <c:out value="${user.person.personDetails.passportNumber}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.user.passport.date.valid" />:</th>
                                                  <td><fmt:formatDate pattern="${dateFormatShort}" value="${user.lastLogin}" />
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.user.passport.date.issued" />:</th>
                                                  <td>
                                                  <c:out value="${user.person.personDetails.passportIssuedBy}"/>&nbsp;&nbsp;
                                                  <fmt:formatDate pattern="${dateFormatShort}" value="${user.person.personDetails.passportIssuedDate}" />
                                                  </td>
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
                                                    ${SystemUtils.getAttribute('system.attrib.address.country',user.person.address.countryId,locale)}
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><spring:message code="label.address.region" />:</th>
                                                  <td>
                                                    ${SystemUtils.getAttribute('system.attrib.address.region.2',user.person.address.regionId,locale)}
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><spring:message code="label.address.city" />:</th>
                                                  <td><c:out value="${user.person.address.city}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><spring:message code="label.address.primary" />:</th>
                                                  <td><c:out value="${user.person.address.addressLine}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><spring:message code="label.address.secondary" />:</th>
                                                  <td><c:out value="${user.person.address.secondaryAddress}"/></td>
                                                </tr>
                                                <thead>
                                                <tr>
                                                    <th colspan="2"><i class="fa fa-phone"></i>&nbsp;<spring:message code="label.contacts" /></th>
                                                </tr>
                                                </thead>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.contacts.phone" />:</th>
                                                  <td><c:out value="${user.person.contact.phone}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.contacts.email" />:</th>
                                                  <td><c:out value="${user.person.contact.email}"/></td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><spring:message code="label.contacts.secondary" />:</th>
                                                  <td><c:out value="${user.person.contact.secondaryContacts}"/></td>
                                                </tr>
                                              </tbody>
                                            </table>                                        
                                        </div>
                                        <div role="tabpanel" class="tab-pane col-md-8 fade" 
                                             id="tab_content4" aria-labelledby="roles-tab">
                                            
                                            <table id="" class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th>№</th>
                                                    <th><spring:message code="label.user.login" /></th>
                                                    <th><spring:message code="label.role.desc" /></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <!-- *********Role list ************ -->
                                                <c:forEach var="role" items="${user.roles}" varStatus="loopCounter">
                                                    <tr>
                                                        <td class="col-md-1">${loopCounter.count }</td>
                                                        <td><a href="role_details.vw?role_id=${role.id}">
                                                            <c:out value="${role.name}"/></a></td>
                                                        <td><c:out value="${role.details}"/></td>
                                                    </tr>
                                                </c:forEach>
                                                <!-- *********Role list ************ -->
                                              </tbody>
                                            </table>                                              
                                        </div>
                                        <div role="tabpanel" class="tab-pane fade col-md-12" id="tab_content5"  aria-labelledby="groups-tab">
                                            <table id="" class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th>№</th>
                                                    <th><spring:message code="label.group.name" /></th>
                                                    <th><spring:message code="label.group.desc" /></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <!-- *********Group list ************ -->
                                                <c:set var="index" value="${groupsPage.number * groupsPage.size}" />
                                                <c:forEach var="group" items="${groupsPage.content}" varStatus="loopCounter">
                                                    <tr>
                                                        <td class="col-md-1">${index + loopCounter.count }</td>
                                                        <td><a href="group_details.vw?group_id=${group[1]}">
                                                            <c:out value="${group[2]}"/></a></td>
                                                        <td><c:out value="${group[3]}"/></td>
                                                    </tr>
                                                </c:forEach>
                                                <!-- *********Group list ************ -->
                                              </tbody>
                                            </table> 
                                            <!------------- Pagination -------------->
                                            <c:if test="${groupsPage.totalPages > 1}">
                                                <jsp:include page="include/pagination.jsp">
                                                     <jsp:param name="page" value="user_details.vw" />
                                                     <jsp:param name="addParam" value="user_id=${param.user_id}" />
                                                     <jsp:param name="totalPages" value="${groupsPage.totalPages}" />
                                                     <jsp:param name="totalElements" value="${groupsPage.totalElements}" />
                                                     <jsp:param name="currentIndex" value="${groupsPage.number}" />
                                                     <jsp:param name="pageableSize" value="${groupsPage.size}" />
                                                 </jsp:include>
                                             </c:if>
                                            <!--------------------------------------->
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
            <jsp:param name="page" value="user_details.vw" />
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
            localStorage.setItem('userDetailsActiveTab', $(e.target).attr('href'));
        });
        
        var activeTab = localStorage.getItem('userDetailsActiveTab');
        
        if(activeTab)
        {
            $('#userDetailsTab a[href="' + activeTab + '"]').tab('show');
        }
    });
    </script>

</body>
</html>
