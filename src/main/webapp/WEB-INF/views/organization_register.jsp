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

<title><spring:message code="label.page.organization_register.title" /></title>

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
                        <div class="col-md-9 col-sm-9 col-xs-9">
                            <div class="x_panel">
                            <form id="organization" data-parsley-validate action="organization_register.do" 
                                  class="form-horizontal form-label-left" method="POST">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="x_title">
                                    <h2><spring:message code="label.page.organization_register.title" /></h2>
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
                                    <ul id="organizationRegisterTab" class="nav nav-tabs bar_tabs" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#tab_content1" id="organization-tab" role="tab" data-toggle="tab" aria-expanded="true">
                                            <spring:message code="label.page.organization_details.title" /></a>
                                        </li>
                                        <li role="presentation" class="">
                                            <a href="#tab_content3" id="contacts-tab" role="tab" data-toggle="tab" aria-expanded="false">
                                            <i class="fa fa-phone"></i>
                                            <spring:message code="label.contacts" /></a>
                                        </li>
                                    </ul>
                                    <div id="organizationDetailsTabContent" class="tab-content">
                                        <div id="tab_content1" role="tabpanel" class="tab-pane col-md-10 fade active in" 
                                              aria-labelledby="groups-tab">
                                            <table class="table table-bordered dataTable">
                                              <thead>
                                                <tr>
                                                    <th colspan="2"></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                <tr>
                                                  <th scope="row" class="col-md-3">
                                                      <label class="control-label-required" for="organization-name">
                                                          <spring:message code="label.organization.name" />:</label></th>
                                                      <td class="col-md-5">
                                                       <input type="text" id="organization-name" name="name" value="${organization.name}"
                                                        class="form-control input-sm" required="required">
                                                      </td>
                                               </tr>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="organization-tpn">
                                                  <spring:message code="label.organization.tax_payer_number" />:</label></th>
                                                  <td><input type="text" id="organization-tpn" name="detail.taxPayerNumber" 
                                                        value="${organization.detail.taxPayerNumber}" class="form-control input-sm">
                                                   </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="organization-sector">
                                                  <spring:message code="label.organization.sector" />:</label></th>
                                                  <td>
                                                    <select id="organization-sector" class="form-control input-select-sm" name="detail.sector">
                                                        <c:forEach var="systemAttr" varStatus="loopCounter"
                                                            items="${SystemUtils.getAttributes('system.attrib.organization.sector')}"> 
                                                            <option ${organization.detail.sector == (loopCounter.count) ? 'selected="selected"' : ''}
                                                            value="${loopCounter.count}">${systemAttr}</option>
                                                        </c:forEach>
                                                    </select>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" ><label class="control-label" for="organization-activity">
                                                  <spring:message code="label.organization.activity" />:</label></th>
                                                  <td>
                                                    <select id="organization-activity" class="form-control input-select-sm" name="detail.activity">
                                                        <c:forEach var="systemAttr" varStatus="loopCounter"
                                                            items="${SystemUtils.getAttributes('system.attrib.organization.activity')}"> 
                                                            <option ${organization.detail.activity == (loopCounter.count) ? 'selected="selected"' : ''}
                                                            value="${loopCounter.count}">${systemAttr}</option>
                                                        </c:forEach>
                                                    </select>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-3">
                                                      <label class="control-label" for="organization-desc">
                                                          <spring:message code="label.organization.desc" />:</label></th>
                                                      <td class="col-md-5">
                                                       <input type="text" id="organization-desc" name="detail.details" value=""
                                                        value="${organization.detail.details}" class="form-control input-sm">
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
                                                  <th scope="row" class="col-md-3">
                                                    <label class="control-label" for="address-country-id">
                                                    <spring:message code="label.address.country" />:</label></th>
                                                  <td>
                                                    <select id="address-country-id" class="form-control input-select-sm" 
                                                        name="address.countryId">
                                                        <c:forEach var="systemAttr" varStatus="loopCounter"
                                                            items="${SystemUtils.getAttributes('system.attrib.address.country')}"> 
                                                            <option ${organization.address.countryId == (loopCounter.count) ? 'selected="selected"' : ''}
                                                            value="${loopCounter.count}">${systemAttr}</option>
                                                        </c:forEach>
                                                    </select>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row" class="col-md-3">
                                                  <label class="control-label" for="address-region-id">
                                                  <spring:message code="label.address.region" />:</label></th>
                                                  <td class="col-md-8">
                                                    <select id="address-region-id" class="form-control input-select-sm" 
                                                        name="address.regionId">
                                                        <c:forEach var="systemAttr" varStatus="loopCounter"
                                                            items="${SystemUtils.getAttributes('system.attrib.address.region.2')}"> 
                                                            <option ${organization.address.regionId == (loopCounter.count) ? 'selected="selected"' : ''}
                                                            value="${loopCounter.count}">${systemAttr}</option>
                                                        </c:forEach>
                                                    </select>
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><label class="control-label" for="address-city">
                                                    <spring:message code="label.address.city" />:</label></th>
                                                  <td><input type="text" id="address-city" name="address.city" 
                                                       value="${organization.address.city}" class="form-control input-sm">
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><label class="control-label" for="address-primary">
                                                    <spring:message code="label.address.primary" />:</label></th>
                                                  <td><input type="text" id="address-primary" name="address.primaryAddress" 
                                                        value="${organization.address.primaryAddress}" class="form-control input-sm">
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><label class="control-label" for="address-secondary">
                                                    <spring:message code="label.address.secondary" />:</label></th>
                                                  <td><input type="text" id="address-secondary" name="address.secondaryAddress" 
                                                      value="${organization.address.secondaryAddress}" class="form-control input-sm">
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
                                                  <td><input type="text" id="contacts-phone" name="contact.phone"
                                                        value="${organization.contact.phone}" class="form-control input-sm">
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><label class="control-label" for="contacts-email">
                                                    <spring:message code="label.contacts.email" />:</label></th>
                                                  <td><input type="text" id="contacts-email" name="contact.email"
                                                        value="${organization.contact.email}" class="form-control input-sm">
                                                  </td>
                                                </tr>
                                                <tr>
                                                  <th scope="row"><label class="control-label" for="contacts-secondary">
                                                    <spring:message code="label.contacts.secondary" />:</label></th>
                                                  <td><input type="text" id="contacts-secondary" name="contact.secondaryContacts"
                                                        value="${organization.contact.secondaryContacts}" class="form-control input-sm">
                                                  </td>
                                                </tr>
                                              </tbody>
                                            </table>                                        
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
            localStorage.setItem('ognzRegisterActiveTab', $(e.target).attr('href'));
        });
        
        var activeTab = localStorage.getItem('ognzRegisterActiveTab');
        
        if(activeTab)
        {
            $('#organizationRegisterTab a[href="' + activeTab + '"]').tab('show');
        }
    });
    </script>

</body>
</html>
