<!-- ************************************* -->
<%@page contentType="text/html" 
        pageEncoding="UTF-8"
	    errorPage="error.jsp"%>

<%@page import="common.utils.system.SystemUtils,java.util.Locale" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- ************************************* -->

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title><spring:message code="label.page.task.categories.title"/></title>

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

<!-- Tree Grid -->
<link rel="stylesheet" href="resources/lib/jquery-treegrid/css/jquery.treegrid.css">

</head>

<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<!-- sidebar -->
			<jsp:include page="include/sidebar.jsp"><jsp:param name="page"
					value="category_list.vw" /></jsp:include>
			<!-- /sidebar -->

			<!-- top navigation -->
			<jsp:include page="include/header.jsp"><jsp:param name="page"
					value="category_list.vw" /></jsp:include>
			<!-- /top navigation -->

			<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<div class="row">
						<div class="col-md-5 col-sm-5 col-xs-5">
							<div class="x_panel">
								<div class="x_title">
									<h2><spring:message code="label.page.task.categories.title"/></h2>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
                                <table class="tree">
                                    <tr class="treegrid-1">
                                        <td>Root node</td><td>Additional info</td>
                                    </tr>
                                    <tr class="treegrid-2 treegrid-parent-1">
                                        <td>Node 1-1</td><td>Additional info</td>
                                    </tr>
                                    <tr class="treegrid-3 treegrid-parent-1">
                                        <td>Node 1-2</td><td>Additional info</td>
                                    </tr>
                                    <tr class="treegrid-4 treegrid-parent-3">
                                        <td>Node 1-2-1</td><td>Additional info</td>
                                    </tr>
                                </table>
                                </div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /page content -->

			<!-- footer content -->
			<jsp:include page="include/footer.jsp"><jsp:param name="page"
					value="main" /></jsp:include>
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
    
    
    <!-- Tree Grid -->
    <script type="text/javascript" src="resources/lib/jquery-treegrid/js/jquery.treegrid.js"></script>
    <script type="text/javascript" src="resources/lib/jquery-treegrid/js/jquery.treegrid.bootstrap3.js"></script>
    <script type="text/javascript">
    
    $(document).ready(function() 
    {
        $('.tree').treegrid();
    });
    
    </script>
</body>
</html>
