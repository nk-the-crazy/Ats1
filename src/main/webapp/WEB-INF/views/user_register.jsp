<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8" 
        errorPage="error.jsp" 
        import="model.identity.*,common.utils.system.SystemUtils"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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

<title><spring:message code="label.page.user_register.title" /></title>

<!-- Bootstrap -->
<link href="resources/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Font Awesome -->
<link href="resources/lib/font-awesome/css/font-awesome.min.css" rel="stylesheet">

<!-- NProgress -->
<link href="resources/lib/nprogress/nprogress.css" rel="stylesheet">

<!-- Select2 -->
<link href="resources/lib/select2/dist/css/select2.min.css" rel="stylesheet">
<link href="resources/lib/select2/dist/css/select2-bootstrap.css" rel="stylesheet">

<!-- Data Table -->
<link href="resources/lib/datatables.net-bs/css/dataTables.bootstrap.min.css"
    rel="stylesheet">
    
<!-- bootstrap-daterangepicker -->
<link href="resources/lib/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">

<!-- bootstrap-duallistbox -->
<link href="resources/lib/bootstrap-duallistbox/dist/duallistbox.css" rel="stylesheet">

<!-- Custom Theme Style -->
<link href="resources/css/custom.css" rel="stylesheet">
</head>
<!-- ***************************** -->
<c:set var="organizations" value="${requestScope.organizationShortList}"/>
<c:set var="roles" value="${requestScope.roleShortList}"/>
<c:set var="groups" value="${requestScope.groupShortList}"/>
<c:set var="dateFormatShort" value="${SystemUtils.getSettings('system.app.date.format.short')}"/>
<!-- ***************************** -->

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <!-- sidebar -->
            <jsp:include page="include/sidebar.jsp"><jsp:param name="page"
                    value="user_register.vw" /></jsp:include>
            <!-- /sidebar -->

            <!-- top navigation -->
            <jsp:include page="include/header.jsp"><jsp:param name="page"
                    value="user_register.vw" /></jsp:include>
            <!-- /top navigation -->

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="">
                    <div class="row">
                        <div class="col-md-11 col-sm-11 col-xs-11">
                            <div class="x_panel">
                            <form id="user" data-parsley-validate action="user_register.do" 
                                  class="form-horizontal form-label-left" method="POST">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="x_title">
                                    <h2><spring:message code="label.page.user_register.title" /></h2>
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
                                    <ul id="userRegisterTab" class="nav nav-tabs bar_tabs" role="tablist">
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
                                    <div id="userRegisterTabContent" class="tab-content">
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
                                                  <th scope="row" class="col-md-2">
                                                    <label class="control-label-required" for="user-name">
                                                    <spring:message code="label.user.login" />:</label></th>
                                                  <td class="col-md-5"><input type="text" id="user-name" name="userName" value="${user.userName}"
                                                        class="form-control input-sm" required="required">
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-2">
                                                    <label class="control-label-required" for="user-email">
                                                    <spring:message code="label.account_email" />:</label></th>
                                                  <td class="col-md-5"><input type="text" id="user-email" name="email" value="${user.email}"
                                                        class="form-control input-sm" required="required">
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-2">
                                                    <label class="control-label-required" for="user-password">
                                                    <spring:message code="label.password" />:</label></th>
                                                  <td class="col-md-5"><input type="password" id="user-password" name="password"
                                                        class="form-control input-sm" required="required" autocomplete="off">
                                                  </td>
                                                </tr>
                                              </tbody>
                                            </table>
                                        </div>
                                        
                                        <div id="tab_content2" role="tabpanel" class="tab-pane fade col-md-10" 
                                             aria-labelledby="profile-tab">
                                            <table class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th colspan="4"><spring:message code="label.profile" /></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <tr>
                                                  <th scope="row" class="col-md-2">
                                                    <label class="control-label-required" for="person-lastname">
                                                    <spring:message code="label.user.last_name" />:</label></th>
                                                  <td colspan="3" class="col-md-8">
                                                     <div class="col-md-4 col-sm-4 col-xs-4"><input type="text" 
                                                      id="person-lastname" name="person.lastName" value="${user.person.lastName}"
                                                      class="form-control input-sm" required="required"></div>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-2">
                                                    <label class="control-label-required" for="person-firstName">
                                                    <spring:message code="label.user.first_name" />:</label></th>
                                                  <td colspan="3" class="col-md-8"><div class="col-md-4 col-sm-4 col-xs-4"><input type="text" 
                                                      id="person-firstName" name="person.firstName" value="${user.person.firstName}"
                                                      class="form-control input-sm" required="required"></div>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-2">
                                                    <label class="control-label" for="person-middleName">
                                                    <spring:message code="label.user.middle_name" />:</label></th>
                                                  <td colspan="3" class="col-md-8"><div class="col-md-4 col-sm-4 col-xs-4"><input type="text" 
                                                      id="person-middleName" name="person.middleName" value="${user.person.middleName}"
                                                      class="form-control input-sm"></div>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-2">
                                                    <label class="control-label-required" for="person-taxPayerNumber">
                                                    <spring:message code="label.user.tax_payer_number" />:</label></th>
                                                  <td colspan="3" class="col-md-8"><div class="col-md-4 col-sm-4 col-xs-4"><input type="text" 
                                                      id="person-taxPayerNumber" name="person.detail.taxPayerNumber" value="${user.person.detail.taxPayerNumber}"
                                                      class="form-control input-sm"></div>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="person-birth-date">
                                                  <spring:message code="label.user.birth_date" />:</label></th>
                                                  <td>
                                                    <div class="col-md-6 col-sm-6 col-xs-6">
                                                        <fmt:formatDate pattern="${dateFormatShort }" value="${user.person.detail.birthDate}" var="bDate"/>
                                                        <input id="person-birth-date" type="text" class="date-picker form-control input-sm" 
                                                               name="person.detail.birthDate" value="${bDate}" />
                                                    </div>
                                                  </td>
                                                  <th scope="row" class="col-md-2"><label class="control-label" for="person-gender">
                                                  <spring:message code="label.user.gender" />:</label></th>
                                                  <td class="col-md-3"><div class="col-md-6 col-sm-6 col-xs-6">
                                                    <select id="person-gender" class="form-control input-select-sm" name="person.detail.gender">
                                                        <c:forEach var="systemAttr" varStatus="loopCounter"
                                                            items="${SystemUtils.getAttributes('system.attrib.user.gender')}"> 
                                                            <option ${user.person.detail.gender == (loopCounter.count) ? 'selected="selected"' : ''}
                                                            value="${loopCounter.count}">${systemAttr}</option>
                                                        </c:forEach>
                                                    </select></div>
                                                  </td>
                                                </tr>
                                                <thead>
                                                    <tr>
                                                        <th colspan="4"><spring:message code="label.user.passport" /></th>
                                                    </tr>
                                                </thead>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="passport-serial">
                                                    <spring:message code="label.user.passport.number" />:</label></th>
                                                  <td>
                                                    <div class="form-group">
                                                        <span class="col-md-3"><input type="text" id="passport-serial" name="person.detail.passportSerial" 
                                                               value="${user.person.detail.passportSerial}" class="form-control input-sm ">
                                                        </span>
                                                        <span class="col-md-9">
                                                        <input type="text" id="passport-number" name="person.detail.passportNumber" 
                                                               value="${user.person.detail.passportNumber}" class="form-control input-sm">
                                                     </span></div>
                                                  </td>
                                                  <th scope="row" ><label class="control-label" for="passport-valid-date">
                                                      <spring:message code="label.user.passport.date.valid" />:</label></th>
                                                      <td>
                                                        <div class="col-md-6 col-sm-6 col-xs-6">
                                                                <fmt:formatDate pattern="${dateFormatShort }" value="${user.person.detail.passportValidDate}" var="pvDate"/>
                                                            <input id="passport-valid-date" type="text" class="date-picker form-control input-sm" 
                                                                   name="person.detail.passportValidDate" value="${pvDate}">
                                                        </div>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="passport-issue-ny">
                                                  <spring:message code="label.user.passport.issued_by" />:</label></th>
                                                  <td>
                                                    <div class="form-group">
                                                        <input type="text" id="passport-issue-ny" name="person.detail.passportIssuedBy" 
                                                               value="${user.person.detail.passportIssuedBy}" class="form-control input-sm">
                                                    </div>
                                                  </td>
                                                  <th scope="row" ><label class="control-label" for="passport-issue-ny">
                                                    <spring:message code="label.user.passport.date.issued" />:</label></th>
                                                  <td>
                                                    <div class="form-group col-md-6 col-sm-6 col-xs-6">
                                                        <fmt:formatDate pattern="${dateFormatShort }" value="${user.person.detail.passportIssuedDate}" var="psDate"/>
                                                        <input id="passport-issue-date" type="text" class="date-picker form-control input-sm" 
                                                           name="person.detail.passportIssuedDate" value="${psDate}">
                                                    </div>
                                                  </td>
                                                </tr>
                                                <thead>
                                                    <tr>
                                                        <th colspan="4"><i class="fa fa-graduation-cap"></i>&nbsp;
                                                        <spring:message code="label.education" /></th>
                                                    </tr>
                                                </thead>
                                                <tr>
                                                  <th scope="row" class="col-md-2">
                                                    <label class="control-label" for="person-institution">
                                                    <spring:message code="label.education.institution" />:</label></th>
                                                  <td><div><input type="text" 
                                                      id="person-institution" name="person.detail.edcInstitution" value="${user.person.detail.edcInstitution}"
                                                      class="form-control input-sm"></div>
                                                  </td>
                                                  <th scope="row" class="col-md-2">
                                                    <label class="control-label" for="person-qualification">
                                                    <spring:message code="label.education.qualification" />:</label></th>
                                                  <td><div><input type="text" 
                                                      id="person-qualification" name="person.detail.qualification" value="${user.person.detail.qualification}"
                                                      class="form-control input-sm"></div>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="edc_certificate_number">
                                                  <spring:message code="label.education.certificate.number" />:</label></th>
                                                  <td>
                                                    <div class="form-group">
                                                        <input type="text" id="edc_certificate_number" name="person.detail.edcCertificateNumber" 
                                                               value="${user.person.detail.edcCertificateNumber}" class="form-control input-sm">
                                                    </div>
                                                  </td>
                                                  <th scope="row" ><label class="control-label" for="edc_certificate_number">
                                                  <spring:message code="label.education.certificate.number" />:</label></th>
                                                  <td>
                                                    <div class="form-group">
                                                        <fmt:formatDate pattern="${dateFormatShort }" value="${user.person.detail.edcCertificateDate}" var="certDate"/>
                                                        <input id="passport-issue-date" type="text" class="date-picker form-control input-sm" 
                                                           name="person.detail.edcCertificateDate" value="${certDate}">
                                                    </div>
                                                  </td>
                                                </tr>
                                                <thead>
                                                    <tr>
                                                        <th colspan="4"><i class="fa fa-bank"></i>&nbsp;
                                                        <spring:message code="label.organization" /></th>
                                                    </tr>
                                                </thead>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="person-organization">
                                                  <spring:message code="label.organization" />:</label></th>
                                                  <td>
                                                    <div class="col-md-10">
                                                    <select id="person-organization" class="select2_single" name="person.organization.id">
                                                        <c:forEach var="organization" items="${organizations}" varStatus="loopCounter"> 
                                                            <c:set var="ogznId" value="${user.person.organization.id}"/>
                                                            <option ${ogznId == organization[0] ? 'selected="selected"' : ''}
                                                            value="${organization[0]}">${organization[1]}</option>
                                                        </c:forEach>
                                                    </select>
                                                    </div>
                                                  </td>
                                                  <th scope="row" class="col-md-2">
                                                    <label class="control-label" for="person-activity">
                                                    <spring:message code="label.user.activity" />:</label></th>
                                                  <td><div><input type="text" 
                                                      id="person-activity" name="person.detail.activity" value="${user.person.detail.activity}"
                                                      class="form-control input-sm"></div>
                                                  </td>
                                                </tr>
                                              </tbody>
                                            </table>                                        
                                        </div>
                                        <div id="tab_content3" role="tabpanel" class="tab-pane fade col-md-8" aria-labelledby="contacts-tab">
                                            <table id="" class="dataTable table table-bordered">
                                              <thead>
                                                <tr>
                                                    <th colspan="2"><i class="fa fa-map-marker"></i>&nbsp;<spring:message code="label.address" /></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <tr>
                                                  <th scope="row" class="col-md-2">
                                                    <label class="control-label" for="address-country-id">
                                                    <spring:message code="label.address.country" />:</label></th>
                                                  <td class="col-md-5">
                                                    <div class="col-md-6">
                                                    <select id="address-country-id" class="form-control input-select-sm" 
                                                        name="person.address.countryId">
                                                        <c:forEach var="systemAttr" varStatus="loopCounter"
                                                            items="${SystemUtils.getAttributes('system.attrib.address.country')}"> 
                                                            <option ${user.person.address.countryId == (loopCounter.count) ? 'selected="selected"' : ''}
                                                            value="${loopCounter.count}">${systemAttr}</option>
                                                        </c:forEach>
                                                    </select>
                                                    </div>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-2">
                                                  <label class="control-label" for="address-region-id">
                                                  <spring:message code="label.address.region" />:</label></th>
                                                  <td>
                                                    <div class="col-md-6">
                                                    <select id="address-region-id" class="form-control input-select-sm" 
                                                        name="person.address.regionId">
                                                        <c:forEach var="systemAttr" varStatus="loopCounter"
                                                            items="${SystemUtils.getAttributes('system.attrib.address.region.2')}"> 
                                                            <option ${user.person.address.regionId == (loopCounter.count) ? 'selected="selected"' : ''}
                                                            value="${loopCounter.count}">${systemAttr}</option>
                                                        </c:forEach>
                                                    </select>
                                                    </div>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><label class="control-label" for="address-city">
                                                    <spring:message code="label.address.city" />:</label></th>
                                                  <td><div class="col-md-6"><input type="text" id="address-city" name="person.address.city" 
                                                       value="${user.person.address.city}" class="form-control input-sm"></div>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><label class="control-label" for="address-primary">
                                                    <spring:message code="label.address.primary" />:</label></th>
                                                  <td><input type="text" id="address-primary" name="person.address.primaryAddress" 
                                                        value="${user.person.address.primaryAddress}" class="form-control input-sm">
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><label class="control-label" for="address-secondary">
                                                    <spring:message code="label.address.secondary" />:</label></th>
                                                  <td><input type="text" id="address-secondary" name="person.address.secondaryAddress" 
                                                      value="${user.person.address.secondaryAddress}" class="form-control input-sm">
                                                  </td>
                                                </tr>
                                                <thead>
                                                <tr>
                                                    <th colspan="2"><i class="fa fa-phone"></i>&nbsp;<spring:message code="label.contacts" /></th>
                                                </tr>
                                                </thead>
                                                <tr>
                                                  <th scope="row"><label class="control-label" for="contacts-phone">
                                                    <spring:message code="label.contacts.phone" />:</label></th>
                                                  <td><input type="text" id="contacts-phone" name="person.contact.phone"
                                                        value="${user.person.contact.phone}" class="form-control input-sm">
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><label class="control-label" for="contacts-email">
                                                    <spring:message code="label.contacts.email" />:</label></th>
                                                  <td><input type="text" id="contacts-email" name="person.contact.email"
                                                        value="${user.person.contact.email}" class="form-control input-sm">
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><label class="control-label" for="contacts-secondary">
                                                    <spring:message code="label.contacts.secondary" />:</label></th>
                                                  <td><input type="text" id="contacts-secondary" name="person.contact.secondaryContacts"
                                                        value="${user.person.contact.secondaryContacts}" class="form-control input-sm">
                                                  </td>
                                                </tr>
                                              </tbody>
                                            </table>                                        
                                        </div>
                                        <div role="tabpanel" class="tab-pane col-md-7 fade" 
                                             id="tab_content4" aria-labelledby="roles-tab">
                                             <c:set var="selectedItemCount" value="${fn:length(paramValues.roleIds)}"/>
                                             <c:set var="selectedItemIndex" value="0"/>
                                             <div class="bootstrap-duallistbox-container">
                                                <select multiple="multiple" class="roleDualBox" size="8" name="roleIds">
                                                <c:forEach var="role" items="${roles}" varStatus="loopCounter">
                                                    <c:set var="selected" value=""/>
                                                    <c:if test="${selectedItemIndex < selectedItemCount }">
                                                        <c:forEach var="selectedItem" items="${paramValues.groupIds}">
                                                            <c:if test="${selectedItem == role[0]}">
                                                                <c:set var="selected" value="selected"/>
                                                                <c:set var="selectedItemIndex" value="${selectedItemIndex + 1 }"/>
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:if>
                                                    <option ${selected} value="${role[0]}">${role[1]}</option>
                                                </c:forEach>
                                                </select>
                                             </div>
                                        </div>
                                        <div role="tabpanel" class="tab-pane fade col-md-7" 
                                             id="tab_content5"  aria-labelledby="groups-tab">
                                             
                                             <c:set var="selectedItemCount" value="${fn:length(paramValues.groupIds)}"/>
                                             <c:set var="selectedItemIndex" value="0"/>
                                             
                                             <div class="bootstrap-duallistbox-container">
                                                <select multiple="multiple" class="groupDualBox" size="8" name="groupIds">
                                                <c:forEach var="group" items="${groups}" varStatus="loopCounter">
                                                    <c:set var="selected" value=""/>
                                                    <c:if test="${selectedItemIndex < selectedItemCount }">
                                                        <c:forEach var="selectedItem" items="${paramValues.groupIds}">
                                                            <c:if test="${selectedItem == group[0]}">
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
            <jsp:param name="page" value="user_edit.vw" />
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
    
    <!-- Select2 -->
    <script src="resources/lib/select2/dist/js/select2.min.js"></script>
    
    <!-- bootstrap-duallistbox -->
    <script src="resources/lib/bootstrap-duallistbox/dist/duallistbox.min.js"></script>
    <script type="text/javascript">
    
    var rolesDualListBox = $('.roleDualBox').bootstrapDualListbox({
    	 bootstrap2compatible    : false,
    	  nonSelectedListLabel: '<spring:message code="label.role.list" />',
    	  selectedListLabel: '<spring:message code="label.role.selected" />',
    	  preserveSelectionOnMove: 'moved',
    	  moveOnSelect: false,
    	  showFilterInputs :true,
    	  infoText: false
    	  
    	});
    
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
    
    <!-- Custom Theme Scripts -->
    <script src="resources/js/custom.min.js"></script>
    
    <script>
      $(document).ready(function() 
      {
        $(".select2_single").select2({
          placeholder: "",
          allowClear: false
        });
      });
    </script>
    <!-- /Select2 -->
    


    <!-- Data Tables -->
    <script src="resources/lib/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="resources/lib/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script type="text/javascript">
    $(document).ready(function()
    {
        $('a[data-toggle="tab"]').on('show.bs.tab', function(e) {
            localStorage.setItem('userRegisterActiveTab', $(e.target).attr('href'));
        });
        
        var activeTab = localStorage.getItem('userRegisterActiveTab');
        
        if(activeTab)
        {
            $('#userRegisterTab a[href="' + activeTab + '"]').tab('show');
        }
    });
    </script>
    <!-- /Data Tables -->
    
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
    
    
</body>
</html>
