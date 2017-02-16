<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8" 
        errorPage="../error.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ************************************* -->

<div class="col-md-3 left_col menu_fixed">
    <div class="left_col scroll-view">
        <div class="navbar nav_title" style="border: 0;">
               <a href="main.vw" class="site_title">
              <img src="resources/images/art-monitor2.png" class="site_title_logo col-md-3">
                  <span class="col-md-6">
                    Электронная Системa<br>
                    Тестирования<br>
                    <span class="system_title">ATS1</span></span>
              </a>
         </div>
        

        <div class="clearfix"></div>

        <!-- sidebar menu -->
        <hr>
        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
            <div class="menu_section">

                <ul class="nav side-menu">
                    <li><a href="main.vw"><i class="fa fa-home"></i> 
                        <spring:message code="label.menu.home" /> </a></li>
                    <li><a><i class="fa fa-graduation-cap"></i> 
                        <spring:message code="label.menu.assessment" /><span
                            class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="user_assessments_list.vw"><spring:message code="label.menu.assessment.list.private" /></a></li>
                            <li><a href="assessment_register.vw"><spring:message code="label.menu.assessment.register" /></a></li>
                            <li><a href="assessment_list.vw"><spring:message code="label.menu.assessment.management" /></a></li>
                        </ul></li>
                    <li><a><i class="fa fa-cubes"></i> 
                        <spring:message code="label.menu.task.list" /><span
                            class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="asmt_task_register.vw"><i class="fa fa-plus"></i> 
                                 <spring:message code="label.menu.task.register" /> </a></li>
                            <li><a href="asmt_task_list.vw"><i class="fa fa-cube"></i> 
                                 <spring:message code="label.menu.task.management" /> </a></li>
                            <li><a href="asmt_category_register.vw"><i class="fa fa-plus"></i> 
                                 <spring:message code="label.menu.task.category.register" /> </a></li>
                            <li><a href="asmt_category_list.vw"><i class="fa fa-sitemap"></i>
                                <spring:message code="label.menu.task.category.management" />
                            </a></li>
                        </ul></li>
                    <li><a><i class="fa fa-bar-chart-o"></i>
                        <spring:message code="label.menu.reports" /><span
                            class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="chartjs.html">Chart JS</a></li>
                            <li><a href="chartjs2.html">Chart JS2</a></li>
                            <li><a href="morisjs.html">Moris JS</a></li>
                            <li><a href="echarts.html">ECharts</a></li>
                            <li><a href="other_charts.html">Other Charts</a></li>
                        </ul></li>
                    <li><a><i class="fa fa-gears"></i> <spring:message
                                code="label.menu.system_settings" /> <span
                            class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a><i class="fa fa-user"></i>
                                <spring:message code="label.menu.manage_users" />
                                    <span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li class="sub_menu"><a
                                        href="user_register.vw"> <spring:message
                                                code="label.menu.register_user" />
                                    </a></li>
                                    <li class="sub_menu"><a href="user_list.vw"> 
                                        <spring:message code="label.menu.user_list" />
                                    </a></li>
                                </ul></li>
                            <li><a><i class="fa fa-group"></i>
                                <spring:message code="label.menu.manage_groups" /><span
                                    class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li class="sub_menu"><a
                                        href="group_register.vw"> <spring:message
                                                code="label.menu.register_group" />
                                    </a></li>
                                    <li class="sub_menu"><a
                                        href="group_list.vw"> <spring:message
                                                code="label.menu.group_list" />
                                    </a></li>
                                </ul></li>
                            <li><a><i class="fa fa-key"></i> <spring:message
                                        code="label.menu.manage_roles" /><span
                                    class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li class="sub_menu"><a
                                        href="role_register.vw"> <spring:message
                                                code="label.menu.register_role" />
                                    </a></li>
                                    <li class="sub_menu"><a
                                        href="role_list.vw"> 
                                        <spring:message code="label.menu.role_list" />
                                    </a></li>
                                </ul></li>
                            <li><a><i class="fa fa-bank"></i> <spring:message
                                        code="label.menu.manage_organizations" /><span
                                    class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li class="sub_menu"><a href="organization_register.vw"> 
                                        <spring:message code="label.menu.register_organization" />
                                    </a></li>
                                    <li class="sub_menu"><a
                                        href="organization_list.vw"> <spring:message
                                                code="label.menu.organization_list" />
                                    </a></li>
                                </ul></li>
                        </ul></li>
                </ul>
            </div>
        </div>
        <!-- /sidebar menu -->

    </div>
</div>
